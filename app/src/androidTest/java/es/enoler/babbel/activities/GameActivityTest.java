package es.enoler.babbel.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import es.enoler.babbel.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GameActivityTest {

	@Rule
	public ActivityTestRule<GameActivity> mActivityTestRule = new ActivityTestRule<>(GameActivity.class);

	@Test
	public void gameActivityTest() {
		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatButton = onView(allOf(withId(R.id.bt_start), withText("Start game"), childAtPosition(allOf(withId(R.id.rl_main_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 1)), 1), isDisplayed()));
		appCompatButton.perform(click());

		ViewInteraction appCompatImageButton = onView(allOf(withId(R.id.ib_correct), withContentDescription("Correct"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 0), isDisplayed()));
		appCompatImageButton.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton2 = onView(allOf(withId(R.id.ib_incorrect), withContentDescription("Incorrect"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 1), isDisplayed()));
		appCompatImageButton2.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton3 = onView(allOf(withId(R.id.ib_correct), withContentDescription("Correct"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 0), isDisplayed()));
		appCompatImageButton3.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton4 = onView(allOf(withId(R.id.ib_correct), withContentDescription("Correct"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 0), isDisplayed()));
		appCompatImageButton4.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton5 = onView(allOf(withId(R.id.ib_incorrect), withContentDescription("Incorrect"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 1), isDisplayed()));
		appCompatImageButton5.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton6 = onView(allOf(withId(R.id.ib_incorrect), withContentDescription("Incorrect"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 1), isDisplayed()));
		appCompatImageButton6.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton7 = onView(allOf(withId(R.id.ib_correct), withContentDescription("Correct"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 0), isDisplayed()));
		appCompatImageButton7.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton8 = onView(allOf(withId(R.id.ib_incorrect), withContentDescription("Incorrect"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 1), isDisplayed()));
		appCompatImageButton8.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatImageButton9 = onView(allOf(withId(R.id.ib_incorrect), withContentDescription("Incorrect"), childAtPosition(allOf(withId(R.id.ll_bottom_container), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 1), isDisplayed()));
		appCompatImageButton9.perform(click());

		// Added a sleep statement to match the app's execution delay.
		// The recommended way to handle such scenarios is to use Espresso idling resources:
		// https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ViewInteraction appCompatButton2 = onView(allOf(withId(android.R.id.button1), withText("Aceptar"), childAtPosition(childAtPosition(withId(R.id.buttonPanel), 0), 3)));
		appCompatButton2.perform(scrollTo(), click());
	}

	private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}
