package es.enoler.babbel.models;

public class Question {
	private String userLanguageWord;
	private String learningLanguageWord;
	private boolean isCorrectTranslated;

	public Question() { }

	public Question(String userLanguageWord, String learningLanguageWord, boolean isCorrectTranslated) {
		this.userLanguageWord = userLanguageWord;
		this.learningLanguageWord = learningLanguageWord;
		this.isCorrectTranslated = isCorrectTranslated;
	}

	public String getUserLanguageWord() {
		return userLanguageWord;
	}

	public void setUserLanguageWord(String userLanguageWord) {
		this.userLanguageWord = userLanguageWord;
	}

	public String getLearningLanguageWord() {
		return learningLanguageWord;
	}

	public void setLearningLanguageWord(String learningLanguageWord) {
		this.learningLanguageWord = learningLanguageWord;
	}

	public boolean isCorrectTranslated() {
		return isCorrectTranslated;
	}

	public void setCorrectTranslated(boolean correctTranslated) {
		isCorrectTranslated = correctTranslated;
	}
}
