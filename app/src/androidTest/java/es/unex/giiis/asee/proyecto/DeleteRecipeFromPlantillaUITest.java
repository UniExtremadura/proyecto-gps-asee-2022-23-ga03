package es.unex.giiis.asee.proyecto;

import static android.content.Context.MODE_PRIVATE;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

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

import java.util.Date;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;
import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DeleteRecipeFromPlantillaUITest {

    private SharedPreferences sp;

    private UserItem item;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        item = new UserItem("UsuarioPrueba", "Usuario de prueba", "UsuarioPrueba@gmail.com", 21, 74.0, 180.0, "123");
        PlantillaItem plantillaItem = new PlantillaItem("dieta guapa", PlantillaItem.Priority.HIGH, PlantillaItem.Day.FRIDAY, 0);
        RecipePlantillaItem recipePlantillaItem = new RecipePlantillaItem("Red Velvet Mason Jar Trifle", RecipePlantillaItem.Period.DINNER, 300.0, "c241539d390d63a3cd461564a0fe288a", "https://edamam-product-images.s3.amazonaws.com/web-img/8bb/8bb11cad3134666c94d8541c41da277e.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHQaCXVzLWVhc3QtMSJHMEUCIQDM9QvCRlI99Dofg6D3AKGxXUk9x%2FG7%2F0pKp1BkJsjb4wIgFhCr8ZYxETKBJUdSBH1TlOIdzEKndoFkElXZHnykqWoq1QQIjf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwxODcwMTcxNTA5ODYiDIe1BqPd5QYjBhl4WyqpBKx%2BMSH33wzZHQbZ95wbc1lgyOyeYl%2FLYBzaXJo5NUb2Xq8afZvDjVKLTgAngDBZX4si7ycrZJk1UrGm9JND8aEwmQiIxnescg3jnl3%2ByVGhs0u7sYXTKvTO7Nk%2B9IXCAS9Z4Le2wR0VRBmkMTnoJAbKS1jtgLPi84xwh%2BiMtNdM1ZTMOaPzj4Mfl1gYttyLYvAu%2FgyuDpTQpZhivz2hKjZ1h0gBWSc%2FsT5Y8tWOaoOaxmD9xbeyn45USGEo%2FOKdggFrDt%2B9SOHD6923%2Bs2RkVbetlOeUETDMBRCgXGFwhUKdhTfbRW16DxuIQcEhxc8rrbL0%2Bwe8wFU3RAwNA06IoppmEKWh4h4x29Ag5lnumFcmjTFwmMyleR18otlXGJwdIJgpJjkukzPw3ZijP5YEVaWSjPvBdK%2Brb9pyekcmdEJkOn8th%2F8Kkdq2E0ugXT7DQcopECPdWPuV8GcFwsaPmAaoW5DHESE%2FEm92HWmw%2FHFQfqsZN1YZJ%2BzCEJN0EWjlEPUBXg7h4J3AxLuG9gRC8BR0eNpGEuNcXXlyarRwt4p2oDMXuKtBTndjaM2HWtpe6X1N3ixXfCbvRdvlTPY%2F1DocYIIlLvwG93UHiByvk6HKKBVA2BLhQtntnvCWzgHMEBGydbdrA1OKr0GLbX2SzY%2BNYbPaWRqXV1L6rzysjq9VJtZzpqHIG%2FlB8JFHjmAFslhrWaS0a8gisH6ljiNKS1jELH5b0Cu0dswg6uinAY6qQHHls65W8cHS95MEETMtFarcOPJtFqAaOCs373nMdEUNNWMrMhk8AwridziuAb9IRRkNl%2BroLmRb4xyu7oD5OZz91jVi2Hqf3kZnz%2Bt8hBAaluhHwEsAZaWuA1MlKxyrsMtQsuG4GP2k3xbti3Nrj4E5fyJW%2F%2B4v5nYDjz4q1iIgZsceeQy%2FkMzYOc33nbJDV%2FoS6TlE45V5wgqOIKaa4VeqppmvIFZog%2BK&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221201T120920Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFM2EA3IU6%2F20221201%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=4967a30a659981d293a34e386e59bdabb0416793c0cbc0b16d92eda4cc44c2e7%22", 0);

        sp = ApplicationProvider.getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);

        AppExecutors.getInstance().diskIO().execute(() -> {
            long id = NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().insert(item);
            item.setId(id);
            plantillaItem.setUserid(id);

            WeightRecordItem recordItem = new WeightRecordItem(id, item.getWeight(), new Date());
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).weightRecordItemDao().insert(recordItem);
            long idPlantilla = NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).plantillaItemDao().insert(plantillaItem);
            recipePlantillaItem.setPlantillaid(idPlantilla);
            NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).recipePlantillaItemDao().insert(recipePlantillaItem);
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
    public void shouldDeleteRecipeFromPlantilla() {
        onView(withText("Diets")).perform(click());
        onView(atPositionOnView(0, R.id.recyclerView, R.id.card_view)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.recipeText))));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withId(R.id.periodTextView))));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.recipeText)).check(matches(withText("Red Velvet Mason Jar Trifle")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.periodTextView)).check(matches(withText("DINNER")));
        onView(atPositionOnView(0, R.id.recyclerView, R.id.deleteButton)).perform(click());
        onView(withId(R.id.yesButton)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(not(hasDescendant(withId(R.id.recipeText)))));
        onView(withId(R.id.recyclerView)).check(matches(not(hasDescendant(withId(R.id.periodTextView)))));
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
