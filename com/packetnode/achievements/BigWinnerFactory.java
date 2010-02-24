package com.packetnode.achievements;

import java.util.ArrayList;
import java.util.List;

import com.packetnode.statistics.CombinedStatistics;

/**
 * Create an achievement if player has won over 200 games
 * @author philipmcmahon
 *
 */
public class BigWinnerFactory implements AchievementFactory {

	/**
	 * Number of wins in order to get this achievement
	 */
	public static final int WINLIMIT = 200;

	public List<Achievement> getAchievements(CombinedStatistics stats) {
		List<Achievement> result = new ArrayList<Achievement>();

		// if we have something to work with
		if (stats.getWins() >= WINLIMIT)
			result.add(new BigWinner());

		return result;
	}

	/**
	 * The actual award
	 * @author philipmcmahon
	 *
	 */
	public final class BigWinner extends Achievement {

		/**
		 * Default ctr
		 */
		public BigWinner() {
			super("BigWinner", true);
		}
	}

}
