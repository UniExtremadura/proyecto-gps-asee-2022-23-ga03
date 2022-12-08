package es.unex.giiis.asee.proyecto;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


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
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.RecetasFragment;

@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class RecipesVisualizationUiTest {

    @Mock
    private RecetasFragment fragment;

    @Mock
    private List<Recipe> recipes;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        recipes = new ArrayList<>();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();

        recipe1.setLabel("Red Velvet Mason Jar Trifle");
        recipe1.setUri("http://www.edamam.com/ontologies/edamam.owl#recipe_c241539d390d63a3cd461564a0fe288a");
        recipe1.setImage("https://edamam-product-images.s3.amazonaws.com/web-img/8bb/8bb11cad3134666c94d8541c41da277e.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHQaCXVzLWVhc3QtMSJHMEUCIQDM9QvCRlI99Dofg6D3AKGxXUk9x%2FG7%2F0pKp1BkJsjb4wIgFhCr8ZYxETKBJUdSBH1TlOIdzEKndoFkElXZHnykqWoq1QQIjf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwxODcwMTcxNTA5ODYiDIe1BqPd5QYjBhl4WyqpBKx%2BMSH33wzZHQbZ95wbc1lgyOyeYl%2FLYBzaXJo5NUb2Xq8afZvDjVKLTgAngDBZX4si7ycrZJk1UrGm9JND8aEwmQiIxnescg3jnl3%2ByVGhs0u7sYXTKvTO7Nk%2B9IXCAS9Z4Le2wR0VRBmkMTnoJAbKS1jtgLPi84xwh%2BiMtNdM1ZTMOaPzj4Mfl1gYttyLYvAu%2FgyuDpTQpZhivz2hKjZ1h0gBWSc%2FsT5Y8tWOaoOaxmD9xbeyn45USGEo%2FOKdggFrDt%2B9SOHD6923%2Bs2RkVbetlOeUETDMBRCgXGFwhUKdhTfbRW16DxuIQcEhxc8rrbL0%2Bwe8wFU3RAwNA06IoppmEKWh4h4x29Ag5lnumFcmjTFwmMyleR18otlXGJwdIJgpJjkukzPw3ZijP5YEVaWSjPvBdK%2Brb9pyekcmdEJkOn8th%2F8Kkdq2E0ugXT7DQcopECPdWPuV8GcFwsaPmAaoW5DHESE%2FEm92HWmw%2FHFQfqsZN1YZJ%2BzCEJN0EWjlEPUBXg7h4J3AxLuG9gRC8BR0eNpGEuNcXXlyarRwt4p2oDMXuKtBTndjaM2HWtpe6X1N3ixXfCbvRdvlTPY%2F1DocYIIlLvwG93UHiByvk6HKKBVA2BLhQtntnvCWzgHMEBGydbdrA1OKr0GLbX2SzY%2BNYbPaWRqXV1L6rzysjq9VJtZzpqHIG%2FlB8JFHjmAFslhrWaS0a8gisH6ljiNKS1jELH5b0Cu0dswg6uinAY6qQHHls65W8cHS95MEETMtFarcOPJtFqAaOCs373nMdEUNNWMrMhk8AwridziuAb9IRRkNl%2BroLmRb4xyu7oD5OZz91jVi2Hqf3kZnz%2Bt8hBAaluhHwEsAZaWuA1MlKxyrsMtQsuG4GP2k3xbti3Nrj4E5fyJW%2F%2B4v5nYDjz4q1iIgZsceeQy%2FkMzYOc33nbJDV%2FoS6TlE45V5wgqOIKaa4VeqppmvIFZog%2BK&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221201T120920Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFM2EA3IU6%2F20221201%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=4967a30a659981d293a34e386e59bdabb0416793c0cbc0b16d92eda4cc44c2e7");
        recipe1.setYield(10);
        recipe1.setCalories(10886.689931893392);
        List<String> mealList = new ArrayList<>();
        mealList.add("lunch/dinner");
        recipe1.setMealType(mealList);
        List<String> dishList = new ArrayList<>();
        dishList.add("desserts");
        recipe1.setDishType(dishList);

        recipe2.setLabel("Oat and Corn Waffles");
        recipe2.setUri("http://www.edamam.com/ontologies/edamam.owl#recipe_c48a6234065b674bd361376c33052f80");
        recipe2.setImage("https://edamam-product-images.s3.amazonaws.com/web-img/0a7/0a7c53b8b5da8ab9e7a9d1aae343c654.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHQaCXVzLWVhc3QtMSJHMEUCIQDM9QvCRlI99Dofg6D3AKGxXUk9x%2FG7%2F0pKp1BkJsjb4wIgFhCr8ZYxETKBJUdSBH1TlOIdzEKndoFkElXZHnykqWoq1QQIjf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwxODcwMTcxNTA5ODYiDIe1BqPd5QYjBhl4WyqpBKx%2BMSH33wzZHQbZ95wbc1lgyOyeYl%2FLYBzaXJo5NUb2Xq8afZvDjVKLTgAngDBZX4si7ycrZJk1UrGm9JND8aEwmQiIxnescg3jnl3%2ByVGhs0u7sYXTKvTO7Nk%2B9IXCAS9Z4Le2wR0VRBmkMTnoJAbKS1jtgLPi84xwh%2BiMtNdM1ZTMOaPzj4Mfl1gYttyLYvAu%2FgyuDpTQpZhivz2hKjZ1h0gBWSc%2FsT5Y8tWOaoOaxmD9xbeyn45USGEo%2FOKdggFrDt%2B9SOHD6923%2Bs2RkVbetlOeUETDMBRCgXGFwhUKdhTfbRW16DxuIQcEhxc8rrbL0%2Bwe8wFU3RAwNA06IoppmEKWh4h4x29Ag5lnumFcmjTFwmMyleR18otlXGJwdIJgpJjkukzPw3ZijP5YEVaWSjPvBdK%2Brb9pyekcmdEJkOn8th%2F8Kkdq2E0ugXT7DQcopECPdWPuV8GcFwsaPmAaoW5DHESE%2FEm92HWmw%2FHFQfqsZN1YZJ%2BzCEJN0EWjlEPUBXg7h4J3AxLuG9gRC8BR0eNpGEuNcXXlyarRwt4p2oDMXuKtBTndjaM2HWtpe6X1N3ixXfCbvRdvlTPY%2F1DocYIIlLvwG93UHiByvk6HKKBVA2BLhQtntnvCWzgHMEBGydbdrA1OKr0GLbX2SzY%2BNYbPaWRqXV1L6rzysjq9VJtZzpqHIG%2FlB8JFHjmAFslhrWaS0a8gisH6ljiNKS1jELH5b0Cu0dswg6uinAY6qQHHls65W8cHS95MEETMtFarcOPJtFqAaOCs373nMdEUNNWMrMhk8AwridziuAb9IRRkNl%2BroLmRb4xyu7oD5OZz91jVi2Hqf3kZnz%2Bt8hBAaluhHwEsAZaWuA1MlKxyrsMtQsuG4GP2k3xbti3Nrj4E5fyJW%2F%2B4v5nYDjz4q1iIgZsceeQy%2FkMzYOc33nbJDV%2FoS6TlE45V5wgqOIKaa4VeqppmvIFZog%2BK&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221201T120920Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFM2EA3IU6%2F20221201%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=a5f08f2e7c649e9cb41dc1494f98a0450379b8f13c7839c24563e4b52dbc8fe0");
        recipe2.setYield(8);
        recipe2.setCalories(2367.6079999999997);
        List<String> mealList2 = new ArrayList<>();
        mealList2.add("breakfast");
        recipe2.setMealType(mealList2);
        List<String> dishList2 = new ArrayList<>();
        dishList2.add("desserts");
        recipe2.setDishType(dishList2);

        recipes.add(recipe1);
        recipes.add(recipe2);

    }

    @Test
    public void shouldReadRecipes(){
        try {
            onView(withId(R.id.navigation_recetas)).perform(click());
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Fragment nav_host_fragment = mActivityRule.getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        fragment = (RecetasFragment) nav_host_fragment.getChildFragmentManager().getFragments().get(0);

        try {
            AppExecutors.getInstance().mainThread().execute(() -> fragment.setDataSource(recipes));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.recipelist);

        // Check that R.id.recipelist has two elements
        assertEquals(2, recyclerView.getAdapter().getItemCount());

        // Check that elements exists inside R.id.recipelist
        // Check that R.id.recipelist hasDescendant withId R.id.imageView
        onView(withId(R.id.recipelist)).check(matches(hasDescendant(withId(R.id.imageView))));
        // Check that R.id.recipelist hasDescendant withId R.id.recipeText
        onView(withId(R.id.recipelist)).check(matches(hasDescendant(withId(R.id.recipeText))));
        // Check that R.id.recipelist hasDescendant withId R.id.dishText
        onView(withId(R.id.recipelist)).check(matches(hasDescendant(withId(R.id.dishText))));
        // Check that R.id.recipelist hasDescendant withId R.id.mealText
        onView(withId(R.id.recipelist)).check(matches(hasDescendant(withId(R.id.mealText))));
        // Check that R.id.recipelist hasDescendant withId R.id.caloryText
        onView(withId(R.id.recipelist)).check(matches(hasDescendant(withId(R.id.caloryText))));

        // Check that recipe values exists inside R.id.recipelist
        // Check that R.id.recipelist hasDescendant withText "Red Velvet Mason Jar Trifle"
        onView(atPositionOnView(0, R.id.recipeText)).check(matches(withText("Red Velvet Mason Jar Trifle")));
        // Check that R.id.recipelist hasDescendant withText "Oat and Corn Waffles"
        onView(atPositionOnView(1, R.id.recipeText)).check(matches(withText("Oat and Corn Waffles")));
        // Check that R.id.recipelist hasDescendant withText "Meal type: lunch/dinner"
        onView(atPositionOnView(0, R.id.mealText)).check(matches(withText("Meal type: lunch/dinner")));
        // Check that R.id.recipelist hasDescendant withText "Meal type: breakfast"
        onView(atPositionOnView(1, R.id.mealText)).check(matches(withText("Meal type: breakfast")));
        // Check that R.id.recipelist hasDescendant withText "Dish type: desserts"
        onView(atPositionOnView(0, R.id.dishText)).check(matches(withText("Dish type: desserts")));
        onView(atPositionOnView(1, R.id.dishText)).check(matches(withText("Dish type: desserts")));
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(R.id.recipelist);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(R.id.recipelist);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                R.id.recipelist);
                    }
                }
                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                            (RecyclerView) view.getRootView().findViewById( R.id.recipelist);
                    if (recyclerView != null && recyclerView.getId() ==  R.id.recipelist) {
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