package com.packetnode;

public class SingletonExample {

	/**
	 * Private class variable
	 */
	private static SingletonExample singleton;
	
	/**
	 * This is the only method available to access the class variable.
	 * @return the singleton instance
	 */
	public static SingletonExample onlyInstance() {
		if (singleton == null)
			singleton = new SingletonExample();
		return singleton;
	}
	
	/**
	 * The Ctr should be private
	 */
	private SingletonExample() {
	}

}
