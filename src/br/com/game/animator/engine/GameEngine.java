package br.com.game.animator.engine;

import java.text.DecimalFormat;
import br.com.game.animator.game.core.IGame;

/**
 * GameEngine - Responsible for the game loop, timing, and performance tracking (FPS/UPS).
 */
public class GameEngine implements Runnable {

	//--- Constants ---//
	private static final long MAX_DELTA_TIME = 100_000_000L; // 100ms cap para delta time
	private static final long SLEEP_BUFFER = 500_000L; // 0.5ms buffer para sleep
	private static final int STATS_BUFFER_SIZE = 10; // Tamanho do buffer circular para estatísticas
	private static final long MAX_STATS_INTERVAL = 1_000_000_000L;
	private static final long FIRST_STATS_INTERVAL = 2_000_000_000L;
	private static final String ENGINE_THREAD_NAME = "GameEngine-Thread";
	private static final DecimalFormat STATS_FORMATTER = new DecimalFormat("0.##");
	
	// FPS frame times as static constants
	private static final long FPS240 = 1_000_000_000L / 240;
	private static final long FPS120 = 1_000_000_000L / 120;
	private static final long FPS90  = 1_000_000_000L / 90;
	private static final long FPS60  = 1_000_000_000L / 60;
	private static final long FPS30  = 1_000_000_000L / 30;

	//--- Properties ---//
	private final IGame game;
	private final int targetFPS;
	private final long targetFrametime;
	public volatile boolean running = false;
	private volatile boolean storeStats = true;
	private long frameCount = 0;
	private long lastFrameCount = 0L;

	//--- Stats Tracking ---//
	public double averageFPS = 0.0;
	public double averageUPS = 0.0;
	private long prevStatsTime = 0L;
	private double fpsStore[] = new double[STATS_BUFFER_SIZE];
	private double upsStore[] = new double[STATS_BUFFER_SIZE];
	private double totalFPSStore = 0.0; // Acumulador para média móvel O(1)
	private double totalUPSStore = 0.0; // Acumulador para média móvel O(1)
	private long statsCount = 0;
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private boolean startStoreStats = false;

	/**
	 * Constructor
	 * 
	 * @param game The game instance to be managed by the engine.
	 * @param targetFPS Target frames per second (0 for unlimited)
	 */
	public GameEngine(IGame game, int targetFPS) {
		this.game = game;
		this.targetFPS = targetFPS;
		this.targetFrametime = getTargetFrametime(this.targetFPS);
		this.startEngine();
	}
	
