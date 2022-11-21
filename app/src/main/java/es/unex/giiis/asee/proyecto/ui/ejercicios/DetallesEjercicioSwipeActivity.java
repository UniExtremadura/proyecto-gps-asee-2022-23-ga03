package es.unex.giiis.asee.proyecto.ui.ejercicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaPagerAdapter;

public class DetallesEjercicioSwipeActivity extends AppCompatActivity {

    private List<Excercise> data;
    private String name;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ejercicio_swipe);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        data = (List<Excercise>) intent.getSerializableExtra("Data");
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new DetallesEjercicioPagerAdapter(data, getSupportFragmentManager(), getLifecycle()));

        for (int i = 0; i < data.size(); i++) {
            String nameBucle = data.get(i).getName();
            if (nameBucle.equals(name)) {
                viewPager.setCurrentItem(i);
                break;
            }

        }
    }
}