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
	private final static long MAX_STATS_INTERVAL = 1000000000L;
	private final static long FIRST_STATS_INTERVAL = 2000000000L;
	private long FPS240                 = (long)(1_000_000_000 / 240);
	private long FPS120                 = (long)(1_000_000_000 / 120);
	private long FPS90                  = (long)(1_000_000_000 / 90);
	private long FPS60                  = (long)(1_000_000_000 / 60);
	private long FPS30                  = (long)(1_000_000_000 / 30);
	private long TARGET_FRAMETIME       = 0;
	private boolean UNLIMITED_FPS       = false;

	//--- Properties ---//
	private IGame game = null;
	public volatile boolean running = false;
	private long frameCount = 0;
	private long lastFrameCount = 0L;

	//--- Stats Tracking ---//
	public double averageFPS = 0.0;
	public double averageUPS = 0.0;
	private long prevStatsTime = 0L;
	private double fpsStore[] = new double[10];
	private double upsStore[] = new double[10];
	private long statsCount = 0;
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private DecimalFormat df = new DecimalFormat("0.##");
	private boolean storeStats = true;
	private boolean startStoreStats = false;

	/**
	 * Constructor
	 * 
	 * @param game The game instance to be managed by the engine.
	 */
	public GameEngine(IGame game, int targetFPS) {
		this.UNLIMITED_FPS = false;
		switch(targetFPS) {
			case 30:
				this.TARGET_FRAMETIME = FPS30;
				break;
			case 60:
				this.TARGET_FRAMETIME = FPS60;
				break;
			case 90:
				this.TARGET_FRAMETIME = FPS90;
				break;
			case 120:
				this.TARGET_FRAMETIME = FPS120;
				break;
			case 240:
				this.TARGET_FRAMETIME = FPS240;
				break;
			case 0:
				this.UNLIMITED_FPS = true;
				break;
			default:
				this.TARGET_FRAMETIME = FPS30;
				break;
		}

		this.game = game;
		this.startEngine();
	}

	/**
	 * startEngine - Starts the game loop in a new thread.
	 */
	protected void startEngine() {
		if (!this.running) {
			Thread engine = new Thread(this);
			engine.start();
		}
	}

	public void run() {
		long lastTime           = System.nanoTime();
		long now                = 0;
		long elapsed            = 0;
		long wait               = 0;
		long overSleep          = 0;
		this.running 			= true;
		this.prevStatsTime 		= lastTime;

		if (UNLIMITED_FPS) {
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
				wait = TARGET_FRAMETIME - workTime - overSleep;

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
						while (System.nanoTime() < now + TARGET_FRAMETIME - overSleep) {
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
					while ((overSleep >= TARGET_FRAMETIME) && (skipsThisCycle < 2)) {
						this.gameUpdate(TARGET_FRAMETIME);
						overSleep -= TARGET_FRAMETIME;
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
		this.game.paintScreen();
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

			// Calcula quantos quadros ocorreram APENAS neste último intervalo
			long framesInInterval = this.frameCount - this.lastFrameCount;
			this.lastFrameCount = this.frameCount;

			// FPS e UPS baseados no tempo real decorrido no intervalo
			double actualFPS = ((double) framesInInterval / realElapsedTime) * 1000000000L;
			double actualUPS = ((double) (framesInInterval + this.framesSkipped) / realElapsedTime) * 1000000000L;

			this.totalFramesSkipped += this.framesSkipped;

			// Armazena no buffer circular de médias (tamanho definido na inicialização, ex:
			// 10)
			int numSamples = this.fpsStore.length;
			int index = (int) (this.statsCount % numSamples);
			this.fpsStore[index] = actualFPS;
			this.upsStore[index] = actualUPS;
			this.statsCount++;

			// Calcula a média móvel das últimas amostras
			double totalFPS = 0.0;
			double totalUPS = 0.0;
			int activeSamples = (int) Math.min(this.statsCount, numSamples);

			for (int i = 0; i < activeSamples; i++) {
				totalFPS += this.fpsStore[i];
				totalUPS += this.upsStore[i];
			}

			this.averageFPS = totalFPS / activeSamples;
			this.averageUPS = totalUPS / activeSamples;

			// Reseta dados do intervalo
			this.framesSkipped = 0;
			this.prevStatsTime = timeNow;
		}
	}

	/**
	 * printStats
	 */
	private void printStats() {
		System.out.println("Frame Count/Loss: " + this.frameCount + " / " + this.totalFramesSkipped);
		System.out.println("Average FPS: " + this.df.format(this.averageFPS));
		System.out.println("Average UPS: " + this.df.format(this.averageUPS));
	}

    public void stop() {
        this.running = false;
    }
}