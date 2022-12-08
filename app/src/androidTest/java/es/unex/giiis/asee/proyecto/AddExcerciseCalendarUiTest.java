package es.unex.giiis.asee.proyecto;

import static android.content.Context.MODE_PRIVATE;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.ejercicios.EjerciciosFragment;

@RunWith (AndroidJUnit4.class)
@LargeTest
public class AddExcerciseCalendarUiTest {
    private SharedPreferences sp;
    private UserItem item;
    private List<Excercise> excercises;
    private EjerciciosFragment fragment;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        item = new UserItem("UsuarioPrueba", "Usuario de prueba", "UsuarioPrueba@gmail.com", 21, 74.0, 180.0, "123");
        sp = ApplicationProvider.getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        excercises = new ArrayList <> ();
        Excercise excercise1 = new Excercise("Flexor Incline Curls", "strength", "biceps", "None", "beginner", "Hold the dumbbell towards the side farther from you so that you have more weight on the side closest to you. (This can be done for a good effect on all bicep dumbbell exercises). Now do a normal incline dumbbell curl, but keep your wrists as far back as possible so as to neutralize any stress that is placed on them. Sit on an incline bench that is angled at 45-degrees while holding a dumbbell on each hand. Let your arms hang down on your sides, with the elbows in, and turn the palms of your hands forward with the thumbs pointing away from the body. Tip: You will keep this hand position throughout the movement as there should not be any twisting of the hands as they come up. This will be your starting position. Curl up the two dumbbells at the same time until your biceps are fully contracted and exhale. Tip: Do not swing the arms or use momentum. Keep a controlled motion at all times. Hold the contracted position for a second at the top. As you inhale, slowly go back to the starting position. Repeat for the recommended amount of repetitions.  Caution: Do not extend your arms totally as you could injure your elbows if you hyperextend them. Also, make sure that on the way down you move slowly to avoid injury. Variations: You can use cables for this movement as well.");
        Excercise excercise2 = new Excercise("Hammer Curls", "powerlifting", "hamstrings", "dumbbell", "expert", "Stand up with your torso upright and a dumbbell on each hand being held at arms length. The elbows should be close to the torso. The palms of the hands should be facing your torso. This will be your starting position. Now, while holding your upper arm stationary, exhale and curl the weight forward while contracting the biceps. Continue to raise the weight until the biceps are fully contracted and the dumbbell is at shoulder level. Hold the contracted position for a brief moment as you squeeze the biceps. Tip: Focus on keeping the elbow stationary and only moving your forearm. After the brief pause, inhale and slowly begin the lower the dumbbells back down to the starting position. Repeat for the recommended amount of repetitions.  Variations: There are many possible variations for this movement. For instance, you can perform the exercise sitting down on a bench with or without back support and you can also perform it by alternating arms; first lift the right arm for one repetition, then the left, then the right, etc.");
        excercises.add (excercise1);
        excercises.add (excercise2);

        AppExecutors.getInstance().diskIO().execute(() -> {
            long id = NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().insert(item);
            item.setId(id);

            WeightRecordItem recordItem = new WeightRecordItem(id, item.getWeight(), new Date());
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).weightRecordItemDao().insert(recordItem);

            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("id",id);
            editor.putString("username",item.getUsername());
            editor.putString("password",item.getPassword());
            editor.apply();
        });
    }

    @After
    public void reset() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("id");
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        AppExecutors.getInstance().diskIO().execute(() -> NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().delete(item));
    }

    @Test
    public void shouldAddExcerciseToCalendar() {
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

        onView (atPositionOnView (0, R.id.excerciseslist, R.id.card_view)).perform (click ());
        onView (withId (R.id.horarioButton)).perform (click ());
        onView (withId (R.id.statusDone)).perform (click ());
        onView (withId (R.id.date_picker_button)).perform (click ());
        onView (withClassName (Matchers.equalTo (DatePicker.class.getName ()))).perform (PickerActions.setDate (2022, 12, 13));
        onView (withText ("OK")).perform (click ());
        onView (withId (R.id.submitButton)).perform (click ());

        pressBack ();

        onView (withId (R.id.navigation_horario)).perform (click ());
        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(com.applandeo.materialcalendarview.R.id.calendarGridView),
                        childAtPosition(
                                withId(com.applandeo.materialcalendarview.R.id.calendarViewPager),
                                0)))
                .atPosition(15);
        linearLayout.perform(scrollTo(), click());

        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.titleText))));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.timeTextView))));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.stateTextView))));

        onView(withId(R.id.date)).check(matches(withText("2022-12-13")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.titleText)).check(matches(withText("Flexor Incline Curls")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.stateTextView)).check(matches(withText("DONE")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public Matcher<View> atPositionOnView(final int position, final int sourceViewId, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(sourceViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(sourceViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                sourceViewId);
                    }
                }
                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                            (RecyclerView) view.getRootView().findViewById(sourceViewId);
                    if (recyclerView != null && recyclerView.getId() == sourceViewId) {
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
