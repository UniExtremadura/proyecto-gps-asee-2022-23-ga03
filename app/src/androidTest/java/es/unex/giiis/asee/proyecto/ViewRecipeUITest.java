package es.unex.giiis.asee.proyecto;

import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;

import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.recipesmodel.Digest;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.recetas.RecetasFragment;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewRecipeUITest {
    private RecetasFragment fragment;
    private List<Recipe> recipesList;

    private SharedPreferences sp;

    private UserItem item;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    @Before
    public void init() {
        recipesList = new ArrayList<>();

        item = new UserItem("UsuarioPrueba", "Usuario de prueba", "UsuarioPrueba@gmail.com", 21, 74.0, 180.0, "123");

        sp = ApplicationProvider.getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);

        AppExecutors.getInstance().diskIO().execute(() -> {
            long id = NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().insert(item);
            item.setId(id);

            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("id",item.getId());
            editor.putString("username",item.getUsername());
            editor.putString("password",item.getPassword());
            editor.apply();
        });
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();

        recipe1.setLabel("Red Velvet Mason Jar Trifle");
        recipe1.setUri("http://www.edamam.com/ontologies/edamam.owl#recipe_c241539d390d63a3cd461564a0fe288a");
        recipe1.setImage("https://edamam-product-images.s3.amazonaws.com/web-img/8bb/8bb11cad3134666c94d8541c41da277e.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHQaCXVzLWVhc3QtMSJHMEUCIQDM9QvCRlI99Dofg6D3AKGxXUk9x%2FG7%2F0pKp1BkJsjb4wIgFhCr8ZYxETKBJUdSBH1TlOIdzEKndoFkElXZHnykqWoq1QQIjf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwxODcwMTcxNTA5ODYiDIe1BqPd5QYjBhl4WyqpBKx%2BMSH33wzZHQbZ95wbc1lgyOyeYl%2FLYBzaXJo5NUb2Xq8afZvDjVKLTgAngDBZX4si7ycrZJk1UrGm9JND8aEwmQiIxnescg3jnl3%2ByVGhs0u7sYXTKvTO7Nk%2B9IXCAS9Z4Le2wR0VRBmkMTnoJAbKS1jtgLPi84xwh%2BiMtNdM1ZTMOaPzj4Mfl1gYttyLYvAu%2FgyuDpTQpZhivz2hKjZ1h0gBWSc%2FsT5Y8tWOaoOaxmD9xbeyn45USGEo%2FOKdggFrDt%2B9SOHD6923%2Bs2RkVbetlOeUETDMBRCgXGFwhUKdhTfbRW16DxuIQcEhxc8rrbL0%2Bwe8wFU3RAwNA06IoppmEKWh4h4x29Ag5lnumFcmjTFwmMyleR18otlXGJwdIJgpJjkukzPw3ZijP5YEVaWSjPvBdK%2Brb9pyekcmdEJkOn8th%2F8Kkdq2E0ugXT7DQcopECPdWPuV8GcFwsaPmAaoW5DHESE%2FEm92HWmw%2FHFQfqsZN1YZJ%2BzCEJN0EWjlEPUBXg7h4J3AxLuG9gRC8BR0eNpGEuNcXXlyarRwt4p2oDMXuKtBTndjaM2HWtpe6X1N3ixXfCbvRdvlTPY%2F1DocYIIlLvwG93UHiByvk6HKKBVA2BLhQtntnvCWzgHMEBGydbdrA1OKr0GLbX2SzY%2BNYbPaWRqXV1L6rzysjq9VJtZzpqHIG%2FlB8JFHjmAFslhrWaS0a8gisH6ljiNKS1jELH5b0Cu0dswg6uinAY6qQHHls65W8cHS95MEETMtFarcOPJtFqAaOCs373nMdEUNNWMrMhk8AwridziuAb9IRRkNl%2BroLmRb4xyu7oD5OZz91jVi2Hqf3kZnz%2Bt8hBAaluhHwEsAZaWuA1MlKxyrsMtQsuG4GP2k3xbti3Nrj4E5fyJW%2F%2B4v5nYDjz4q1iIgZsceeQy%2FkMzYOc33nbJDV%2FoS6TlE45V5wgqOIKaa4VeqppmvIFZog%2BK&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221201T120920Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFM2EA3IU6%2F20221201%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=4967a30a659981d293a34e386e59bdabb0416793c0cbc0b16d92eda4cc44c2e7");
        recipe1.setYield(10);
        recipe1.setTotalTime(50);
        recipe1.setTotalWeight(100.0);
        recipe1.setCalories(10886.689931893392);
        List<String> mealList = new ArrayList<>();
        mealList.add("lunch/dinner");
        recipe1.setMealType(mealList);
        List<String> dishList = new ArrayList<>();
        dishList.add("desserts");
        recipe1.setDishType(dishList);
        List<String> ingrList1 = new ArrayList<>();
        ingrList1.add("ingr1");
        recipe1.setIngredientLines(ingrList1);
        List<String> dietList1 = new ArrayList<>();
        dietList1.add("diet1");
        recipe1.setDietLabels(dietList1);
        List<String> healthList1 = new ArrayList<>();
        healthList1.add("health1");
        recipe1.setHealthLabels(healthList1);
        List<Digest> listaDigest1 = new ArrayList<>();
        Digest digest1 = new Digest();
        digest1.setLabel("digest1");
        digest1.setTotal(50.0);
        digest1.setUnit("g");
        listaDigest1.add(digest1);
        recipe1.setDigest(listaDigest1);

        recipe2.setLabel("Oat and Corn Waffles");
        recipe2.setUri("http://www.edamam.com/ontologies/edamam.owl#recipe_c48a6234065b674bd361376c33052f80");
        recipe2.setImage("https://edamam-product-images.s3.amazonaws.com/web-img/0a7/0a7c53b8b5da8ab9e7a9d1aae343c654.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHQaCXVzLWVhc3QtMSJHMEUCIQDM9QvCRlI99Dofg6D3AKGxXUk9x%2FG7%2F0pKp1BkJsjb4wIgFhCr8ZYxETKBJUdSBH1TlOIdzEKndoFkElXZHnykqWoq1QQIjf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwxODcwMTcxNTA5ODYiDIe1BqPd5QYjBhl4WyqpBKx%2BMSH33wzZHQbZ95wbc1lgyOyeYl%2FLYBzaXJo5NUb2Xq8afZvDjVKLTgAngDBZX4si7ycrZJk1UrGm9JND8aEwmQiIxnescg3jnl3%2ByVGhs0u7sYXTKvTO7Nk%2B9IXCAS9Z4Le2wR0VRBmkMTnoJAbKS1jtgLPi84xwh%2BiMtNdM1ZTMOaPzj4Mfl1gYttyLYvAu%2FgyuDpTQpZhivz2hKjZ1h0gBWSc%2FsT5Y8tWOaoOaxmD9xbeyn45USGEo%2FOKdggFrDt%2B9SOHD6923%2Bs2RkVbetlOeUETDMBRCgXGFwhUKdhTfbRW16DxuIQcEhxc8rrbL0%2Bwe8wFU3RAwNA06IoppmEKWh4h4x29Ag5lnumFcmjTFwmMyleR18otlXGJwdIJgpJjkukzPw3ZijP5YEVaWSjPvBdK%2Brb9pyekcmdEJkOn8th%2F8Kkdq2E0ugXT7DQcopECPdWPuV8GcFwsaPmAaoW5DHESE%2FEm92HWmw%2FHFQfqsZN1YZJ%2BzCEJN0EWjlEPUBXg7h4J3AxLuG9gRC8BR0eNpGEuNcXXlyarRwt4p2oDMXuKtBTndjaM2HWtpe6X1N3ixXfCbvRdvlTPY%2F1DocYIIlLvwG93UHiByvk6HKKBVA2BLhQtntnvCWzgHMEBGydbdrA1OKr0GLbX2SzY%2BNYbPaWRqXV1L6rzysjq9VJtZzpqHIG%2FlB8JFHjmAFslhrWaS0a8gisH6ljiNKS1jELH5b0Cu0dswg6uinAY6qQHHls65W8cHS95MEETMtFarcOPJtFqAaOCs373nMdEUNNWMrMhk8AwridziuAb9IRRkNl%2BroLmRb4xyu7oD5OZz91jVi2Hqf3kZnz%2Bt8hBAaluhHwEsAZaWuA1MlKxyrsMtQsuG4GP2k3xbti3Nrj4E5fyJW%2F%2B4v5nYDjz4q1iIgZsceeQy%2FkMzYOc33nbJDV%2FoS6TlE45V5wgqOIKaa4VeqppmvIFZog%2BK&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221201T120920Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFM2EA3IU6%2F20221201%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=a5f08f2e7c649e9cb41dc1494f98a0450379b8f13c7839c24563e4b52dbc8fe0");
        recipe2.setYield(8);
        recipe2.setTotalTime(150);
        recipe2.setTotalWeight(200.0);
        recipe2.setCalories(2367.6079999999997);
        List<String> mealList2 = new ArrayList<>();
        mealList2.add("breakfast");
        recipe2.setMealType(mealList2);
        List<String> dishList2 = new ArrayList<>();
        dishList2.add("desserts");
        recipe2.setDishType(dishList2);
        List<String> ingrList2 = new ArrayList<>();
        ingrList2.add("ingr2");
        recipe2.setIngredientLines(ingrList2);
        List<String> dietList2 = new ArrayList<>();
        dietList2.add("diet2");
        recipe2.setDietLabels(dietList2);
        List<String> healthList2 = new ArrayList<>();
        healthList2.add("health2");
        recipe2.setHealthLabels(healthList2);
        List<Digest> listaDigest2 = new ArrayList<>();
        Digest digest2 = new Digest();
        digest2.setLabel("digest");
        digest2.setTotal(150.0);
        digest2.setUnit("mg");
        listaDigest2.add(digest2);
        recipe2.setDigest(listaDigest2);
        recipesList.add(recipe1);
        recipesList.add(recipe2);
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
    public void shouldViewRecipeDetails(){
        try {
            onView(withId(R.id.navigation_recetas)).perform(click());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Fragment nav_host_fragment = mActivityRule.getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        fragment = (RecetasFragment) nav_host_fragment.getChildFragmentManager().getFragments().get(0);

        try {
            AppExecutors.getInstance().mainThread().execute(() -> fragment.setDataSource(recipesList));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(atPositionOnView(0, R.id.recipelist, R.id.card_view)).perform(click());
        onView(withId(R.id.t_recipeName)).check(matches(withText("Red Velvet Mason Jar Trifle")));
        onView(withId(R.id.t_raciones)).check(matches(withText("Rations: 10")));
        onView(withId(R.id.t_mealType)).check(matches(withText("Meal Type: lunch/dinner")));
        onView(withId(R.id.t_dishType)).check(matches(withText("Dish Type: desserts")));
        onView(withId(R.id.t_time)).check(matches(withText("Total Time: 50")));
        onView(withId(R.id.t_weight)).check(matches(withText("Weight: 100.00 g")));
        onView(withId(R.id.t_ingredientLines)).check(matches(withText("ingr1")));
        onView(withId(R.id.t_dietLabels)).check(matches(withText("diet1")));
        onView(withId(R.id.t_healthLabels)).check(matches(withText("health1")));
        onView(withId(R.id.t_dietLines)).check(matches(withText("digest1 - 50.00 g\n")));
        pressBack();
        onView(atPositionOnView(1, R.id.recipelist, R.id.card_view)).perform(click());
        onView(withId(R.id.t_recipeName)).check(matches(withText("Oat and Corn Waffles")));
        onView(withId(R.id.t_raciones)).check(matches(withText("Rations: 8")));
        onView(withId(R.id.t_mealType)).check(matches(withText("Meal Type: breakfast")));
        onView(withId(R.id.t_dishType)).check(matches(withText("Dish Type: desserts")));
        onView(withId(R.id.t_time)).check(matches(withText("Total Time: 150")));
        onView(withId(R.id.t_weight)).check(matches(withText("Weight: 200.00 g")));
        onView(withId(R.id.t_ingredientLines)).check(matches(withText("ingr2")));
        onView(withId(R.id.t_dietLabels)).check(matches(withText("diet2")));
        onView(withId(R.id.t_healthLabels)).check(matches(withText("health2")));
        onView(withId(R.id.t_dietLines)).check(matches(withText("digest - 150.00 mg\n")));
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

