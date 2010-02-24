package com.packetnode.statistics;

import com.packetnode.*;

import java.util.*;

/**
 * Default Implementation. This contains both a HistoricalStatistics and
 * Statistics object which it delegates to accordingly.
 * 
 * @author philipmcmahon
 * 
 */
public class CombinedStatisticsImpl implements CombinedStatistics {

	private Statistics stats = new StatisticsImpl();
	private HistoricalStatistics hStats = new HistoricalStatisticsImpl();
	private Set<CombinedStatistics> delegates = new HashSet<CombinedStatistics>();

	public String getFormattedStats(String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s--- Game Stats ---\n", prefix));
		sb.append(stats.getFormattedStats(prefix));
		sb.append(String.format("%s--- Historical Stats ---\n", prefix));
		sb.append(hStats.getFormattedStats(prefix));
		return sb.toString();
	}

	public void resetStats() {
		stats = new StatisticsImpl();
	}

	/**
	 * Delegates is the wrong word, but this is a list of Statistic interfaces
	 * who also want to be updated when this object is updated.
	 * @param delegate - listener who wants to be called when an attribute is updated
	 */

	public void addDelegate(CombinedStatistics delegate) {
		this.delegates.add(delegate);
	}

	/**
	 * Remove this delegate from the watchers list
	 * @param delegate - to be removed
	 */
	public void removeDelegate(CombinedStatistics delegate) {
		this.delegates.remove(delegate);
	}

	public String getFormattedStats() {
		return getFormattedStats("\t");
	}

	public void incAttemptedAttacks() {
		stats.incAttemptedAttacks();
		for (Statistics s : this.delegates)
			s.incAttemptedAttacks();
	}

	public void incDamageDone(int damage) {
		if (damage > 0) {
			stats.incHits();
			for (Statistics s : this.delegates)
				s.incHits();
		}

		stats.incDamageDone(damage);
		for (Statistics s : this.delegates)
			s.incDamageDone(damage);
	}

	public void incHits() {
		stats.incHits();
		for (Statistics s : this.delegates)
			s.incHits();
	}

	public void incKills() {
		stats.incKills();
		for (Statistics s : this.delegates)
			s.incKills();
	}

	/**
	 * Check and see if player is dead before incrementing kills
	 * @param player - who may or may not be dead
	 */
	public void incKills(Player player) {
		if (player.isDead())
			incKills();
	}

	public void incSpellDamageDone(int damage) {
		stats.incSpellDamageDone(damage);
		for (Statistics s : this.delegates)
			s.incSpellDamageDone(damage);

		// new - we could also have taken a factory approach like achievements
		if (damage > 0)
			incSpellHits();
	}

	public void incSpellsCast() {
		stats.incSpellsCast();
		for (Statistics s : this.delegates)
			s.incSpellsCast();
	}

	public void setTimePlayed(long time) {
		stats.setTimePlayed(time);
	}

	/**
	 * Check and see if player is dead before setting time
	 * @param player who may or may not be dead
	 */
	public void setTimePlayed(Player player) {
		long timePlayed = System.currentTimeMillis()
				- player.getGame().getStartTime();
		if (player.isDead())
			setTimePlayed(timePlayed);
		for (Statistics s : this.delegates)
			s.setTimePlayed(timePlayed);
	}

	/**
	 * Return the current game statistics
	 * @return current game statistic
	 */
	public CombinedStatistics getGameStats() {
		return this;
	}

	public void setDead(boolean dead) {
		stats.setDead(dead);
		if (dead) {
			// if theres only one person who attacked,
			// that person gets a firstHitKill
			if (stats.getAttackedBy().size() == 1) {
				stats.getAttackedBy().iterator().next().incFirstHitKills();

			}
			// everybody involved gets an assist
			else {
				// uniqify it first
				for (Player p : stats.getAttackedBy())
					p.incAssists();
			}
		}
	}

	public void addAttackedBy(Player player) {
		stats.addAttackedBy(player);
		for (Statistics s : this.delegates)
			s.addAttackedBy(player);
	}

	public void incFirstHitKills() {
		stats.incFirstHitKills();
		for (Statistics s : this.delegates)
			s.incFirstHitKills();
	}

	public void incAssists() {
		stats.incAssists();
		for (Statistics s : this.delegates)
			s.incAssists();
	}

	public List<Player> getAttackedBy() {
		return stats.getAttackedBy();
	}

	public void incGamesPlayed() {
		hStats.incGamesPlayed();
	}

	public void incWins() {
		hStats.incWins();
	}

	public void update(Statistics stats) {
		hStats.update(stats);
	}

	public int getHits() {
		return stats.getHits();
	}

	public int getAttemptedAttacks() {
		return stats.getAttemptedAttacks();
	}

	/**
	 * Return the historical statistics
	 * @return historical statistics
	 */
	public HistoricalStatistics getHistoricalStat() {
		return hStats;
	}

	public void updateHistoricalStats() {
		hStats.update(stats);
	}

	public int getAssists() {
		return stats.getAssists();
	}

	public int getDamageDone() {
		return stats.getDamageDone();
	}

	public int getFirstHitKills() {
		return stats.getFirstHitKills();
	}

	public int getKills() {
		return stats.getKills();
	}

	public int getSpellDamageDone() {
		return stats.getSpellDamageDone();
	}

	public int getSpellsCast() {
		return stats.getSpellsCast();
	}

	public long getTimePlayed() {
		return stats.getTimePlayed();
	}

	public boolean isDead() {
		return stats.isDead();
	}

	public int getWins() {
		return hStats.getWins();
	}

	public int getGamesPlayed() {
		return hStats.getGamesPlayed();
	}

	public int getSpellHits() {
		return stats.getSpellHits();
	}

	public void incSpellHits() {
		stats.incSpellHits();
		for (Statistics s : this.delegates)
			s.incSpellHits();
	}
}
