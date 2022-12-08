package es.unex.giiis.asee.proyecto;

import static android.content.Context.MODE_PRIVATE;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.Date;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

@RunWith(AndroidJUnit4.class)
public class EditRecipeCalendarUITest {

    private SharedPreferences sp;

    private UserItem item;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        item = new UserItem("UsuarioPrueba", "Usuario de prueba", "UsuarioPrueba@gmail.com", 21, 74.0, 180.0, "123");

        sp = ApplicationProvider.getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        Date date = null;
        try {
            date = CalendarDayItem.FORMAT.parse("22:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CalendarDayItem calendarDayItem = new CalendarDayItem("Red Velvet Mason Jar Trifle", "c241539d390d63a3cd461564a0fe288a", CalendarDayItem.Status.DONE, "2022-12-13", date, 0, "recipe");


        AppExecutors.getInstance().diskIO().execute(() -> {
            long id = NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().insert(item);
            item.setId(id);
            calendarDayItem.setUserid(id);

            WeightRecordItem recordItem = new WeightRecordItem(id, item.getWeight(), new Date());
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).weightRecordItemDao().insert(recordItem);
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).calendarDayItemDao().insert(calendarDayItem);

            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("id", id);
            editor.putString("username", item.getUsername());
            editor.putString("password", item.getPassword());
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
    public void shouldEditCalendar() {
        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(com.applandeo.materialcalendarview.R.id.calendarGridView),
                        childAtPosition(
                                withId(com.applandeo.materialcalendarview.R.id.calendarViewPager),
                                0)))
                .atPosition(16);
        linearLayout.perform(scrollTo(), click());

        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.titleText))));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.timeTextView))));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.stateTextView))));

        onView(withId(R.id.date)).check(matches(withText("2022-12-13")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.titleText)).check(matches(withText("Red Velvet Mason Jar Trifle")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.timeTextView)).check(matches(withText("22:00:00")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.stateTextView)).check(matches(withText("DONE")));


        onView(atPositionOnView(0, R.id.recyclerView, R.id.modifyButton)).perform(click());
        onView(withId(R.id.statusNotDone)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());
        onView(atPositionOnView(0, R.id.recyclerView, R.id.titleText)).check(matches(withText("Red Velvet Mason Jar Trifle")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.timeTextView)).check(matches(withText("22:00:00")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.stateTextView)).check(matches(withText("NOTDONE")));



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

}
