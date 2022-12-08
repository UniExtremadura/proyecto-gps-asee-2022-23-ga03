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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.ejercicios.EjerciciosFragment;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

@RunWith (AndroidJUnit4.class)
@LargeTest
public class ViewEventsCalendarUiTest {
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
            date = CalendarDayItem.FORMAT.parse("15:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CalendarDayItem calendarDayItem = new CalendarDayItem("press de banca", "", CalendarDayItem.Status.NOTDONE, "2022-12-13", date, 0, "excercise");

        try {
            date = CalendarDayItem.FORMAT.parse("20:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CalendarDayItem calendar_day_item = new CalendarDayItem ("default_recipe", "1", CalendarDayItem.Status.DONE, "2022-12-13", date, 0, "recipe");

        AppExecutors.getInstance().diskIO().execute(() -> {
            long id = NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().insert(item);
            item.setId(id);
            calendarDayItem.setUserid (id);
            calendar_day_item.setUserid (id);

            WeightRecordItem recordItem = new WeightRecordItem(id, item.getWeight(), new Date());
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).weightRecordItemDao ().insert(recordItem);
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).calendarDayItemDao ().insert (calendarDayItem);
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).calendarDayItemDao ().insert (calendar_day_item);

            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("id",id);
            editor.putString("username",item.getUsername());
            editor.putString("password",item.getPassword());
            editor.apply();
        });
    }

    @Test
    public void shouldViewEventsInCalendar () {
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
        onView(atPositionOnView(0, R.id.recyclerView, R.id.titleText)).check(matches(withText("press de banca")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.timeTextView)).check(matches(withText("15:00:00")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.stateTextView)).check(matches(withText("NOTDONE")));

        onView(atPositionOnView(1, R.id.recyclerView, R.id.titleText)).check(matches(withText("default_recipe")));
        onView(atPositionOnView(1, R.id.recyclerView, R.id.timeTextView)).check(matches(withText("20:00:00")));
        onView(atPositionOnView(1, R.id.recyclerView, R.id.stateTextView)).check(matches(withText("DONE")));
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


    @After
    public void reset() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("id");
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        AppExecutors.getInstance().diskIO().execute(() -> NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().delete(item));
    }
}
