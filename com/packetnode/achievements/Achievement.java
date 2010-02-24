package com.packetnode.achievements;

/**
 * Default achievement class, subclass this to create a new achievement
 * @author philipmcmahon
 *
 */
public abstract class Achievement {

	private String name;
	private boolean onceOnly;

	/**
	 * Default Constructor
	 * @param name - name of achievement
	 */
	public Achievement(String name) {
		this.name = name;
	}

	/**
	 *  May not be necessary but since I overrid hashCode, made sense
	 *  if its going to be used in a HashMap
	 */
	public boolean equals(Object o) {
		return o.getClass().equals(this.getClass())
				&& this.getName().equals(((Achievement) o).getName());
	}

	/**
	 * We used the name as the key
	 */
	public int hashCode() {
		return this.getName().hashCode();
	}

	/**
	 * Constructor
	 * @param name
	 * @param onceOnly - should this achievement be awarded only once ?
	 */
	public Achievement(String name, boolean onceOnly) {
		this(name);
		this.onceOnly = onceOnly;
	}

	/**
	 * Should this achievement be awarded only once ?
	 * @return only once 
	 */
	public boolean isOnceOnly() {
		return this.onceOnly;
	}

	/**
	 * Get name of achievemewnt
	 * @return name
	 */
	public String getName() {
		return name;
	}

}
