package com.packetnode;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.packetnode.Game.Status;
import com.packetnode.achievements.Achievement;
import com.packetnode.achievements.AchievementFactory;
import com.packetnode.achievements.AchievementFactoryImpl;

/**
 * Application driver - creates 2 teams, each with 5 players, then repeatedly
 * runs a game 10 times with those params.
 * 
 * @see Game
 * @see Player
 * @see Team
 * @author philipmcmahon
 * 
 */
public class Driver {

	Random generator = new Random();

	Team a = null;
	Team b = null;
	Set<Team> teams = new HashSet<Team>();

	/**
	 * Default constructor
	 * 
	 * @param totalPlayers
	 *            - total players in game
	 */
	public Driver(int totalPlayers) {

		a = new Team("a");
		b = new Team("b");
		int numPlayers = totalPlayers / 2;

		for (int i = 0; i < numPlayers; i++) {
			a.addPlayer(new Player(String.valueOf(i)));
		}

		for (int i = numPlayers; i < totalPlayers; i++) {
			b.addPlayer(new Player(String.valueOf(i)));
		}

		teams.add(a);
		teams.add(b);

		System.out.printf("Team a has the following players: %s\n", a
				.getPlayerNames());
		System.out.printf("Team b has the following players: %s\n", b
				.getPlayerNames());
	}

	/**
	 * 
	 * @throws InterruptedException
	 */
	private void play() throws InterruptedException {

		Game g = new Game(a, b);
		g.startGame();

		// we'll give it a chance to start up
		while (!g.getStatus().equals(Status.STARTED)) {
			Thread.sleep(100);
			// g.yield();
		}

		while (!g.getStatus().equals(Status.FINISHED)) {
			// pick a random (alive) person from a random team
			Team team = g.getRandomTeam();
			Player attacker = team.getRandomAlivePlayer();

			// and random (alive) person from other team
			Team other = g.getOtherTeam(team);
			Player opponent = other.getRandomAlivePlayer();

			if (attacker != null && opponent != null) {
				// and attack or cast a spell
				if (generator.nextInt(2) == 0) {
					attacker.castSpell(opponent);
				} else {
					attacker.attack(opponent);
				}
			}
		}

		System.out
				.printf(
						"Team %s is the winner with %d players alive, team %s is the loser with %d\n",
						g.getWinner().getName(), g.getWinner()
								.getNumberOfAlivePlayers(), g.getLoser()
								.getName(), g.getLoser()
								.getNumberOfAlivePlayers());

		for (Team t : g.getTeams()) {
			System.out.printf("%s", t.toLongString());
			for (Player p : t.getPlayers())
				System.out.printf("%s", p.toLongString());
		}
	}

	private void getAchievements() {
		// time to hand out awards
		AchievementFactory factory = AchievementFactoryImpl.getFactory();

		for (Team t : teams)
			for (Player p : t.getPlayers()) {
				List<Achievement> awards = factory.getAchievements(p);
				for (Achievement a : awards) {
					p.addAchievement(a);
					System.out.printf("Player %s got %s achievement\n", p
							.getName(), a.getName());
				}
			}
	}

	/**
	 * @param args
	 *            - no args used
	 */
	public static void main(String[] args) throws InterruptedException {

		Driver driver = new Driver(10);
		for (int i = 0; i < 100; i++) {
			driver.play();
			driver.getAchievements();
		}
	}

}
