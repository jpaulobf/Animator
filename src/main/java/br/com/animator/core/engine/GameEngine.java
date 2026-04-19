package br.com.animator.core.engine;

import java.text.DecimalFormat;
import java.util.concurrent.locks.LockSupport;
import br.com.animator.core.game.GameGlobals;
import br.com.animator.core.game.IGame;

/**
 * GameEngine - Responsible for the game loop, timing, and performance tracking
 * (FPS/UPS).
 */
public class GameEngine implements Runnable {

	// --- Constants ---//
	private static final long MAX_ACCUMULATED_LAG = 1_000_000_000L; // 1 second cap
	private static final long MAX_DELTA_TIME = 100_000_000L; // 100ms cap for delta time
	private static final long SLEEP_BUFFER = 500_000L; // 0.5ms sleep buffer
	private static final int STATS_BUFFER_SIZE = 10; // Circular buffer size for statistics
	private static final long MAX_STATS_INTERVAL = 1_000_000_000L;
	private static final long FIRST_STATS_INTERVAL = 2_000_000_000L;
	private static final String ENGINE_THREAD_NAME = "GameEngine-Thread";
	private static final DecimalFormat STATS_FORMATTER = new DecimalFormat("0.##");

	// --- Properties ---//
	private final IGame game;
	private volatile int targetFPS;
	private volatile long targetFrametime;
	private volatile boolean running = false;
	private volatile boolean isToCalc = true;
	private volatile boolean storeStats = true;
	private long frameCount = 0;
	private long lastFrameCount = 0L;

	// --- Stats Tracking ---//
	private volatile double averageFPS = 0.0;
	private volatile double averageUPS = 0.0;
	private long prevStatsTime = 0L;
	private long nextStatsTime = 0L;
	private double fpsStore[] = new double[STATS_BUFFER_SIZE];
	private double upsStore[] = new double[STATS_BUFFER_SIZE];
	private double totalFPSStore = 0.0;
	private double totalUPSStore = 0.0;
	private long statsCount = 0;
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;

	/**
	 * Constructor
	 * 
	 * @param game      The game instance to be managed by the engine.
	 * @param targetFPS Target frames per second (0 for unlimited)
	 */
	public GameEngine(IGame game, int targetFPS) {
		this.game = game;
		this.targetFPS = targetFPS;
		this.targetFrametime = getTargetFrametime(this.targetFPS);

		// Add high resolution timer mode
		Thread timerResolutionDaemon = new Thread(() -> {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException ignored) {
			}
		}, "TimerResolutionTrigger");
		timerResolutionDaemon.setDaemon(true);
		timerResolutionDaemon.start();

