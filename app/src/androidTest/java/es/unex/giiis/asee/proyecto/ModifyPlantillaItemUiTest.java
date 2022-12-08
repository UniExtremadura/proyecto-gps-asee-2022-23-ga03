package es.unex.giiis.asee.proyecto;

import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ModifyPlantillaItemUiTest {

    private SharedPreferences sp;

    private UserItem item;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        PlantillaItem plantillaItem1 = new PlantillaItem("Plantilla prueba 1", PlantillaItem.Priority.HIGH, PlantillaItem.Day.SATURDAY, 0);
        PlantillaItem plantillaItem2 = new PlantillaItem("Plantilla prueba 2", PlantillaItem.Priority.MED, PlantillaItem.Day.TUESDAY, 0);
        item = new UserItem("UsuarioPrueba", "Usuario de prueba", "UsuarioPrueba@gmail.com", 21, 74.0, 180.0, "123");

        sp = ApplicationProvider.getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);

        AppExecutors.getInstance().diskIO().execute(() -> {
            long id = NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().insert(item);
            item.setId(id);

            plantillaItem1.setUserid(id);
            plantillaItem2.setUserid(id);

            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).plantillaItemDao().insert(plantillaItem1);
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).plantillaItemDao().insert(plantillaItem2);

            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("id",item.getId());
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
    public void shouldModifyPlantillaItem() {
        String newTitle = "Prueba Modificada";
        String newPriority = "LOW";
        String newDay = "FRIDAY";

        onView(withText("Diets")).perform(click());

        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.recyclerView);

        // Check that R.id.recyclerView has two elements
        assertEquals(2, recyclerView.getAdapter().getItemCount());

        // Check that elements exists inside R.id.recyclerView
        // Check that R.id.recyclerView hasDescendant withId R.id.titleText
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.titleText))));
        // Check that R.id.recyclerView hasDescendant withId R.id.dayText
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.dayText))));
        // Check that R.id.recyclerView hasDescendant withId R.id.priorityText
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.priorityText))));

        // Check that diets values exists inside R.id.recyclerView
        // Check that R.id.recyclerView hasDescendant withText "Plantilla prueba 1"
        onView(atPositionOnView(0, R.id.recyclerView, R.id.titleText)).check(matches(withText("Plantilla prueba 1")));
        // Check that R.id.recyclerView hasDescendant withText "Plantilla prueba 1"
        onView(atPositionOnView(1, R.id.recyclerView, R.id.titleText)).check(matches(withText("Plantilla prueba 2")));
        // Check that R.id.recyclerView hasDescendant withText "HIGH"
        onView(atPositionOnView(0, R.id.recyclerView, R.id.dayText)).check(matches(withText("HIGH")));
        // Check that R.id.recyclerView hasDescendant withText "MED"
        onView(atPositionOnView(1, R.id.recyclerView, R.id.dayText)).check(matches(withText("MED")));
        // Check that R.id.recyclerView hasDescendant withText "SATURDAY"
        onView(atPositionOnView(0, R.id.recyclerView, R.id.priorityText)).check(matches(withText("SATURDAY")));
        // Check that R.id.recyclerView hasDescendant withText "TUESDAY"
        onView(atPositionOnView(1, R.id.recyclerView, R.id.priorityText)).check(matches(withText("TUESDAY")));

        // Navigate to modify plantilla item
        onView(atPositionOnView(1, R.id.recyclerView, R.id.modifyButton)).perform(click());

        // Perform typeText() and closeSoftKeyboard() actions on R.id.title
        onView(withId(R.id.title)).perform(replaceText(newTitle), closeSoftKeyboard());
        onView(withId(R.id.day_spinner)).perform(click());
        onView(withText(newDay)).perform(click());
        onView(withId(R.id.priority_spinner)).perform(click());
        onView(withText(newPriority)).perform(click());

        // Finish update of plantilla item
        onView(withId(R.id.submitButton)).perform(click());

        // Check that diet has updated inside R.id.recyclerView
        // Check that R.id.recyclerView hasDescendant withText "Plantilla prueba 1"
        onView(atPositionOnView(1, R.id.recyclerView, R.id.titleText)).check(matches(withText(newTitle)));
        // Check that R.id.recyclerView hasDescendant withText "MED"
        onView(atPositionOnView(1, R.id.recyclerView, R.id.dayText)).check(matches(withText(newPriority)));
        // Check that R.id.recyclerView hasDescendant withText "TUESDAY"
        onView(atPositionOnView(1, R.id.recyclerView, R.id.priorityText)).check(matches(withText(newDay)));
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
