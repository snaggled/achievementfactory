package com.packetnode.achievements;

import static org.junit.Assert.*;
import com.packetnode.statistics.*;
import java.util.*;

import org.junit.Test;

/**
 * Test our achievement factory mechanism
 * 
 * @author philipmcmahon
 * 
 */
public class AchievementFactoryTest {

	CombinedStatistics stats;
	AchievementFactory factory;

	/**
	 * Test achievement creation
	 */
	@Test
	public void testGetAchievements() {
		stats = new CombinedStatisticsImpl();
		factory = AchievementFactoryImpl.getFactory();

		// should be empty with no stats
		List<Achievement> a = factory.getAchievements(stats);
		assertTrue(a.isEmpty());

		// > 500 damage should equal bruiser award
		stats.incDamageDone(BruiserFactory.DAMAGELIMIT + 1);
		a = factory.getAchievements(stats);
		assertFalse(a.isEmpty());
		// next line is ugly, should have made something static somewhere
		assertTrue(a.contains((new BruiserFactory()).new Bruiser()));

		// 10 attempted attacks and no hits should not result in an additional
		// reward
		// bruiser should still be there, but thats all
		for (int i = 0; i < 10; i++)
			stats.incAttemptedAttacks();
		a = factory.getAchievements(stats);
		assertTrue(a.contains((new BruiserFactory()).new Bruiser()));
		assertTrue(a.size() == 1);

		// adding 9 hits should result in a sharpshooter award
		for (int i = 0; i < 9; i++)
			stats.incHits();
		a = factory.getAchievements(stats);
		assertTrue(a.size() == 2);
		assertTrue(a.contains((new BruiserFactory()).new Bruiser()));
		assertTrue(a.contains((new SharpshooterFactory()).new Sharpshooter()));

		// casting more than 50 spells should result in a sharpshooter award
		for (int i = 0; i < WizardFactory.SPELLLIMIT; i++)
			stats.incSpellHits();
		a = factory.getAchievements(stats);
		assertTrue(a.size() == 3);
		assertTrue(a.contains((new WizardFactory()).new Wizard()));
		assertTrue(a.contains((new BruiserFactory()).new Bruiser()));
		assertTrue(a.contains((new SharpshooterFactory()).new Sharpshooter()));

		// playing 1000 games should result in a veteran award
		for (int i = 0; i < VeteranFactory.GAMELIMIT; i++)
			stats.incGamesPlayed();
		a = factory.getAchievements(stats);
		assertTrue(a.size() == 4);
		assertTrue(a.contains((new BruiserFactory()).new Bruiser()));
		assertTrue(a.contains((new SharpshooterFactory()).new Sharpshooter()));
		assertTrue(a.contains((new VeteranFactory()).new Veteran()));

		// winning 200 games should result in a veteran award
		for (int i = 0; i < BigWinnerFactory.WINLIMIT; i++)
			stats.incWins();
		a = factory.getAchievements(stats);
		assertTrue(a.size() == 5);
		assertTrue(a.contains((new BruiserFactory()).new Bruiser()));
		assertTrue(a.contains((new SharpshooterFactory()).new Sharpshooter()));
		assertTrue(a.contains((new VeteranFactory()).new Veteran()));
		assertTrue(a.contains((new BigWinnerFactory()).new BigWinner()));
	}

}
