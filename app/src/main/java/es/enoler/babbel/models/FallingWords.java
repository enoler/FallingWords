package es.enoler.babbel.models;

import android.content.Context;
import es.enoler.babbel.R;
import es.enoler.babbel.helpers.DataHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FallingWords {

	private Context mContext;
	private User mUser;
	private ArrayList<HashMap<String, String>> allWords;

	private String userLanguageKey;
	private String learningLanguageKey;

	public FallingWords() { }

	public FallingWords(Context context, User user) {
		mContext = context;
		mUser = user;

		// I don't like so much the solution below, very vulnerable to errors.
		// The language of the user has to be the same string than the key.
		userLanguageKey = "text_" + mUser.getUserLanguage();
		learningLanguageKey = "text_" + mUser.getLearningLanguage();

		this.allWords = readAllWords();
	}

	/**
	 * Get the questions of the game.
	 * @param numberQuestions Number of desired questions for the game.
	 * @return Array of questions that contains a word, it translation and if it is correct or not.
	 */
	public ArrayList<Question> getQuestions(int numberQuestions) {
		ArrayList<Question> questions = new ArrayList<>();

		for(int i = 0; i < numberQuestions; i++) {
			// Get a random word of the array of words.
			Random rand = new Random();
			int index = rand.nextInt(allWords.size());

			// Create the question.
			Question question = new Question();
			question.setUserLanguageWord(allWords.get(index).get(userLanguageKey));

			// Decide if the question will be correct or not. 0-49 Incorrect, 50-99 Correct.
			int asdf =  rand.nextInt(100);
			boolean isCorrect = asdf > 49;
			question.setCorrectTranslated(isCorrect);

			// If is not correct, get another index
			if(isCorrect) {
				// If it is correct, return the correct translation.
				question.setLearningLanguageWord(allWords.get(index).get(learningLanguageKey));
			} else {
				// If it is not correct, return a random translation. (Being sure that is not the same).
				int incorrectAnswerIndex;

				do { incorrectAnswerIndex = rand.nextInt(allWords.size()); }
				while(incorrectAnswerIndex == index);

				question.setLearningLanguageWord(allWords.get(incorrectAnswerIndex).get(learningLanguageKey));
			}
			questions.add(question);
		}

		return questions;
	}

	/**
	 * Get pairs of words translated in the language of the user and the language of the course.
	 * @return Array of HashMap which contains a word in two different languages.
	 */
	private ArrayList<HashMap<String, String>> readAllWords() {
		ArrayList<HashMap<String, String>> allWords = new ArrayList<>();
		JSONArray words = DataHelper.loadJson(mContext, R.raw.words_v2);

		for (int i = 0; i< words.length(); i++) {
			try {
				HashMap<String, String> wordHashMap = new HashMap<>();
				JSONObject word = (JSONObject) words.get(i);

				// Get data.
				String userLanguageWord = word.getString(userLanguageKey);
				String learningLanguageWord = word.getString(learningLanguageKey);

				// Save into the ArrayList.
				wordHashMap.put(userLanguageKey, userLanguageWord);
				wordHashMap.put(learningLanguageKey, learningLanguageWord);
				allWords.add(wordHashMap);
			} catch(JSONException ex) { ex.printStackTrace(); }
		}

		return allWords;
	}
}
