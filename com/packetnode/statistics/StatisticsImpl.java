package com.packetnode.statistics;

import java.util.*;
import com.packetnode.*;

/**
 * Default implementation of Statistics interface
 * @author philipmcmahon
 * 
 */
public class StatisticsImpl implements Statistics {

	// we will rely on the interface definition for our javadoc
	public String getFormattedStats(String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%sattempted Attacks: %d\n", prefix,
				attemptedAttacks));
		sb.append(String.format("%shits: %d\n", prefix, hits));
		sb.append(String.format("%sdamage Done: %d\n", prefix, damageDone));
		sb.append(String.format("%skills: %d\n", prefix, kills));
		sb.append(String.format("%sspells cast: %d\n", prefix, spellsCast));
		sb.append(String.format("%sspell damage done: %d\n", prefix,
				spellDamageDone));
		sb.append(String.format("%stime played: %s\n", prefix, timePlayed));
		sb.append(String.format("%sattacked by: %s\n", prefix, attackedBy));
		sb.append(String.format("%sfirst hit kills: %s\n", prefix,
				firstHitKills));
		sb.append(String.format("%sassists: %s\n", prefix, assists));

		// new stat
		sb.append(String.format("%sspell hits: %s\n", prefix, spellHits));

		return sb.toString();
	}

	public String getFormattedStats() {
		return getFormattedStats("\t");
	}

	protected int attemptedAttacks;

	public void incAttemptedAttacks() {
		attemptedAttacks++;
	}

	public int getAttemptedAttacks() {
		return attemptedAttacks;
	}

	protected int hits;

	public void incHits() {
		hits++;
	}

	protected int damageDone;

	public void incDamageDone(int damage) {
		damageDone += damage;
	}

	protected int kills = 0;
	
	public void incKills() {
		kills++;
	}

	protected boolean dead = false;

	public void setDead(boolean dead) {
		this.dead = true;
	}

	protected int firstHitKills;

	protected int spellsCast;

	public void incSpellsCast() {
		spellsCast++;
	}

	protected int spellDamageDone;

	public void incSpellDamageDone(int damage) {
		damageDone += damage;
		spellDamageDone += damage;
	}

	protected long timePlayed;

	public void setTimePlayed(long time) {
		if (timePlayed == 0)
			timePlayed = time;
	}

	protected List<Player> attackedBy = new ArrayList<Player>() {
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (Player p : this)
				sb.append(p.getName()).append(",");
			return sb.substring(0, (sb.length() > 0 ? sb.length() - 1 : 0));
		}
	};

	public void addAttackedBy(Player player) {
		attackedBy.add(player);
	}

	public List<Player> getAttackedBy() {
		return attackedBy;
	}

	public void incFirstHitKills() {
		firstHitKills++;

	}

	protected int assists;

	public void incAssists() {
		assists++;
	}

	public int getHits() {
		return hits;
	}

	public int getAssists() {
		return assists;
	}

	public int getDamageDone() {
		return damageDone;
	}

	public int getFirstHitKills() {
		return firstHitKills;
	}

	public int getKills() {
		return kills;
	}

	
	public int getSpellDamageDone() {
		return spellDamageDone;
	}

	public int getSpellsCast() {
		return spellsCast;
	}

	public long getTimePlayed() {
		return timePlayed;
	}

	public boolean isDead() {
		return dead;
	}

	// hits
	protected int spellHits;

	public int getSpellHits() {
		return spellHits;
	}

	public void incSpellHits() {
		spellHits++;
	}

}