	/**
	 * Gets the target frame time in nanoseconds based on desired FPS.
	 * 
	 * @param fps Target FPS (0 for unlimited)
	 * @return Frame time in nanoseconds, or -1 for unlimited
	 */
	private long getTargetFrametime(int fps) {
		return switch(fps) {
			case 30 -> FPS30;
			case 60 -> FPS60;
			case 90 -> FPS90;
			case 120 -> FPS120;
			case 240 -> FPS240;
			case 0 -> -1; // Unlimited FPS
			default -> FPS60;
		};
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
		long lastTime           = System.nanoTime();
		long now                = 0;
		long elapsed            = 0;
		long wait               = 0;
		long overSleep          = 0;
		this.running            = true;
		this.prevStatsTime      = lastTime;

		if (this.targetFrametime < 0) {
			// Unlimited FPS mode
			System.out.println("Running in UNLIMITED FPS mode.");
			while (running) {
				now = System.nanoTime();
				elapsed = now - lastTime;
				lastTime = now;

				// Cap delta time to avoid huge jumps
				if (elapsed > MAX_DELTA_TIME) elapsed = MAX_DELTA_TIME;

				this.gameUpdate(elapsed);
				this.paint();
				this.gameRender(elapsed);
				
				// Yield to prevent CPU starvation
				Thread.yield();

				if (this.storeStats) {
					this.storeStats();
				}
			}
		} else {
			// Fixed FPS mode
			while (running) {
				now = System.nanoTime();
				elapsed = now - lastTime;
				lastTime = now;

				this.gameUpdate(elapsed);
				this.paint();
				this.gameRender(elapsed);

				// Calculate time taken for this frame
				long workTime = System.nanoTime() - now;

				// Calculate wait time, compensating for previous over-sleep/lag
				wait = this.targetFrametime - workTime - overSleep;

				if (wait > 0) {
					try {
						// Hybrid Sleep Strategy with dynamic buffer:
						// Use dynamic buffer or fixed minimum, then spin-wait for precision
						long sleepBuffer = Math.max(SLEEP_BUFFER, wait / 10); // Adaptive buffer
						long sleepMs = (wait - sleepBuffer) / 1_000_000;
						
						if (sleepMs > 0) {
							Thread.sleep(sleepMs);
						}
						
						// Busy-wait for the remaining nanoseconds with yield for other threads
						while (System.nanoTime() < now + this.targetFrametime - overSleep) {
							Thread.yield();
						}
						overSleep = 0;
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				} else {
					// We are behind schedule
					overSleep = -wait;
					
					// Frame Skipping: Limit skips per cycle to prevent extreme lag recovery
					int skipsThisCycle = 0;
					while ((overSleep >= this.targetFrametime) && (skipsThisCycle < 2)) {
						this.gameUpdate(this.targetFrametime);
						overSleep -= this.targetFrametime;
						skipsThisCycle++;
					}
					this.framesSkipped += skipsThisCycle;
				}
				if (this.storeStats) {
					this.storeStats();
				}
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
	 * @param frametime
	 */
	private void gameUpdate(long frametime) {
		this.game.update(frametime);
	}

	/**
	 * gameRender - Render the game state to the screen.
	 * @param frametime
	 */
	private void gameRender(long frametime) {
		this.game.render(frametime);
	}

	/**
	 * Store the game statistics in the Stats Store to calculate the average FPS and
	 * UPS.
	 */
	private void storeStats() {
		this.frameCount++;
		long timeNow = System.nanoTime();
		long realElapsedTime = timeNow - this.prevStatsTime;

		// Define se usamos o intervalo inicial (2s) ou o normal (1s)
		long triggerInterval = startStoreStats ? MAX_STATS_INTERVAL : FIRST_STATS_INTERVAL;

		if (realElapsedTime >= triggerInterval) {
			this.startStoreStats = true;

			// Validação de tempo zero (edge case)
			if (realElapsedTime <= 0) return;

			// Calcula quantos quadros ocorreram APENAS neste último intervalo
			long framesInInterval = this.frameCount - this.lastFrameCount;
			this.lastFrameCount = this.frameCount;

			// FPS e UPS baseados no tempo real decorrido no intervalo
			double actualFPS = (framesInInterval * 1_000_000_000.0) / realElapsedTime;
			double actualUPS = ((framesInInterval + this.framesSkipped) * 1_000_000_000.0) / realElapsedTime;

			this.totalFramesSkipped += this.framesSkipped;

			// Sistema de buffer circular com acumulador eficiente O(1)
			int index = (int) (this.statsCount % STATS_BUFFER_SIZE);

			// Remove valor antigo do acumulador se buffer está cheio
			if (this.statsCount >= STATS_BUFFER_SIZE) {
				this.totalFPSStore -= this.fpsStore[index];
				this.totalUPSStore -= this.upsStore[index];
			}

			// Adiciona novo valor
			this.fpsStore[index] = actualFPS;
			this.upsStore[index] = actualUPS;
			this.totalFPSStore += actualFPS;
			this.totalUPSStore += actualUPS;
			this.statsCount++;

			// Calcula média em O(1) ao invés de O(n)
			int activeSamples = (int) Math.min(this.statsCount, STATS_BUFFER_SIZE);
			this.averageFPS = this.totalFPSStore / activeSamples;
			this.averageUPS = this.totalUPSStore / activeSamples;

			// Reseta dados do intervalo
			this.framesSkipped = 0;
			this.prevStatsTime = timeNow;
		}
	}

	/**
	 * printStats - Thread-safe stats printing
	 */
	private void printStats() {
		synchronized(this) {
			System.out.println("Frame Count/Loss: " + this.frameCount + " / " + this.totalFramesSkipped);
			System.out.println("Average FPS: " + STATS_FORMATTER.format(this.averageFPS));
			System.out.println("Average UPS: " + STATS_FORMATTER.format(this.averageUPS));
		}
	}

    public void stop() {
        this.running = false;
    }
}
