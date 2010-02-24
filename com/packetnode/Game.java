package com.packetnode;

import java.util.*;

/**
 * Game object, take 2 teams and plays game
 * 
 * @author philipmcmahon
 * 
 */
public class Game extends Thread {

	/**
	 * Game status
	 */
	public enum Status {
		NOTSTARTED, STARTED, FINISHED
	}

	private List<Team> teams = new ArrayList<Team>();
	private Status status;
	private Random generator = new Random();
	private long startTime;
	private long finishTime;

	private Game() {
		setStatus(Status.NOTSTARTED);
	}

	/**
	 * Default Constructor
	 * 
	 * @param a
	 *            - team a
	 * @param b
	 *            - team b
	 */
	public Game(Team a, Team b) {
		// we have to call this explicitly
		this();
		this.teams.add(a);
		this.teams.add(b);
	}

	/**
	 * What time did this game start ?
	 * 
	 * @return game starttime
	 */
	public long getStartTime() {
		return this.startTime;
	}

	/**
	 * What time did this game finish
	 * 
	 * @return game finishTime
	 */
	public long getFinishTime() {
		return this.finishTime;
	}

	/**
	 * Return a random team from the game
	 * 
	 * @return random team
	 */
	public Team getRandomTeam() {
		return getTeams().get(generator.nextInt(getTeams().size()));
	}

	/**
	 * Return the team of a given player
	 * 
	 * @param player
	 *            - player we want the team of
	 * @return team of player
	 */
	public Team getTeam(Player player) {
		return getTeams().get(0).getPlayers().contains(player) ? getTeams()
				.get(0) : getTeams().get(1);
	}

	/**
	 * Return the other team in the game
	 * 
	 * @param team
	 *            - we do not want
	 * @return other team
	 */
	public Team getOtherTeam(Team team) {
		return teams.indexOf(team) == 1 ? teams.get(0) : teams.get(1);
	}

	private void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Get game status
	 * 
	 * @return game status
	 */
	public Status getStatus() {
		return this.status;
	}

	/**
	 * Add a team to this game
	 * 
	 * @param team
	 *            - team to be added
	 * @throws IllegalArgumentException
	 */
	public void addTeam(Team team) throws IllegalArgumentException {
		if (this.teams.size() > 1)
			throw new IllegalArgumentException(
					"Cannot have more than 2 teams in a Game");

		this.teams.add(team);
	}

	/**
	 * Remove a team from this game
	 * 
	 * @param team
	 *            - team to be removed
	 * @throws IllegalArgumentException
	 */
	public void removeTeam(Team team) throws IllegalArgumentException {
		if (this.teams.size() < 0)
			throw new IllegalArgumentException(
					"There are no teams in this Game");

		this.teams.remove(team);
	}

	/**
	 * 
	 */
	private void begin() {
		setStatus(Status.STARTED);
		this.startTime = System.currentTimeMillis();

		// need to make sure everybody is alive
		for (Team t : this.teams)
			for (Player p : t.getPlayers())
				p.resetHitPoints();

		for (Team t : this.teams) {
			t.startGame(this);
			for (Player p : t.getPlayers())
				p.startGame(this);
		}
	}

	/**
	 * 
	 */
	private void finish() {
		setStatus(Status.FINISHED);
		this.finishTime = System.currentTimeMillis();

		for (Team t : this.teams) {
			t.finishGame();
			for (Player p : t.getPlayers())
				p.finishGame();
		}

	}

	/**
	 * Don't call this, call startGame instead. This is public because it
	 * inherits from Thread
	 * 
	 * @see Thread
	 */
	public void run() {

		synchronized (this) {
			begin();
			while (true) {

				// this is where we would wait on user events, such as attack or
				// cast spell
				try {
					this.wait();

				} catch (InterruptedException e) {
					// tbd
				}

				if (teams.get(0).getNumberOfAlivePlayers() == 0
						|| teams.get(1).getNumberOfAlivePlayers() == 0)
					break;
			}
			finish();
		}
	}

	/**
	 * Start the game
	 */
	public void startGame() {
		if (this.teams.size() != 2)
			throw new IllegalArgumentException(
					"You need 2 teams to start a Game");
		if (teams.get(0).getNumberOfPlayers() != teams.get(1)
				.getNumberOfPlayers())
			throw new IllegalArgumentException(
					"Number of players in  both teams is not equal");
		if (teams.get(0).getNumberOfPlayers() < 3)
			// 3 should be a defined constant - bad magic number!
			throw new IllegalArgumentException(
					"Number of players in teams cannot be less than 3");

		this.start();
	}

	/**
	 * Get list of teams involved in the game
	 * 
	 * @return list of teams playing the game
	 */
	public List<Team> getTeams() {
		return this.teams;
	}

	/**
	 * Get the winner of the game. Game needs to be a FINISHED state
	 * 
	 * @return winner of game
	 */
	public Team getWinner() {
		if (this.status != Status.FINISHED)
			throw new IllegalStateException("Game is not finished");

		for (Team t : getTeams()) {
			if (t.getNumberOfAlivePlayers() > 0)
				return t;
		}

		// should never happen
		return null;
	}

	/**
	 * Get the loser of the game
	 * 
	 * @return loser of game
	 */
	public Team getLoser() {
		return this.teams.get(0) == getWinner() ? this.teams.get(1)
				: this.teams.get(0);
	}
}
