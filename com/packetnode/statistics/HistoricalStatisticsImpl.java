package com.packetnode.statistics;

/**
 * This is an implementation of the HistoricalStatistics interface
 * @author philipmcmahon
 *
 */
public class HistoricalStatisticsImpl extends StatisticsImpl implements
		HistoricalStatistics {

	public String getFormattedStats(String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getFormattedStats(prefix));
		sb.append(String.format("%sgames played: %d\n", prefix, gamesPlayed));
		sb.append(String.format("%swins: %d\n", prefix, wins));
		return sb.toString();
	}

	private int gamesPlayed;

	public void incGamesPlayed() {
		this.gamesPlayed++;
	}

	private int wins;

	public void incWins() {
		this.wins++;
	}

	public int getWins() {
		return this.wins;
	}

	public int getGamesPlayed() {
		return this.gamesPlayed;
	}

	public void update(Statistics stats) {
		this.attemptedAttacks += stats.getAttemptedAttacks();
		this.damageDone += stats.getDamageDone();
		this.hits += stats.getHits();
		this.kills += stats.getKills();
		this.spellsCast += stats.getSpellsCast();
		this.spellDamageDone += stats.getSpellDamageDone();
		this.timePlayed += stats.getTimePlayed();

		// we could append this but we're not
		this.attackedBy = stats.getAttackedBy();
		this.firstHitKills += stats.getFirstHitKills();
		this.assists += stats.getAssists();

		// new stat
		this.spellHits += stats.getSpellHits();
	}
}