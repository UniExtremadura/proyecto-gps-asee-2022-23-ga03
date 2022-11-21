package es.unex.giiis.asee.proyecto.ui.ejercicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;

public class DetallesEjercicioActivity extends AppCompatActivity {

    Excercise excercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ejercicio);

        Intent intent = getIntent();
        FavoriteExcerciseItem favExcercise = (FavoriteExcerciseItem) intent.getSerializableExtra("Data");

        excercise = new Excercise(favExcercise.getTittle(), favExcercise.getType(), favExcercise.getMuscle(), favExcercise.getEquipment(),
                favExcercise.getDifficulty(), favExcercise.getInstructions());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, newInstance());
        fragmentTransaction.commit();
    }

    public DetallesEjercicioFragment newInstance() {
        DetallesEjercicioFragment fragment = new DetallesEjercicioFragment();
        Bundle args = new Bundle();
        args.putSerializable("Details", excercise);
        fragment.setArguments(args);
        return fragment;
    }
}