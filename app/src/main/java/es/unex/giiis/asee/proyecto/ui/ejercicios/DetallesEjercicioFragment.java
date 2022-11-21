package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;


public class DetallesEjercicioFragment extends Fragment {

    private Toolbar mToolbar;
    private Excercise excercise;
    private SharedPreferences sp;
    private TextView nameView, typeView, muscleView, equipmentView, difficultyView, instructionsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_ejercicio, container, false);
        Bundle args = getArguments();
        excercise = (Excercise) args.getSerializable("Details");
        mToolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Excercise details");
        setHasOptionsMenu(true);

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        bindViews(v);
        fillInformation();

        return v;
    }

    private void bindViews(View v) {
        nameView = v.findViewById(R.id.nameView);
        typeView = v.findViewById(R.id.typeView);
        muscleView = v.findViewById(R.id.muscleView);
        equipmentView = v.findViewById(R.id.equipmentView);
        difficultyView = v.findViewById(R.id.difficultyView);
        instructionsView = v.findViewById(R.id.instructionsView);
    }

    private void fillInformation() {
        nameView.setText(excercise.getName());
        typeView.setText(excercise.getType());
        muscleView.setText(excercise.getMuscle());
        equipmentView.setText(excercise.getEquipment());
        difficultyView.setText(excercise.getDifficulty());
        instructionsView.setText(excercise.getInstructions());
    }
}