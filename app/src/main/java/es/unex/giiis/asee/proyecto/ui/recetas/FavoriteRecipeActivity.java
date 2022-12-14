package es.unex.giiis.asee.proyecto.ui.recetas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.viewmodels.FavoriteRecipeActivityViewModel;

public class FavoriteRecipeActivity extends AppCompatActivity implements FavoriteRecipeListAdapter.OnListInteractionListener, FavoriteRecipeListAdapter.OnDeleteButtonInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteRecipeListAdapter mAdapter;
    private Toolbar mToolbar;

    private FavoriteRecipeActivityViewModel mFavoriteRecipeActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mFavoriteRecipeActivityViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.favoriteRecipeActivityFactory).get(FavoriteRecipeActivityViewModel.class);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Favorites");

        recyclerView = findViewById(R.id.favoritelist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FavoriteRecipeListAdapter(new ArrayList<>(), this, this);

        mFavoriteRecipeActivityViewModel.getUserFavorites().observe(this, new Observer<List<FavoriteRecipeItem>>() {
            @Override
            public void onChanged(List<FavoriteRecipeItem> favoriteRecipeItems) {
                mAdapter.swap(favoriteRecipeItems);
            }
        });

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onListInteraction(FavoriteRecipeItem item) {
        mFavoriteRecipeActivityViewModel.fetchOneRecipe(item.getWebid());
        Intent intent = new Intent(FavoriteRecipeActivity.this, DetallesRecetaActivity.class);
        intent.putExtra("webid", item.getWebid());
        startActivity(intent);
    }

    @Override
    public void onDeleteInteraction(FavoriteRecipeItem item) {
        mFavoriteRecipeActivityViewModel.deleteFavorite(item.getWebid());
    }
}