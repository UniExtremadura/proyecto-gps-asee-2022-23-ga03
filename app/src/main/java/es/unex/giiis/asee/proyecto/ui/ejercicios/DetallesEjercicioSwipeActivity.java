package es.unex.giiis.asee.proyecto.ui.ejercicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaPagerAdapter;
import es.unex.giiis.asee.proyecto.viewmodels.ExcerciseListViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModel;

public class DetallesEjercicioSwipeActivity extends AppCompatActivity {

    private List<Excercise> data;
    private String name;
    private ViewPager2 viewPager;
    private DetallesEjercicioPagerAdapter pAdapter;

    private ExcerciseListViewModel mExcerciseListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ejercicio_swipe);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mExcerciseListViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.excerciseFactory).get(ExcerciseListViewModel.class);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");

        data = new ArrayList<>();
        pAdapter = new DetallesEjercicioPagerAdapter(data, getSupportFragmentManager(), getLifecycle());

        mExcerciseListViewModel.getDownloadedExcercises().observe(this, new Observer<List<Excercise>>() {
            @Override
            public void onChanged(List<Excercise> excercises) {
                data = excercises;
                pAdapter.setData(data);

                for (int i = 0; i < data.size(); i++) {
                    String nameBucle = data.get(i).getName();
                    if (nameBucle.equals(name)) {
                        viewPager.setCurrentItem(i);
                        break;
                    }

                }
            }
        });

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pAdapter);
    }
}