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
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.databinding.FragmentRecetasBinding;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.UserViewModel;

public class RecetasFragment extends Fragment implements RecipesListAdapter.OnListInteractionListener {

    private FragmentRecetasBinding binding;
    private RecyclerView recyclerView;
    private RecipesListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar mToolbar;
    private ImageButton favoriteButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private RecipeListViewModel mRecipeListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecetasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mRecipeListViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity(), (ViewModelProvider.Factory) appContainer.recipeFactory).get(RecipeListViewModel.class);

        mToolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipes");

        recyclerView = (RecyclerView) binding.recipelist;

        favoriteButton = binding.favoriteButtonList;

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecipesListAdapter(new ArrayList<>(), this);

        recyclerView.setAdapter(mAdapter);

        favoriteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), FavoriteRecipeActivity.class);
            startActivity(intent);
        });

        mProgressBar = binding.progressBar;
        mSwipeRefreshLayout = binding.swipeRefreshLayout;
        mSwipeRefreshLayout.setOnRefreshListener(mRecipeListViewModel::fetchRecipes);

        mRecipeListViewModel.getDownloadedRecipes().observe(getViewLifecycleOwner(), recipes -> {
            mAdapter.swap(recipes);
            if (recipes != null && recipes.size() != 0)
                showRecipesDataView();
            else
                showLoading();
        });

        return root;
    }

    private void showLoading(){
        mProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void showRecipesDataView(){
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListInteraction(Recipe recipe) {
        Intent intent = new Intent(getActivity(), DetallesRecetaSwipeActivity.class);

        Gson gson = new Gson();
        String myJson = gson.toJson(recipe);

        intent.putExtra("Recipe", myJson);

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}