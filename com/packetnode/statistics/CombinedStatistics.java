package com.packetnode.statistics;

/**
 * A combined set of historical and game statistics
 * @author philipmcmahon
 */
public interface CombinedStatistics extends Statistics, HistoricalStatistics {

	/**
	 * Reset the game statistics to default
	 */
	public void resetStats();

	/**
	 * Update (merge) the game statistics into the historical statistics
	 */
	public void updateHistoricalStats();
}
