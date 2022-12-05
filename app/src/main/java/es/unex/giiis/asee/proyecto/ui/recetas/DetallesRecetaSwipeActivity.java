package es.unex.giiis.asee.proyecto.ui.recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModel;

public class DetallesRecetaSwipeActivity extends AppCompatActivity {

    private List<Recipe> data;
    private Recipe recipe;
    private ViewPager2 viewPager;
    private DetallesRecetaPagerAdapter pAdapter;

    private RecipeListViewModel mRecipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_receta_swipe);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mRecipeListViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.recipeFactory).get(RecipeListViewModel.class);

        Gson gson = new Gson();
        recipe = gson.fromJson(getIntent().getStringExtra("Recipe"), Recipe.class);

        data = new ArrayList<>();
        pAdapter = new DetallesRecetaPagerAdapter(data, getSupportFragmentManager(),getLifecycle());

        mRecipeListViewModel.getDownloadedRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                data = recipes;
                pAdapter.setData(data);

                String webid = recipe.getUri().substring(recipe.getUri().lastIndexOf("_") + 1);

                for(int i = 0; i<data.size(); i++) {
                    String dataId = data.get(i).getUri().substring(data.get(i).getUri().lastIndexOf("_") + 1);
                    if(dataId.equals(webid)){
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pAdapter);
    }
}
