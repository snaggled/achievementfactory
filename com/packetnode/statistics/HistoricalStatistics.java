package com.packetnode.statistics;

/**
 * High level historical statistics that span multiple games.
 * @author philipmcmahon
 * 
 */
public interface HistoricalStatistics extends Statistics {

	/**
	 * Pretty print
	 * @param prefix - e.g. "\t"
	 * @return pretty printed stats
	 */
	public String getFormattedStats(String prefix);

	/**
	 * Increment the number of games played by 1
	 */
	public void incGamesPlayed();

	/**
	 * Get the total number of games played
	 * @return number of games played
	 */
	public int getGamesPlayed();

	/**
	 * Increment the number of games won by 1
	 */
	public void incWins();

	/**
	 * Get the total number of games won
	 * @return number of games won
	 */
	public int getWins();

	/**
	 * Merge a set of game statistics into this set of historical statistics
	 * @param stats - game statistics to be merged
	 */
	public void update(Statistics stats);

}
