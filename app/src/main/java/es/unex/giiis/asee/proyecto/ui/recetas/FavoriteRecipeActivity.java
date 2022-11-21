package es.unex.giiis.asee.proyecto.ui.recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class FavoriteRecipeActivity extends AppCompatActivity implements FavoriteRecipeListAdapter.OnListInteractionListener, FavoriteRecipeListAdapter.OnDeleteButtonInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteRecipeListAdapter mAdapter;
    private Toolbar mToolbar;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Favorites");

        sp = getSharedPreferences("UserPref", MODE_PRIVATE);

        recyclerView = findViewById(R.id.favoritelist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FavoriteRecipeListAdapter(new ArrayList<>(), this, this);

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncLoad().execute();
    }

    @Override
    public void onListInteraction(FavoriteRecipeItem item) {

    }

    @Override
    public void onDeleteInteraction(FavoriteRecipeItem item) {
        new AsyncDelete().execute(item);
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<FavoriteRecipeItem>> {

        @Override
        protected List<FavoriteRecipeItem> doInBackground(Void... voids){
            long userid = sp.getLong("id", 0);
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(FavoriteRecipeActivity.this);
            List<FavoriteRecipeItem> items = nutrifitDb.favoriteRecipeItemDao().getAll(userid);

            return items;
        }

        @Override
        protected void onPostExecute(List<FavoriteRecipeItem> items){
            super.onPostExecute(items);
            mAdapter.swap(items);
        }
    }

    class AsyncDelete extends AsyncTask<FavoriteRecipeItem, Void, FavoriteRecipeItem> {

        @Override
        protected FavoriteRecipeItem doInBackground(FavoriteRecipeItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(FavoriteRecipeActivity.this);
            nutrifitDb.favoriteRecipeItemDao().delete(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(FavoriteRecipeItem item){
            super.onPostExecute(item);
            mAdapter.delete(item);
        }
    }
}