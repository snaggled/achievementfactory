package com.packetnode.achievements;

import com.packetnode.statistics.*;
import java.util.*;

/**
 * Singleton implementation of achievement factory
 * @author philipmcmahon
 *
 */
public class AchievementFactoryImpl implements AchievementFactory {

	private List<AchievementFactory> factories = new ArrayList<AchievementFactory>();
	private static AchievementFactory singletonFactory = null;

	/**
	 * Singleton - returns only available instance
	 * @return singleton factory
	 */
	public static AchievementFactory getFactory() {
		if (singletonFactory == null) {
			singletonFactory = new AchievementFactoryImpl();
		}
		return singletonFactory;
	}

	private AchievementFactoryImpl() {

		// we can either add these in here or do it later in a superclass
		addFactory(new SharpshooterFactory());
		addFactory(new BruiserFactory());
		addFactory(new VeteranFactory());
		addFactory(new BigWinnerFactory());
		addFactory(new WizardFactory());
	}

	public List<Achievement> getAchievements(CombinedStatistics stats) {
		List<Achievement> result = new ArrayList<Achievement>();
		for (AchievementFactory f : this.factories)
			result.addAll(f.getAchievements(stats));
		return result;
	}

	/**
	 * Add a factory to create new achievements
	 * @param factory - to be considered in processing of stats
	 */
	public void addFactory(AchievementFactory factory) {
		this.factories.add(factory);
	}

	/**
	 * Remove factory from achievement processing
	 * @param factory - to be removed
	 */
	public void removeFactory(Achievement factory) {
		this.factories.remove(factory);
	}
}
