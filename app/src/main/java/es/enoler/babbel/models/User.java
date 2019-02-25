package es.enoler.babbel.models;

public class User {

	private String name;
	private String userLanguage;
	private String learningLanguage;

	public User() { }

	public User(String userName, String userLanguage, String learningLanguage) {
		name = userName;
		this.userLanguage = userLanguage;
		this.learningLanguage = learningLanguage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public String getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(String learningLanguage) {
		this.learningLanguage = learningLanguage;
	}
}
