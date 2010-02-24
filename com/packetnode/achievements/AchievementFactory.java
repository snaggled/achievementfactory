package com.packetnode.achievements;

import java.util.List;
import com.packetnode.statistics.CombinedStatistics;

/**
 * Achievement factory interface
 * @author philipmcmahon
 *
 */
public interface AchievementFactory {

	/**
	 * Pass in a set of stats, receive a list of relevant achievements
	 * @param stats - both historical and game-specific
	 * @return list of relevant achievements
	 */
	public List<Achievement> getAchievements(CombinedStatistics stats);
}
