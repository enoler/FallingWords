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
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
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
	private static final String POINTS_PLACEHOLDER = "__POINTS__";
	private final int NUMBER_QUESTIONS = 10;
	private final int RESPONSE_TIME_MILLISECONDS = 10000;
	private final int SUCCESFUL_EDGE = 5;

	private final Handler mHideHandler = new Handler();
	private static Boolean answeredQuestion = false;
	private User mUser;
	private View mContentView;
	private ArrayList<Question> mQuestions;
	private int mPoints;
	private int mQuestionIndex;

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
				if(mQuestionIndex < NUMBER_QUESTIONS)
					selectResponse(mQuestions.get(mQuestionIndex).isCorrectTranslated());
			}
		});

		ibIncorrect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mQuestionIndex < NUMBER_QUESTIONS)
					selectResponse(!mQuestions.get(mQuestionIndex).isCorrectTranslated());
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
		answeredQuestion = true;
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

	private void animateObject() {
		tvFallingWord.setTranslationY(0);
		ObjectAnimator animation =
				ObjectAnimator.ofFloat(tvFallingWord, "translationY",
						((ViewGroup) tvFallingWord.getParent()).getHeight()
				- tvFallingWord.getHeight());
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(10000);
		animation.start();
		animation.setAutoCancel(true);
		animation.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) { }

			@Override
			public void onAnimationEnd(Animator animation) {
				if (!answeredQuestion) changeQuestion();
			}

			@Override
			public void onAnimationCancel(Animator animation) { }

			@Override
			public void onAnimationRepeat(Animator animation) { }
		});
		answeredQuestion = false;
	}

	/**
	 * Change the question if there is still more. Otherwise, it finish the game.
	 */
	private void changeQuestion() {
		startCountdown();
		mQuestionIndex++;
		if(mQuestionIndex < NUMBER_QUESTIONS) {
			tvSuggestedWord.setText(mQuestions.get(mQuestionIndex).getUserLanguageWord());
			tvFallingWord.setText(mQuestions.get(mQuestionIndex).getLearningLanguageWord());
			animateObject();
		} else onFinishGame();
	}

	private void incrementPoint(){
		String startingPoints = getString(R.string.points).replace(POINTS_PLACEHOLDER, Integer.toString(++mPoints));
		tvPoints.setText(startingPoints);
	}

	/**
	 * Reset paramenters and read new random questions.
	 */
	private void initGame() {
		// Initialize game.
		FallingWords game = new FallingWords(this, mUser);
		mQuestions = game.getQuestions(NUMBER_QUESTIONS);
		mPoints = -1;
		mQuestionIndex = 0;

		// Start in 0.
		incrementPoint();

		// Initial words.
		tvSuggestedWord.setText(mQuestions.get(mQuestionIndex).getUserLanguageWord());
		tvFallingWord.setText(mQuestions.get(mQuestionIndex).getLearningLanguageWord());
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
		if(mPoints >= SUCCESFUL_EDGE) resultMessageId = R.string.result_message_succesful;
		else resultMessageId = R.string.result_message_unsuccesful;

		String resultMessage = getString(resultMessageId)
				.replace(POINTS_PLACEHOLDER, Integer.toString(mPoints));
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

	private void startCountdown() {
		new CountDownTimer(RESPONSE_TIME_MILLISECONDS, 1000) {

			public void onTick(long millisUntilFinished) {
				tvTime.setText(Long.toString(millisUntilFinished / 1000));
			}

			public void onFinish() { }

		}.start();
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
