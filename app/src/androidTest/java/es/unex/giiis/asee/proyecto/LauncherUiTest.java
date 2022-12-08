package es.unex.giiis.asee.proyecto;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LauncherUiTest {

    @Rule
    public ActivityTestRule <LauncherActivity> lActivityRule =
            new ActivityTestRule <> (LauncherActivity.class);

    @Test
    public void shouldLoadCorrectly () {
        onView (withId (R.id.logo_nutrifit)).check (matches (isDisplayed()));
        onView (withId (R.id.logo_nutrifit)).check (isAbove (withId (R.id.text_nutrifit)));
        onView (withId (R.id.text_nutrifit)).check (matches (isDisplayed()));
        onView (withId (R.id.text_nutrifit)).check (matches (withText ("Nutrifit")));
    }
}