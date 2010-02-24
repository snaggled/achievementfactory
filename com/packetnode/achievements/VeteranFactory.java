package com.packetnode.achievements;

import java.util.*;

import com.packetnode.statistics.CombinedStatistics;

/**
 * Create achievement if played more than 1000 games
 * @author philipmcmahon
 *
 */
public class VeteranFactory implements AchievementFactory {

	/**
	 * Number of games played to get this award
	 */
	public static final double GAMELIMIT = 1000;

	public List<Achievement> getAchievements(CombinedStatistics stats) {
		List<Achievement> result = new ArrayList<Achievement>();

		if (stats.getGamesPlayed() >= GAMELIMIT)
			result.add(new Veteran());

		return result;
	}

	/**
	 * The actual award
	 * @author philipmcmahon
	 *
	 */
	public final class Veteran extends Achievement {

		/**
		 * Default ctr
		 */
		public Veteran() {
			super("Veteran", true);
		}
	}
}
