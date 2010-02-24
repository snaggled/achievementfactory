package com.packetnode.achievements;

import java.util.ArrayList;
import java.util.List;

import com.packetnode.statistics.CombinedStatistics;

/**
 * Create achievement if more than 50 spells cast
 * @author philipmcmahon
 *
 */
public class WizardFactory implements AchievementFactory {

	/**
	 * Number of spells cast before this achievement is created
	 */
	public static final int SPELLLIMIT = 50;

	public List<Achievement> getAchievements(CombinedStatistics stats) {
		List<Achievement> result = new ArrayList<Achievement>();

		// if we have something to work with
		if (stats.getSpellHits() >= SPELLLIMIT)
			result.add(new Wizard());

		return result;
	}

	/**
	 * The actual award
	 * @author philipmcmahon
	 *
	 */
	public final class Wizard extends Achievement {

		/**
		 * Default ctr
		 */
		public Wizard() {
			super("Wizard");
		}
	}

}
