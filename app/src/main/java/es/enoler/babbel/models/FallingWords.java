package es.enoler.babbel.models;

import android.content.Context;
import es.enoler.babbel.R;
import es.enoler.babbel.helpers.DataHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FallingWords {

	private Context mContext;
	private User mUser;
	private ArrayList<HashMap<String, String>> allWords;

	public FallingWords() { }

	public FallingWords(Context context, User user) {
		mContext = context;
		mUser = user;
		this.allWords = readAllWords();
	}

	private ArrayList<HashMap<String, String>> readAllWords() {
		ArrayList<HashMap<String, String>> allWords = new ArrayList<>();
		JSONArray words = DataHelper.loadJson(mContext, R.raw.words_v2);

		// I don't like so much this solution, very vulnerable to errors.
		// The language of the user has to be the same than the key.
		String userLanguageKey = "text_" + mUser.getUserLanguage();
		String learningLanguageKey = "text_" + mUser.getLearningLanguage();
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
			} catch(JSONException ex) {
				ex.printStackTrace();
			}
		}

		return allWords;
	}
}
