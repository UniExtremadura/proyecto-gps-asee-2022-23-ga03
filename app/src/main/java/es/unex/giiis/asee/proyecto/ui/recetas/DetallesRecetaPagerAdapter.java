package es.unex.giiis.asee.proyecto.ui.recetas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModel;

public class DetallesRecetaPagerAdapter extends FragmentStateAdapter {

    private List<Recipe> data;

    public DetallesRecetaPagerAdapter(List<Recipe> data,  @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        DetallesRecetaFragment fragment = new DetallesRecetaFragment();
        Bundle args = new Bundle();
        args.putSerializable("Details", data.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Recipe> data) {
        this.data = data;
    }
}
