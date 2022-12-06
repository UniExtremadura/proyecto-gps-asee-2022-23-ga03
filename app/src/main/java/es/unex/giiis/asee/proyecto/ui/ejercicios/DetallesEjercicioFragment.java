package es.unex.giiis.asee.proyecto.ui.ejercicios;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.ui.horario.AddEventToHorarioActivity;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;
import es.unex.giiis.asee.proyecto.viewmodels.FavoriteExcerciseViewModel;


public class DetallesEjercicioFragment extends Fragment {

    private static final int ADD_TO_CALENDAR = 1;
    private Toolbar mToolbar;
    private Excercise excercise;
    private SharedPreferences sp;
    private ImageButton bHorario, bFavorite;
    private boolean favoriteState = false;
    private TextView nameView, typeView, muscleView, equipmentView, difficultyView, instructionsView;

    private FavoriteExcerciseViewModel mFavoriteExcerciseViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_ejercicio, container, false);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mFavoriteExcerciseViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.favoriteExcerciseFactory).get(FavoriteExcerciseViewModel.class);

        bindViews(v);

        Bundle args = getArguments();
        excercise = (Excercise) args.getSerializable("Details");

        mToolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Excercise details");

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        mFavoriteExcerciseViewModel.getUserFavorites().observe(getViewLifecycleOwner(), new Observer<List<FavoriteExcerciseItem>>() {
            @Override
            public void onChanged(List<FavoriteExcerciseItem> favoriteExcerciseItems) {
                if(favoriteExcerciseItems != null) {
                    for(FavoriteExcerciseItem item : favoriteExcerciseItems) {
                        if(item.getTittle().equals(excercise.getName())) {
                            favoriteState = true;
                            break;
                        }
                    }
                }
            }
        });

        fillInformation();

        bHorario.setOnClickListener (v1 -> agregarAHorario ());
        bFavorite.setOnClickListener(v13 -> cambiarFavorito());

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
        bFavorite = v.findViewById(R.id.favoriteButton);
    }

    public void cambiarFavorito(){
        Log.d("Estado", String.valueOf(favoriteState));
            if(favoriteState) {
                eliminarFavorito();
                favoriteState = false;
            } else {
                agregarFavorito();
                favoriteState = true;
            }
    }

    private void eliminarFavorito(){
        mFavoriteExcerciseViewModel.delete(excercise.getName());
        Toast toast = Toast.makeText(getContext(), "Excercise deleted from favorites", Toast.LENGTH_LONG);
        toast.show();
    }

    private void agregarFavorito(){
        long userid = sp.getLong("id", 0);
        FavoriteExcerciseItem item = new FavoriteExcerciseItem(excercise.getName(),
                excercise.getType(), excercise.getMuscle(),
                excercise.getEquipment(), excercise.getDifficulty(), excercise.getInstructions(), userid);
        mFavoriteExcerciseViewModel.insert(item);
        Toast toast = Toast.makeText(getContext(), "Excercise added to favorites", Toast.LENGTH_LONG);
        toast.show();
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
                toast = Toast.makeText(getContext(), "Exercise added to calendar", Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG);
            }
            toast.show();
        }
    }
}