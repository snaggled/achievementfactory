package com.packetnode.achievements;

import java.util.*;

import com.packetnode.statistics.CombinedStatistics;

/**
 * Create achievement if > 75% hit rate
 * @author philipmcmahon
 *
 */
public class SharpshooterFactory implements AchievementFactory {

	/**
	 * Accuracy level to get this achievement
	 */
	public static final double SUCCESSRATE = 0.75;

	public List<Achievement> getAchievements(CombinedStatistics stats) {
		List<Achievement> result = new ArrayList<Achievement>();

		// if we have something to work with
		if (stats.getHits() > 0 && stats.getAttemptedAttacks() > 0) {
			float successRate = (float) stats.getHits()
					/ (float) stats.getAttemptedAttacks();
			if (successRate >= SUCCESSRATE)
				result.add(new Sharpshooter());
			// System.out.println(stats.getHits() + "/" +
			// stats.getAttemptedAttacks() + " == " + successRate);
		}

		return result;
	}

	/**
	 * The actual award
	 * @author philipmcmahon
	 *
	 */
	public final class Sharpshooter extends Achievement {

		/**
		 * Default ctr
		 */
		public Sharpshooter() {
			super("Sharpshooter");
		}
	}
}
