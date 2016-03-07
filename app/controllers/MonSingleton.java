package controllers;

public class MonSingleton {
	private static MonSingleton instance = null;
	
	private MonSingleton() { }
	
	public static MonSingleton getInstance() {
		if (MonSingleton.instance == null) {
			instance = new MonSingleton();
		}
		return instance;
	}
}