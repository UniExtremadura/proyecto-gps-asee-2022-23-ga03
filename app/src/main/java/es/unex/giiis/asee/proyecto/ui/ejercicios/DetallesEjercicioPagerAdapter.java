package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaFragment;

public class DetallesEjercicioPagerAdapter extends FragmentStateAdapter {

    private List<Excercise> data;

    public DetallesEjercicioPagerAdapter(List<Excercise> data, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        DetallesEjercicioFragment fragment = new DetallesEjercicioFragment();
        Bundle args = new Bundle();
        args.putSerializable("Details", data.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Excercise> excercises) {
        data = excercises;
    }
}

