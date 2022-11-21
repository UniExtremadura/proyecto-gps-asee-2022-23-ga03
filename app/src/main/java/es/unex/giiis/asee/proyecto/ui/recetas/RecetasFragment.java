package es.unex.giiis.asee.proyecto.ui.recetas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.databinding.FragmentRecetasBinding;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public class RecetasFragment extends Fragment implements RecipesListAdapter.OnListInteractionListener {

    private FragmentRecetasBinding binding;
    private RecyclerView recyclerView;
    private RecipesListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar mToolbar;
    private ImageButton favoriteButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecetasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mToolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipes");
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) binding.recipelist;

        favoriteButton = binding.favoriteButtonList;

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecipesListAdapter(new ArrayList<>(), this);

        if(savedInstanceState != null){
            mAdapter.swap((List<Recipe>) savedInstanceState.getSerializable("Data"));
        } else {
            AppExecutors.getInstance().networkIO().execute(new RecipesNetworkLoaderRunnable(recipes -> mAdapter.swap(recipes)));
        }

        recyclerView.setAdapter(mAdapter);

        favoriteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), FavoriteRecipeActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Data", (Serializable) mAdapter.getDataset());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return true;
    }

    @Override
    public void onListInteraction(Recipe recipe) {
        Intent intent = new Intent(getActivity(), DetallesRecetaSwipeActivity.class);

        Gson gson = new Gson();
        String myJson = gson.toJson(recipe);

        intent.putExtra("Recipe", myJson);
        List<Recipe> data = mAdapter.getDataset();
        intent.putExtra("Data", (Serializable) data);

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}