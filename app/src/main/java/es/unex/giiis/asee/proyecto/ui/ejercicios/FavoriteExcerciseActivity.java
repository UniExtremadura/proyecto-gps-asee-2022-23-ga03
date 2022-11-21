package es.unex.giiis.asee.proyecto.ui.ejercicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeActivity;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeListAdapter;

public class FavoriteExcerciseActivity extends AppCompatActivity implements FavoriteExcerciseListAdapter.OnListInteractionListener, FavoriteExcerciseListAdapter.OnDeleteButtonInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteExcerciseListAdapter mAdapter;
    private Toolbar mToolbar;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_excercise);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Favorites");

        sp = getSharedPreferences("UserPref", MODE_PRIVATE);

        recyclerView = findViewById(R.id.favoritelist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FavoriteExcerciseListAdapter(new ArrayList<>(), this, this);

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncLoad().execute();
    }

    @Override
    public void onListInteraction(FavoriteExcerciseItem item) {
        Intent intent = new Intent(FavoriteExcerciseActivity.this, DetallesEjercicioActivity.class);
        intent.putExtra("Data", (Serializable) item);
        startActivity(intent);
    }

    @Override
    public void onDeleteInteraction(FavoriteExcerciseItem item) {
        new AsyncDelete().execute(item);
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<FavoriteExcerciseItem>> {

        @Override
        protected List<FavoriteExcerciseItem> doInBackground(Void... voids){
            long userid = sp.getLong("id", 0);
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(FavoriteExcerciseActivity.this);
            List<FavoriteExcerciseItem> items = nutrifitDb.favoriteExcerciseItemDao().getAll(userid);

            return items;
        }

        @Override
        protected void onPostExecute(List<FavoriteExcerciseItem> items){
            super.onPostExecute(items);
            mAdapter.swap(items);
        }
    }

    class AsyncDelete extends AsyncTask<FavoriteExcerciseItem, Void, FavoriteExcerciseItem> {

        @Override
        protected FavoriteExcerciseItem doInBackground(FavoriteExcerciseItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(FavoriteExcerciseActivity.this);
            nutrifitDb.favoriteExcerciseItemDao().delete(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(FavoriteExcerciseItem item){
            super.onPostExecute(item);
            mAdapter.delete(item);
        }
    }
}