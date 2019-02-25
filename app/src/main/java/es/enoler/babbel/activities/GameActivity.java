package es.enoler.babbel.activities;

import android.annotation.SuppressLint;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import es.enoler.babbel.R;
import es.enoler.babbel.models.FallingWords;
import es.enoler.babbel.models.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {

	/**
	 * Some older devices needs a small delay between UI widget updates
	 * and a change of the status and navigation bar.
	 */
	private static final int UI_ANIMATION_DELAY = 100;

	// The idea of these constants is to make possible use the game with a different
	// languages configuration. In a real environment, they would be taken depending on the user.
	private static final String USER_LANGUAGE = "spa";
	private static final String LEARNING_LANGUAGE = "eng";
	private static final String USER_NAME = "Enol";

	private final Handler mHideHandler = new Handler();
	private User user;
	private View mContentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		mContentView = findViewById(R.id.tv_falling_word);

		setFullScreen();
		user = new User(USER_NAME, USER_LANGUAGE, LEARNING_LANGUAGE);
		initGame();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		setFullScreen();
	}

	private void initGame() {
		FallingWords game = new FallingWords(this, user);
	}

	//region Full screen setup

	private final Runnable hideUiElements = new Runnable() {
		@SuppressLint("InlinedApi")
		@Override
		public void run() {
			// Delayed removal of status and navigation bar
			mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
					View.SYSTEM_UI_FLAG_FULLSCREEN |
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
					View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}
	};

	private void setFullScreen() {
		// Hide UI first.
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) { actionBar.hide(); }

		// Schedule a runnable to remove the status and navigation bar after a delay.
		mHideHandler.postDelayed(hideUiElements, UI_ANIMATION_DELAY);
	}

	//endregion
}
