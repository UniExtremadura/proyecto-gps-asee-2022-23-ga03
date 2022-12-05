package es.unex.giiis.asee.proyecto.ui.horario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public class DetallesPlantillaPagerAdapter extends FragmentStateAdapter {

    private List<PlantillaItem> data;

    public DetallesPlantillaPagerAdapter(List<PlantillaItem> data, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        DetallesPlantillaFragment fragment = new DetallesPlantillaFragment();
        Bundle args = new Bundle();
        args.putSerializable("Details", data.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<PlantillaItem> data) {
        this.data = data;
    }
}

