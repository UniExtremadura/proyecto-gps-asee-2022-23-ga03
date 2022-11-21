package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.databinding.FragmentEjerciciosBinding;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaSwipeActivity;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeActivity;

public class EjerciciosFragment extends Fragment implements ExcerciseListAdapter.OnListInteractionListener {

    private FragmentEjerciciosBinding binding;
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private ExcerciseListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton favoriteButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEjerciciosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mToolbar = binding.toolbar;
        favoriteButton = binding.favoriteButtonList;

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Excercises");
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) binding.excerciseslist;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExcerciseListAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(layoutManager);
        AppExecutors.getInstance().networkIO().
                execute(new ExercisesNetworkLoaderRunnable(excersisesList -> mAdapter.swap(excersisesList)));
        recyclerView.setAdapter(mAdapter);

        favoriteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), FavoriteExcerciseActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onListInteraction(Excercise excercise) {
        Intent intent = new Intent(getActivity(), DetallesEjercicioSwipeActivity.class);
        intent.putExtra("Name", excercise.getName());
        List<Excercise> data = mAdapter.getDataset();
        intent.putExtra("Data", (Serializable) data);

        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}