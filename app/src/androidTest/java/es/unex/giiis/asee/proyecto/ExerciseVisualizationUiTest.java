package es.unex.giiis.asee.proyecto;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anyOf;
import static org.junit.Assert.assertEquals;

import android.content.res.Resources;
import android.view.View;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.ui.ejercicios.EjerciciosFragment;

@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class ExerciseVisualizationUiTest {

    @Mock
    private EjerciciosFragment fragment;

    @Mock
    private List<Excercise> excercises;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        excercises = new ArrayList<>();
        Excercise excercise1 = new Excercise("Flexor Incline Curls", "strength", "biceps", "None", "beginner", "Hold the dumbbell towards the side farther from you so that you have more weight on the side closest to you. (This can be done for a good effect on all bicep dumbbell exercises). Now do a normal incline dumbbell curl, but keep your wrists as far back as possible so as to neutralize any stress that is placed on them. Sit on an incline bench that is angled at 45-degrees while holding a dumbbell on each hand. Let your arms hang down on your sides, with the elbows in, and turn the palms of your hands forward with the thumbs pointing away from the body. Tip: You will keep this hand position throughout the movement as there should not be any twisting of the hands as they come up. This will be your starting position. Curl up the two dumbbells at the same time until your biceps are fully contracted and exhale. Tip: Do not swing the arms or use momentum. Keep a controlled motion at all times. Hold the contracted position for a second at the top. As you inhale, slowly go back to the starting position. Repeat for the recommended amount of repetitions.  Caution: Do not extend your arms totally as you could injure your elbows if you hyperextend them. Also, make sure that on the way down you move slowly to avoid injury. Variations: You can use cables for this movement as well.");
        Excercise excercise2 = new Excercise("Hammer Curls", "powerlifting", "hamstrings", "dumbbell", "expert", "Stand up with your torso upright and a dumbbell on each hand being held at arms length. The elbows should be close to the torso. The palms of the hands should be facing your torso. This will be your starting position. Now, while holding your upper arm stationary, exhale and curl the weight forward while contracting the biceps. Continue to raise the weight until the biceps are fully contracted and the dumbbell is at shoulder level. Hold the contracted position for a brief moment as you squeeze the biceps. Tip: Focus on keeping the elbow stationary and only moving your forearm. After the brief pause, inhale and slowly begin the lower the dumbbells back down to the starting position. Repeat for the recommended amount of repetitions.  Variations: There are many possible variations for this movement. For instance, you can perform the exercise sitting down on a bench with or without back support and you can also perform it by alternating arms; first lift the right arm for one repetition, then the left, then the right, etc.");

        excercises.add(excercise1);
        excercises.add(excercise2);
    }

    @Test
    public void shouldReadExcercises(){
        try {
            onView(withId(R.id.navigation_ejercicios)).perform(click());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Fragment nav_host_fragment = mActivityRule.getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        fragment = (EjerciciosFragment) nav_host_fragment.getChildFragmentManager().getFragments().get(0);

        try {
            AppExecutors.getInstance().mainThread().execute(() -> fragment.setDataSource(excercises));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.excerciseslist);

        // Check that R.id.excerciseslist has two elements
        assertEquals(2, recyclerView.getAdapter().getItemCount());

        // Check that elements exists inside R.id.excerciseslist
        // Check that R.id.excerciseslist hasDescendant withId R.id.excerciseText
        onView(withId(R.id.excerciseslist)).check(matches(hasDescendant(withId(R.id.excerciseText))));
        // Check that R.id.excerciseslist hasDescendant withId R.id.muscleText
        onView(withId(R.id.excerciseslist)).check(matches(hasDescendant(withId(R.id.muscleText))));
        // Check that R.id.excerciseslist hasDescendant withId R.id.typeText
        onView(withId(R.id.excerciseslist)).check(matches(hasDescendant(withId(R.id.typeText))));
        // Check that R.id.excerciseslist hasDescendant withId R.id.difficultyText
        onView(withId(R.id.excerciseslist)).check(matches(hasDescendant(withId(R.id.difficultyText))));

        // Check that excercise values exists inside R.id.excerciseslist
        // Check that R.id.excerciseslist hasDescendant withText "Flexor Incline Curls"
        onView(atPositionOnView(0, R.id.excerciseText)).check(matches(withText("Flexor Incline Curls")));
        // Check that R.id.excerciseslist hasDescendant withText "Hammer Curls"
        onView(atPositionOnView(1, R.id.excerciseText)).check(matches(withText("Hammer Curls")));
        // Check that R.id.excerciseslist hasDescendant withText "Type: strength"
        onView(atPositionOnView(0, R.id.typeText)).check(matches(withText("Type: strength")));
        // Check that R.id.excerciseslist hasDescendant withText "Type: powerlifting"
        onView(atPositionOnView(1, R.id.typeText)).check(matches(withText("Type: powerlifting")));
        // Check that R.id.excerciseslist hasDescendant withText "Muscle: biceps"
        onView(atPositionOnView(0, R.id.muscleText)).check(matches(withText("Muscle: biceps")));
        // Check that R.id.excerciseslist hasDescendant withText "Muscle: hamstrings"
        onView(atPositionOnView(1, R.id.muscleText)).check(matches(withText("Muscle: hamstrings")));
        // Check that R.id.excerciseslist hasDescendant withText "Difficulty: beginner"
        onView(atPositionOnView(0, R.id.difficultyText)).check(matches(withText("Difficulty: beginner")));
        // Check that R.id.excerciseslist hasDescendant withText "Difficulty: expert"
        onView(atPositionOnView(1, R.id.difficultyText)).check(matches(withText("Difficulty: expert")));

    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(R.id.excerciseslist);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(R.id.excerciseslist);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                R.id.excerciseslist);
                    }
                }
                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                            (RecyclerView) view.getRootView().findViewById( R.id.excerciseslist);
                    if (recyclerView != null && recyclerView.getId() ==  R.id.excerciseslist) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                    } else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return view == childView;
                } else {
                    View targetView = childView.findViewById(targetViewId);
                    return view == targetView;
                }

            }
        };
    }
}
