package es.unex.giiis.asee.proyecto.ui.ejercicios;

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

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.databinding.FragmentEjerciciosBinding;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;

public class EjerciciosFragment extends Fragment implements ExcerciseListAdapter.OnListInteractionListener {

    private FragmentEjerciciosBinding binding;
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private ExcerciseListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEjerciciosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mToolbar = binding.toolbar;
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

        return root;
    }

    @Override
    public void onListInteraction(Excercise excercise) {



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}