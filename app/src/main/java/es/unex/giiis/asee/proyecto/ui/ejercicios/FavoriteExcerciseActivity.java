package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.viewmodels.FavoriteExcerciseViewModel;

public class FavoriteExcerciseActivity extends AppCompatActivity implements FavoriteExcerciseListAdapter.OnListInteractionListener, FavoriteExcerciseListAdapter.OnDeleteButtonInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteExcerciseListAdapter mAdapter;
    private Toolbar mToolbar;

    private FavoriteExcerciseViewModel mFavoriteExcerciseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_excercise);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mFavoriteExcerciseViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.favoriteExcerciseFactory).get(FavoriteExcerciseViewModel.class);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Favorites");

        recyclerView = findViewById(R.id.favoritelist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FavoriteExcerciseListAdapter(new ArrayList<>(), this, this);

        mFavoriteExcerciseViewModel.getUserFavorites().observe(this, new Observer<List<FavoriteExcerciseItem>>() {
            @Override
            public void onChanged(List<FavoriteExcerciseItem> favoriteExcerciseItems) {
                mAdapter.swap(favoriteExcerciseItems);
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListInteraction(FavoriteExcerciseItem item) {
        Intent intent = new Intent(FavoriteExcerciseActivity.this, DetallesEjercicioActivity.class);
        intent.putExtra("Data", (Serializable) item);
        startActivity(intent);
    }

    @Override
    public void onDeleteInteraction(FavoriteExcerciseItem item) {
        mFavoriteExcerciseViewModel.delete(item.getTittle());
    }
}