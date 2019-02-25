package es.enoler.babbel.helpers;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Helper for interact with data of external files.
 */
public class DataHelper {

	public DataHelper() { }

	public static JSONArray loadJson(Context context, int rawResId) {
		JSONArray json = new JSONArray();
		try {
			InputStream words = context.getResources().openRawResource(rawResId);
			String string = getInputString(words);
			json = new JSONArray(string);
		} catch (JSONException ex)  { ex.printStackTrace(); }

		return json;
	}

	private static String getInputString(InputStream input) {
		StringBuilder stringBuilder = new StringBuilder();
		String line;

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
			while ((line = bufferedReader.readLine()) != null) { stringBuilder.append(line); }
		} catch (IOException ex) { ex.printStackTrace(); }

		return stringBuilder.toString();
	}
}
