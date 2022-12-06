package es.unex.giiis.asee.proyecto.ui.recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;

import com.google.gson.Gson;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModel;

public class DetallesRecetaActivity extends AppCompatActivity {

    private String webid;

    private RecipeListViewModel mRecipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_receta);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mRecipeListViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.recipeFactory).get(RecipeListViewModel.class);

        webid = getIntent().getStringExtra("webid");

        mRecipeListViewModel.getFetchedRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe.getUri().substring(recipe.getUri().lastIndexOf("_") + 1).equals(webid)) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, newInstance(recipe));
                    fragmentTransaction.commit();
                }
            }
        });
    }

    public DetallesRecetaFragment newInstance(Recipe recipe) {
        DetallesRecetaFragment fragment = new DetallesRecetaFragment();
        Bundle args = new Bundle();
        args.putSerializable("Details", recipe);
        fragment.setArguments(args);
        return fragment;
    }
}
