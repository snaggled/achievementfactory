package com.packetnode;

import java.util.*;
import com.packetnode.statistics.*;

/**
 * Team, consists of a number of players, generates statistics
 * @author philipmcmahon
 *
 */
public class Team extends CombinedStatisticsImpl {

	// yay for Generic Types
	private List<Player> players = new ArrayList<Player>();
	private String name = null;
	private Random generator = new Random();
	private Game game = null;

	/**
	 * Pretty print
	 * 
	 * @return this teams info
	 */
	public String toLongString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("\tTeam: %s\n", getName()));
		sb.append(String.format("\tAlive Players: %d\n", this
				.getNumberOfAlivePlayers()));
		sb.append(getGameStats().getFormattedStats());
		sb.append("\n");
		// should be using BufferedString or something ?
		return sb.toString();
	}

	/**
	 * Get this teams name
	 * 
	 * @param name
	 *            Team name
	 */
	public Team(String name) {
		this.name = name;
	}

	/**
	 * Get this teams name
	 * 
	 * @return name Team name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Add a player to this team
	 * 
	 * @param player
	 *            - Player to be added
	 * @throws IllegalArgumentException
	 */
	public void addPlayer(Player player) throws IllegalArgumentException {

		// shouldn't really be a RunTimeException
		if (this.players.size() > 5)
			throw new IllegalArgumentException(
					"You can't have more than 5 players in a Team");

		this.players.add(player);
	}

	/**
	 * Remove a player from this team
	 * 
	 * @param player
	 *            - player to be removed
	 * @throws IllegalArgumentException
	 */
	public void removePlayer(Player player) throws IllegalArgumentException {

		// see addPlayer
		if (this.players.size() < 1)
			throw new IllegalArgumentException(
					"You can't have a Team with nobody in it");

		this.players.remove(player);
	}

	/**
	 * Get the players on this team
	 * 
	 * @return - the list of players on this team
	 */
	public List<Player> getPlayers() {
		return this.players;
	}

	/**
	 * Get the number of players on this team
	 * 
	 * @return - number of players on this team
	 */
	public int getNumberOfPlayers() {
		return getPlayers().size();
	}

	/**
	 * Get number of currently alive players on this team
	 * 
	 * @return number of currently alive players
	 */
	public int getNumberOfAlivePlayers() {
		int alivePlayers = 0;
		for (Player p : getPlayers()) {
			if (!p.isDead())
				alivePlayers++;
		}
		return alivePlayers;
	}

	/**
	 * Return a random player from this team
	 * 
	 * @return random player
	 */
	public Player getRandomPlayer() {
		return players.get(generator.nextInt(players.size()));
	}

	/**
	 * Return a random alive player from this team
	 * 
	 * @return random alive player
	 */
	public Player getRandomAlivePlayer() {
		if (this.game == null)
			return null;

		Player alivePlayer = null;
		synchronized (this.game) {
			if (this.game == null || getNumberOfAlivePlayers() == 0)
				return null;
			alivePlayer = getAlivePlayers().get(
					generator.nextInt(getNumberOfAlivePlayers()));
		}
		return alivePlayer;
	}

	/**
	 * Start the game this team will participate in
	 * 
	 * @param game
	 *            - game to be played
	 */
	public void startGame(Game game) {
		// reset the stats because its a new game
		resetStats();

		// and add our statistics as a delegate to each of our players
		// statistics
		for (Player p : getPlayers())
			p.addDelegate(getGameStats());

		this.game = game;
	}

	/**
	 * Get the game currently or recently played
	 * 
	 * @return game currently or recently played
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * Finish current game
	 */
	public void finishGame() {
		incGamesPlayed();

		if (this.equals(getGame().getWinner()))
			incWins();
		updateHistoricalStats();
	}

	/**
	 * Get a list of currently alive players
	 * 
	 * @return currently alive players
	 */
	public List<Player> getAlivePlayers() {
		if (this.game == null)
			throw new IllegalStateException("Team is not in an actual game");

		List<Player> alivePlayers = new ArrayList<Player>();
		for (Player p : getPlayers()) {
			if (!p.isDead())
				alivePlayers.add(p);
		}

		return alivePlayers;
	}

	/**
	 * Return the list of player names in team
	 * 
	 * @return players names in team
	 */
	public String getPlayerNames() {
		String names = "";
		for (Player p : getPlayers()) {
			names = names.concat(p.getName()).concat(",");
		}
		return names
				.substring(0, (names.length() > 0 ? names.length() - 1 : 0));
	}
}
