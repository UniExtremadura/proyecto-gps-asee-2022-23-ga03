package es.unex.giiis.asee.proyecto.ui.ejercicios;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.horario.AddEventToHorarioActivity;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaFragment;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;


public class DetallesEjercicioFragment extends Fragment {

    private List<FavoriteExcerciseItem> favoritesList;
    private static final int ADD_TO_CALENDAR = 1;
    private Toolbar mToolbar;
    private Excercise excercise;
    private SharedPreferences sp;
    private ImageButton bHorario, bFavorite;
    private boolean favoriteState = false;
    private boolean executing = false;
    private long favID = 0;
    private TextView nameView, typeView, muscleView, equipmentView, difficultyView, instructionsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_ejercicio, container, false);

        bindViews(v);

        Bundle args = getArguments();
        excercise = (Excercise) args.getSerializable("Details");

        mToolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Excercise details");

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        new AsyncLoad().execute();

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
        if(!executing) {
            executing = true;
            if(favoriteState) {
                eliminarFavorito();
                favoriteState = false;
            } else {
                agregarFavorito();
                favoriteState = true;
            }
        }
    }

    private void eliminarFavorito(){
        long userid = sp.getLong("id", 0);
        FavoriteExcerciseItem item = new FavoriteExcerciseItem(favID, excercise.getName(),
                excercise.getType(), excercise.getMuscle(),
                excercise.getEquipment(), excercise.getDifficulty(), excercise.getInstructions(), userid);
        new AsyncDelete().execute(item);
        Toast toast = Toast.makeText(getContext(), "Excercise deleted from favorites", Toast.LENGTH_LONG);
        toast.show();
    }

    private void agregarFavorito(){
        long userid = sp.getLong("id", 0);
        FavoriteExcerciseItem item = new FavoriteExcerciseItem(excercise.getName(),
                excercise.getType(), excercise.getMuscle(),
                excercise.getEquipment(), excercise.getDifficulty(), excercise.getInstructions(), userid);
        new AsyncInsert().execute(item);
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

    class AsyncLoad extends AsyncTask<Void, Void, List<FavoriteExcerciseItem>> {

        @Override
        protected List<FavoriteExcerciseItem> doInBackground(Void... voids){
            long userid = sp.getLong("id", 0);
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            List<FavoriteExcerciseItem> items = nutrifitDb.favoriteExcerciseItemDao().getAll(userid);

            return items;
        }

        @Override
        protected void onPostExecute(List<FavoriteExcerciseItem> items){
            super.onPostExecute(items);
            favoritesList = items;
            if(favoritesList != null) {
                for(FavoriteExcerciseItem item : favoritesList) {
                    if(item.getTittle().equals(excercise.getName())) {
                        favoriteState = true;
                        favID = item.getId();
                        break;
                    }
                }
            }
        }
    }

    class AsyncInsert extends AsyncTask<FavoriteExcerciseItem, Void, FavoriteExcerciseItem> {

        @Override
        protected FavoriteExcerciseItem doInBackground(FavoriteExcerciseItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            Long id = nutrifitDb.favoriteExcerciseItemDao().insert(items[0]);

            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(FavoriteExcerciseItem items){
            super.onPostExecute(items);
            executing = false;
            favID = items.getId();
        }
    }

    class AsyncDelete extends AsyncTask<FavoriteExcerciseItem, Void, Integer> {

        @Override
        protected Integer doInBackground(FavoriteExcerciseItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            int rows = nutrifitDb.favoriteExcerciseItemDao().delete(items[0]);

            return rows;
        }

        @Override
        protected void onPostExecute(Integer rows){
            super.onPostExecute(rows);
            executing = false;
        }
    }
}