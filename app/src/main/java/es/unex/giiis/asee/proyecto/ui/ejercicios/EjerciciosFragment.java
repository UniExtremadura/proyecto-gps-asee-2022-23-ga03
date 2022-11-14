package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.unex.giiis.asee.proyecto.databinding.FragmentEjerciciosBinding;

public class EjerciciosFragment extends Fragment {

    private FragmentEjerciciosBinding binding;
    private Toolbar mToolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEjerciciosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mToolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipes");
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}