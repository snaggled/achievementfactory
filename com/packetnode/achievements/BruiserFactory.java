package com.packetnode.achievements;

import java.util.ArrayList;
import java.util.List;

import com.packetnode.statistics.CombinedStatistics;

/**
 * Create an achievement if more than 500 points of damage in a single game
 * @author philipmcmahon
 *
 */
public class BruiserFactory implements AchievementFactory {

	/**
	 * Damage in one game necesarry to get this achievement
	 */
	public static final int DAMAGELIMIT = 500;

	public List<Achievement> getAchievements(CombinedStatistics stats) {
		List<Achievement> result = new ArrayList<Achievement>();

		// if we have something to work with
		if (stats.getDamageDone() > DAMAGELIMIT)
			result.add(new Bruiser());

		return result;
	}

	/**
	 * The actual award
	 * @author philipmcmahon
	 *
	 */
	public final class Bruiser extends Achievement {

		/**
		 * Default ctr
		 */
		public Bruiser() {
			super("Bruiser");
		}
	}

}