		this.startEngine();
	}

	/**
	 * Gets the target frame time in nanoseconds based on desired FPS.
	 * 
	 * @param fps Target FPS (0 for unlimited)
	 * @return Frame time in nanoseconds, or -1 for unlimited
	 */
	private long getTargetFrametime(int fps) {
		if (fps <= 0) {
			return -1;
		}
		return 1_000_000_000L / fps;
	}

	/**
	 * Dynamically updates the target FPS.
	 * 
	 * @param fps The new target FPS (0 or negative for unlimited)
	 */
	public void setTargetFPS(int fps) {
		this.targetFPS = fps;
		this.targetFrametime = getTargetFrametime(fps);
	}

	/**
	 * startEngine - Starts the game loop in a new thread with appropriate priority.
	 */
	protected void startEngine() {
		if (!this.running) {
			Thread engine = new Thread(this, ENGINE_THREAD_NAME);
			engine.setPriority(Thread.MAX_PRIORITY);
			engine.start();
		}
	}

	/**
	 * run - The main game loop with adaptive timing control.
	 */
	public void run() {
		long lastTime = System.nanoTime();
		long now = 0;
		long elapsed = 0;
		long wait = 0;
		long overSleep = 0;
		this.running = true;
		this.prevStatsTime = lastTime;
		this.nextStatsTime = lastTime + FIRST_STATS_INTERVAL;

		while (running) {
			// Capture target frametime locally to ensure consistency during this iteration
			long currentTarget = this.targetFrametime;

			if (currentTarget <= 0) {
				// --- UNLIMITED MODE ---
				now = System.nanoTime();
				elapsed = now - lastTime;
				lastTime = now;

				if (elapsed > MAX_DELTA_TIME)
					elapsed = MAX_DELTA_TIME;

				//--- set default game velocity
				this.calcVelocity(elapsed);

				//--- gameloop
				this.gameUpdate(elapsed);
				this.gameRender(elapsed);
				this.paint();

				overSleep = 0; // Reset compensation when in unlimited
			} else {
				// --- FIXED FPS MODE ---
				now = System.nanoTime();
				elapsed = now - lastTime;
				lastTime = now;

				//--- set default game velocity
				this.calcVelocity(elapsed);

				//--- gameloop
				this.gameUpdate(elapsed);
				this.gameRender(elapsed);
				this.paint();

				// Calculate time taken for this frame
				long workTime = System.nanoTime() - now;

				// Calculate wait time, compensating for previous over-sleep/lag
				wait = currentTarget - workTime - overSleep;

				if (wait > 0) {
					long targetEnd = now + currentTarget - overSleep;
					long nanosToPark = wait - SLEEP_BUFFER;

					if (nanosToPark > 0) {
						LockSupport.parkNanos(nanosToPark);
					}

					while (System.nanoTime() < targetEnd) {
						// onSpinWait é muito mais leve que yield para o processador
						Thread.onSpinWait();
					}
					// Calcula o atraso real exato para compensar no próximo frame
					overSleep = System.nanoTime() - targetEnd;
				} else {
					// We are behind schedule
					overSleep = -wait;
					
					// Cap accumulated lag to prevent "spiral of death" after long pauses
					if (overSleep > MAX_ACCUMULATED_LAG) 
						overSleep = MAX_ACCUMULATED_LAG;

					// Frame Skipping: Limit skips per cycle to prevent extreme lag recovery
					int skipsThisCycle = 0;
					while ((overSleep >= currentTarget) && (skipsThisCycle < 2)) {

						//--- set default game velocity
						this.calcVelocity(currentTarget);

						//--- gameloop
						this.gameUpdate(currentTarget);
						overSleep -= currentTarget;
						skipsThisCycle++;
					}
					this.framesSkipped += skipsThisCycle;
				}
			}

			if (this.storeStats) {
				this.storeStats(now);
			}
		}

		this.printStats();
		System.exit(0);
	}

	/**
	 * paint - Paint the buffer to the screen.
	 */
	private void paint() {
		try {
			this.game.paintScreen();
		} catch (Exception e) {
			System.err.println("Error painting screen: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * gameUpdate - Update the game state based on the elapsed frame time.
	 * 
	 * @param frametime
	 */
	private void gameUpdate(long frametime) {
		this.game.update(frametime);
	}

	/**
	 * gameRender - Render the game state to the screen.
	 * 
	 * @param frametime
	 */
	private void gameRender(long frametime) {
		this.game.render(frametime);
	}

	/**
	 * Store the game statistics in the Stats Store to calculate the average FPS and
	 * UPS.
	 * 
	 * @param timeNow The current time in nanoseconds already captured in the loop.
	 */
	private void storeStats(long timeNow) {
		this.frameCount++;

		if (timeNow < this.nextStatsTime) {
			return;
		}

		long realElapsedTime = timeNow - this.prevStatsTime;
		if (realElapsedTime <= 0) return;

		// Calculate FPS and UPS using a multiplier to avoid multiple divisions
		double invTime = 1_000_000_000.0 / realElapsedTime;
		long framesInInterval = this.frameCount - this.lastFrameCount;
		this.lastFrameCount = this.frameCount;

		double actualFPS = framesInInterval * invTime;
		double actualUPS = (framesInInterval + this.framesSkipped) * invTime;

			this.totalFramesSkipped += this.framesSkipped;

			// Circular buffer system with efficient O(1) accumulator
			int index = (int) (this.statsCount % STATS_BUFFER_SIZE);

			// Remove old value from accumulator if buffer is full
			if (this.statsCount >= STATS_BUFFER_SIZE) {
				this.totalFPSStore -= this.fpsStore[index];
				this.totalUPSStore -= this.upsStore[index];
			}

			// Add new value
			this.fpsStore[index] = actualFPS;
			this.upsStore[index] = actualUPS;
			this.totalFPSStore += actualFPS;
			this.totalUPSStore += actualUPS;
			this.statsCount++;

			// Calculate average in O(1) instead of O(n)
			int activeSamples = (int) Math.min(this.statsCount, STATS_BUFFER_SIZE);
			this.averageFPS = this.totalFPSStore / activeSamples;
			this.averageUPS = this.totalUPSStore / activeSamples;

			// Reset interval data
			this.framesSkipped = 0;
			this.prevStatsTime = timeNow;
			this.nextStatsTime = timeNow + MAX_STATS_INTERVAL;
		}

	/**
	 * printStats - Thread-safe stats printing
	 */
	private void printStats() {
		synchronized (this) {
			System.out.println("Frame Count/Loss: " + this.frameCount + " / " + this.totalFramesSkipped);
			System.out.println("Average FPS: " + STATS_FORMATTER.format(this.averageFPS));
			System.out.println("Average UPS: " + STATS_FORMATTER.format(this.averageUPS));
		}
	}

	public double getAverageFPS() {
		return averageFPS;
	}

	public double getAverageUPS() {
		return averageUPS;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void stop() {
		this.running = false;
	}

	public void calcVelocity(long currentTarget) {
		GameGlobals.GAME_VELOCITY = (this.isToCalc)?currentTarget * GameGlobals.GAME_TARGET_VELOCITY:1;
	}

	public void setToCalc(boolean isToCalc) {
		this.isToCalc = isToCalc;
	}
}
