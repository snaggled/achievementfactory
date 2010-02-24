package com.packetnode;

import com.packetnode.statistics.*;
import java.util.*;
import com.packetnode.achievements.*;

/**
 * Core Player class.
 * 
 * @author philipmcmahon
 * 
 */
public class Player extends CombinedStatisticsImpl implements
		CombinedStatistics {

	private static final int HITPOINTS = 1000;

	/**
	 * There are no setter methods on hitPoints. The key here is that the only
	 * object who has the authority to modify a persons hitPoints is that person
	 * object.
	 */
	int hitPoints = HITPOINTS;
	String name = null;
	private Random generator = new Random();
	private Game game = null;
	private Map<Achievement, Integer> achievements = new HashMap<Achievement, Integer>() {
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<Achievement, Integer> m : this.entrySet())
				sb.append(m.getKey().getName()).append(" -> ").append(
						m.getValue()).append(",");
			return sb.substring(0, (sb.length() > 0 ? sb.length() - 1 : 0));
		}

	};

	/**
	 * Return list of this players achievements.
	 * 
	 * @return HashMap containing achievement to (number of times received)
	 */
	public Map<Achievement, Integer> getAchievements() {
		return this.achievements;
	}

	/**
	 * Add an achievement award to this player
	 * 
	 * @param achievement
	 */
	public void addAchievement(Achievement achievement) {
		if (this.achievements.containsKey(achievement)) {
			if (!achievement.isOnceOnly())
				this.achievements.put(achievement, this.achievements
						.get(achievement) + 1);
		} else {
			this.achievements.put(achievement, 1);
		}

	}

	/**
	 * Pretty print this Players info
	 * 
	 * @return this players info
	 */
	public String toLongString() {
		String prefix = "\t\t";
		StringBuffer sb = new StringBuffer("\n");
		sb.append(String.format("%sPlayer: %s\n", prefix, getName()));
		sb.append(String.format("%sHitpoints: %d\n", prefix, getHitPoints()));
		sb.append(String.format("%sAchievements: %s\n", prefix,
				getAchievements()));
		sb.append(getFormattedStats(prefix));
		sb.append("\n");
		// should be using BufferedString or something ?
		return sb.toString();
	}

	/**
	 * Default constructor
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * Return current number of remaining hitPoints
	 * 
	 * @return hitpoints remaining
	 */
	public int getHitPoints() {
		return this.hitPoints;
	}

	/**
	 * Set number of hitPoints back to max
	 */
	public void resetHitPoints() {
		this.hitPoints = HITPOINTS;
	}

	/**
	 * Is this player still alive ?
	 * 
	 * @return boolean - true or false
	 */
	public boolean isDead() {
		return this.hitPoints <= 0;
	}

	/**
	 * Get this players name
	 * 
	 * @return player name
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Get this players name
	 * 
	 * @return player name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Start a new game
	 * 
	 * @param game
	 *            - game to be played
	 */
	public void startGame(Game game) {
		// we reset the game stats at the start of each game
		resetStats();
		this.game = game;
	}

	/**
	 * Finish this players current game
	 */
	public void finishGame() {
		// this.game = null;
		setTimePlayed(this.game.getFinishTime() - this.game.getStartTime());

		// this is our hook
		updateHistoricalStats();
		incGamesPlayed();
		if (getGame().getTeam(this).equals(getGame().getWinner()))
			incWins();

	}

	/**
	 * This players current or most recent game (may be null)
	 * 
	 * @return current or most recent game
	 */
	public Game getGame() {
		return this.game;
	}

	// core method for calculating damage
	private int damagedBy(Player player) {
		// player is a var in case we wanted to calculate some probability
		// but we're just using a random for this example
		if (this.game == null)
			throw new IllegalStateException("Player is not in an actual game");

		if (isDead())
			throw new IllegalStateException("Player is already dead");

		int damage = 0;

		// we're synchronized on the game object itself
		synchronized (this.game) {

			// we'll call it 50% change of hitting
			boolean success = generator.nextInt(2) == 0 ? false : true;
			if (success)
				damage = generator.nextInt(1200);
			if (damage > getHitPoints())
				damage = getHitPoints();
			this.hitPoints -= damage;

			// tbd
			this.game.notifyAll(); // more than 1 ? Probably not
		}

		return damage;
	}

	// I attacked you!
	protected synchronized int attack(Player player) {
		// System.out.printf("Player %s is attacking player %s\n", this,
		// player);
		if (isDead())
			throw new IllegalStateException("Player is dead and cannot attack");

		// and attack
		int damage = player.attackedBy(this);

		// stats
		incAttemptedAttacks();
		incDamageDone(damage);
		incKills(player);

		return damage;
	}

	// You attacked me!
	protected int attackedBy(Player player) {
		int damage = damagedBy(player);
		addAttackedBy(player);
		System.out.printf("Player %s attacked player %s for %d damage\n",
				player, this, damage);
		if (isDead()) {
			setDead(true);
			setTimePlayed(this);

			// this.getGameStats().setTimePlayed(System.currentTimeMillis() -
			// this.game.getStartTime());
			System.out
					.printf("Player %s has killed Player %s!\n", player, this);
		}
		return damage;
	}

	// I cast a spell on you!
	protected synchronized int castSpell(Player player) {
		// System.out.printf("Player %s is casting spell on player %s\n", this,
		// player);
		if (isDead())
			throw new IllegalStateException(
					"Player is dead and cannot cast spell");

		// stats
		incSpellsCast();

		int damage = player.spellCastBy(this);

		// stats
		incSpellDamageDone(damage);
		incKills(player);

		return damage;
	}

	// you cast a spell on me!
	protected synchronized int spellCastBy(Player player) {
		// maybe we check here to see if we're resistant to spells!
		int damage = damagedBy(player);

		// if (self.game.isVerbose())
		System.out.printf("Player %s cast spell on player %s for %d damage\n",
				player, this, damage);
		addAttackedBy(player);

		// addAttackedBy(player);
		if (isDead()) {

			setDead(true);
			setTimePlayed(this);

			// if (this.game.verbose())
			System.out
					.printf("Player %s has killed Player %s!\n", player, this);
		}

		return damage;
	}
}
