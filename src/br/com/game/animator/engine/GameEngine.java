package br.com.game.animator.engine;

import java.text.DecimalFormat;
import br.com.game.animator.game.IGame;

/**
 * GameEngine - Responsible for the game loop, timing, and performance tracking (FPS/UPS).
 */
public class GameEngine implements Runnable {

	//--- Constants ---//
	public static final Integer FPS = 60;
	private static final Integer MAX_FRAME_SKIPS = 5;
	private final long PERIOD = 1000000000L / FPS;
	private final static long MAX_STATS_INTERVAL = 1000000000L;
	private final static long FIRST_STATS_INTERVAL = 2000000000L;

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
	public GameEngine(IGame game) {
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

	/**
	 * run - The main game loop, responsible for updating, rendering, and timing control.
	 */
	public void run() {
		this.running = true;
		long beforeTime = System.nanoTime();
		long overSleepTime = 0L;
		this.prevStatsTime = beforeTime;

		while (this.running) {
			this.gameUpdate(PERIOD);
			try {
				this.paint();
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.gameRender(PERIOD);

			long afterTime = System.nanoTime();
			long timeDiff = afterTime - beforeTime;
			long sleepTime = (PERIOD - timeDiff) - overSleepTime;

			if (sleepTime > 0) {
				long sleepMs = sleepTime / 1000000L;
				if (sleepMs > 2) {
					try {
						Thread.sleep(sleepMs - 2);
					} catch (InterruptedException e) {
					}
				}

				while (System.nanoTime() - beforeTime < PERIOD) {
				}

				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else {
				long excess = -sleepTime;
				overSleepTime = 0L;

				int skips = 0;
				while ((excess > PERIOD) && (skips < MAX_FRAME_SKIPS)) {
					excess -= PERIOD;
					this.gameUpdate(PERIOD);
					skips++;
				}
				this.framesSkipped += skips;
			}

			beforeTime = System.nanoTime();

			if (this.storeStats) {
				this.storeStats();
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