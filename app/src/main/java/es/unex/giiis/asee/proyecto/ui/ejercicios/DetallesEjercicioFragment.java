package es.unex.giiis.asee.proyecto.ui.ejercicios;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Date;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.horario.AddEventToHorarioActivity;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;


public class DetallesEjercicioFragment extends Fragment {
    private static final int ADD_TO_CALENDAR = 1;
    private Toolbar mToolbar;
    private Excercise excercise;
    private SharedPreferences sp;
    private ImageButton bHorario;
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

        bHorario.setOnClickListener (v1 -> agregarAHorario ());

        return v;
    }

    public void agregarAHorario(){
        String date = String.valueOf(LocalDate.now());
        Date time = new Date();
        Intent intent = new Intent(getContext(), AddEventToHorarioActivity.class);
        CalendarDayItem.packageIntent(intent, 0, excercise.getName (), "", CalendarDayItem.Status.NOTDONE,
                date, time, sp.getLong("id", 0), "Excercise");
        intent.putExtra("Mode", "Insert");
        startActivityForResult(intent, ADD_TO_CALENDAR);
    }

    private void bindViews(View v) {
        nameView = v.findViewById(R.id.nameView);
        typeView = v.findViewById(R.id.typeView);
        muscleView = v.findViewById(R.id.muscleView);
        equipmentView = v.findViewById(R.id.equipmentView);
        difficultyView = v.findViewById(R.id.difficultyView);
        instructionsView = v.findViewById(R.id.instructionsView);
        bHorario = v.findViewById (R.id.horarioButton);
    }

    private void fillInformation() {
        nameView.setText(excercise.getName());
        typeView.setText(excercise.getType());
        muscleView.setText(excercise.getMuscle());
        equipmentView.setText(excercise.getEquipment());
        difficultyView.setText(excercise.getDifficulty());
        instructionsView.setText(excercise.getInstructions());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast toast;
        if (requestCode == ADD_TO_CALENDAR) {
            if (resultCode == RESULT_OK) {
                toast = Toast.makeText(getContext(), "Exercise added to diet", Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG);
            }
            toast.show();
        }
    }
}