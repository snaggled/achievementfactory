package com.packetnode.statistics;

import com.packetnode.Player;
import java.util.List;

/**
 * High level statistics interface for game.
 * @author philipmcmahon
 * 
 */
public interface Statistics {

	/**
	 * Pretty print
	 * @param prefix - e.g. "\t"
	 * @return pretty printed stats
	 */
	public String getFormattedStats(String prefix);

	/**
	 * Pretty print, default prefix 
	 * @return - pretty printed stats
	 */
	public String getFormattedStats();

	/**
	 * Get number attempted attacks
	 * @return attempted attacks
	 */
	public int getAttemptedAttacks();

	/**
	 * Increment attempted attacks by 1
	 */
	public void incAttemptedAttacks();

	/**
	 * Increment successful hits by 1
	 */
	public void incHits();

	/**
	 * Get number of successful hits
	 * @return number of successful hits
	 */
	public int getHits();

	/**
	 * Incremement total damage done by damage
	 * @param damage done
	 */
	public void incDamageDone(int damage);

	/**
	 * Get total damage done
	 * @return total damage done
	 */
	public int getDamageDone();

	/**
	 * Increment the number of kills by 1
	 */
	public void incKills();

	/**
	 * Get the number of kills
	 * @return number of kills
	 */
	public int getKills();

	/**
	 * Increment the number of spells cast by 1
	 */
	public void incSpellsCast();

	/**
	 * Get the number of spells cast
	 * @return number of spells cast
	 */
	public int getSpellsCast();

	/**
	 * Increment numbber of first hit kills by 1
	 */
	public void incFirstHitKills();

	/**
	 * Get the number of first hit kills
	 * @return first hit kills
	 */
	public int getFirstHitKills();

	/**
	 * Increment the total spell damage done by damage
	 * @param damage
	 */
	public void incSpellDamageDone(int damage);

	/**
	 * Get the total spell damage done
	 * @return spell damage done
	 */
	public int getSpellDamageDone();

	/**
	 * Set this statistic to dead
	 * @param dead - is dead or not
	 */
	public void setDead(boolean dead);

	/**
	 * Is this statistic dead ?
	 * @return true or false
	 */
	public boolean isDead();

	/**
	 * Set the total time played to time
	 * @param time - total time played
	 */
	public void setTimePlayed(long time);

	/**
	 * Get the total time played
	 * @return total time played
	 */
	public long getTimePlayed();

	/**
	 * Add an attack by player
	 * @param player - player who attacked
	 */
	public void addAttackedBy(Player player);

	/**
	 * Get all the players who have attacked
	 * @return players who have attacked
	 */
	public List<Player> getAttackedBy();

	/**
	 * Increment number of assisted kills by 1
	 */
	public void incAssists();

	/**
	 * Get number of assisted kills
	 * @return number of assists kills
	 */
	public int getAssists();

	// this is our new statistic
	/**
	 * Increment number of successful spell hits by 1
	 */
	public void incSpellHits();

	/**
	 * Get number of successful spell hits
	 * @return number of successful spell hits
	 */
	public int getSpellHits();
}
