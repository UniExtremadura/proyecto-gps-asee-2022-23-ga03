package es.unex.giiis.asee.proyecto.ui.recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.FavoriteExcerciseViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.FavoriteRecipeViewModel;

public class FavoriteRecipeActivity extends AppCompatActivity implements FavoriteRecipeListAdapter.OnListInteractionListener, FavoriteRecipeListAdapter.OnDeleteButtonInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteRecipeListAdapter mAdapter;
    private Toolbar mToolbar;

    private FavoriteRecipeViewModel mFavoriteRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mFavoriteRecipeViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.favoriteRecipeFactory).get(FavoriteRecipeViewModel.class);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Favorites");

        recyclerView = findViewById(R.id.favoritelist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FavoriteRecipeListAdapter(new ArrayList<>(), this, this);

        mFavoriteRecipeViewModel.getUserFavorites().observe(this, new Observer<List<FavoriteRecipeItem>>() {
            @Override
            public void onChanged(List<FavoriteRecipeItem> favoriteRecipeItems) {
                mAdapter.swap(favoriteRecipeItems);
            }
        });

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onListInteraction(FavoriteRecipeItem item) {

    }

    @Override
    public void onDeleteInteraction(FavoriteRecipeItem item) {
        mFavoriteRecipeViewModel.delete(item.getWebid());
    }
}