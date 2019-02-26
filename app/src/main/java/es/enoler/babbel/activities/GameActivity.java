package es.enoler.babbel.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.core.content.ContextCompat;
import es.enoler.babbel.R;
import es.enoler.babbel.models.FallingWords;
import es.enoler.babbel.models.Question;
import es.enoler.babbel.models.User;

import java.util.ArrayList;

/**
 * Screen of the FallingWords game.
 */
public class GameActivity extends AppCompatActivity {

	/**
	 * Some older devices needs a small delay between UI widget updates
	 * and a change of the status and navigation bar.
	 */
	private static final int UI_HIDE_ANIMATION_DELAY = 100;

	// The idea of these constants is to make possible use the game with a different
	// languages configuration. In a real environment, they would be taken depending on the user.
	private static final String USER_LANGUAGE = "spa";
	private static final String LEARNING_LANGUAGE = "eng";
	private static final String USER_NAME = "Enol";
	private final int RESPONSE_TIME_MILLISECONDS = 10000;
	private final int SUCCESFUL_EDGE = 5;
	private final int NUMBER_QUESTIONS = 10;

	private static final String POINTS_PLACEHOLDER = "__POINTS__";

	private final Handler mHideHandler = new Handler();
	private User mUser;
	private View mContentView;
	private ArrayList<Question> mQuestions;
	private FallingWords mGame;
	private CountDownTimer mCounterDown;

	private TextView tvTime;
	private TextView tvPoints;
	private TextView tvSuggestedWord;
	private TextView tvInstructions;
	private ImageButton ibIncorrect;
	private ImageButton ibCorrect;
	private Button btStart;
	private TextView tvFallingWord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		mContentView = findViewById(R.id.tv_falling_word);

		mQuestions = new ArrayList<>();

		initViews();

		btStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { onClickStartButton(); }
		});

		ibCorrect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int index = mGame.getQuestionIndex();
				if(index < NUMBER_QUESTIONS)
					selectResponse(mQuestions.get(index).isCorrectTranslated());
			}
		});

		ibIncorrect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int index = mGame.getQuestionIndex();
				if(index < NUMBER_QUESTIONS)
					selectResponse(!mQuestions.get(index).isCorrectTranslated());
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		setFullScreen();
	}

	/**
	 * If is correct show during 300ms the feedback of the response, and then change the question.
	 * @param isCorrect If the answer was correct or not.
	 */
	private void selectResponse(boolean isCorrect) {
		mGame.setAnsweredQuestion(true);
		if(isCorrect) {
			tvFallingWord.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.succesful));
			incrementPoint();
		} else {
			tvFallingWord.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.unsuccesful));
		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				tvFallingWord.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
				changeQuestion();
			}
		}, 300);
	}

	/**
	 * Animation for the falling word.
	 */
	private void animateObject() {
		tvFallingWord.setTranslationY(0);
		ObjectAnimator animation =
				ObjectAnimator.ofFloat(tvFallingWord, "translationY",
						((ViewGroup) tvFallingWord.getParent()).getHeight());
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(10000);
		animation.start();
		animation.setAutoCancel(true);
		animation.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) { }

			@Override
			public void onAnimationEnd(Animator animation) {
				if (!mGame.isAnsweredQuestion()) changeQuestion();
			}

			@Override
			public void onAnimationCancel(Animator animation) { }

			@Override
			public void onAnimationRepeat(Animator animation) { }
		});
		mGame.setAnsweredQuestion(false);
	}

	/**
	 * Change the question if there is still more. Otherwise, it finish the game.
	 */
	private void changeQuestion() {
		startCounterDown();

		int index = mGame.getQuestionIndex() + 1;
		mGame.setQuestionIndex(index);
		if(index < NUMBER_QUESTIONS) {
			tvSuggestedWord.setText(mQuestions.get(index).getUserLanguageWord());
			tvFallingWord.setText(mQuestions.get(index).getLearningLanguageWord());
			animateObject();
		} else onFinishGame();
	}

	private void incrementPoint(){
		mGame.setPoints(mGame.getPoints() + 1);
		String startingPoints = getString(R.string.points)
				.replace(POINTS_PLACEHOLDER, Integer.toString(mGame.getPoints()));
		tvPoints.setText(startingPoints);
	}

	private CountDownTimer initCountdown() {
		return new CountDownTimer(RESPONSE_TIME_MILLISECONDS, 1000) {
			public void onTick(long millisUntilFinished) {
				tvTime.setText(Long.toString(millisUntilFinished / 1000));
			}

			public void onFinish() { }
		};
	}

	/**
	 * Reset parameters and read new random questions.
	 */
	private void initGame() {
		// Initialize game.
		mGame = new FallingWords(this, mUser);
		mQuestions = mGame.getQuestions(NUMBER_QUESTIONS);

		// Start in 0.
		incrementPoint();
		startCounterDown();

		// Initial words.
		tvSuggestedWord.setText(mQuestions.get(mGame.getQuestionIndex()).getUserLanguageWord());
		tvFallingWord.setText(mQuestions.get(mGame.getQuestionIndex()).getLearningLanguageWord());
		animateObject();
	}

	private void initViews() {
		btStart = findViewById(R.id.bt_start);
		tvTime = findViewById(R.id.tv_countdown);
		tvPoints = findViewById(R.id.tv_points);
		tvSuggestedWord = findViewById(R.id.tv_suggested_word);
		tvInstructions = findViewById(R.id.tv_instructions);
		ibIncorrect = findViewById(R.id.ib_incorrect);
		ibCorrect = findViewById(R.id.ib_correct);
		tvFallingWord = findViewById(R.id.tv_falling_word);
	}

	/**
	 * Set up the game screen and start the game.
	 */
	private void onClickStartButton() {
		// Set views.
		btStart.setVisibility(View.GONE);
		tvTime.setVisibility(View.VISIBLE);
		tvPoints.setVisibility(View.VISIBLE);
		tvInstructions.setVisibility(View.GONE);
		ibCorrect.setVisibility(View.VISIBLE);
		ibIncorrect.setVisibility(View.VISIBLE);
		tvFallingWord.setVisibility(View.VISIBLE);

		// Start game.
		mUser = new User(USER_NAME, USER_LANGUAGE, LEARNING_LANGUAGE);
		initGame();
	}

	/**
	 * Show the result and set up again the main screen.
	 */
	private void onFinishGame() {
		// Show the result
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(R.string.result_label));

		// Customize message depending on the result.
		int resultMessageId;
		if(mGame.getPoints() >= SUCCESFUL_EDGE) resultMessageId = R.string.result_message_successful;
		else resultMessageId = R.string.result_message_unsuccessful;

		String resultMessage = getString(resultMessageId)
				.replace(POINTS_PLACEHOLDER, Integer.toString(mGame.getPoints()));
		alertDialog.setMessage(resultMessage);

		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok),
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }
			});
		alertDialog.setCancelable(false);
		alertDialog.show();
		setFullScreen();

		// Set views.
		btStart.setVisibility(View.VISIBLE);
		tvTime.setVisibility(View.GONE);
		tvPoints.setVisibility(View.GONE);
		tvInstructions.setVisibility(View.VISIBLE);
		ibCorrect.setVisibility(View.GONE);
		ibIncorrect.setVisibility(View.GONE);
		tvFallingWord.setVisibility(View.GONE);
		tvSuggestedWord.setText(getString(R.string.falling_word));
	}

	/**
	 * Restart the counter and invalidate the previous one if it was still running.
	 */
	private void startCounterDown() {
		if(mCounterDown != null) mCounterDown.cancel();
		mCounterDown = initCountdown().start();
	}

	//region Full screen setup

	/**
	 * Necessary set up for making the screen fullscreen.
	 */
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
		mHideHandler.postDelayed(hideUiElements, UI_HIDE_ANIMATION_DELAY);
	}

	//endregion
}
