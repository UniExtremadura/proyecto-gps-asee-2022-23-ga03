package es.unex.giiis.asee.proyecto.ui.horario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import es.unex.giiis.asee.proyecto.R;

public class DetallesPlantillaSwipeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private List<PlantillaItem> data;
    private PlantillaItem item;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_plantilla_swipe);

        Intent intent = getIntent();
        item = new PlantillaItem(intent);
        data = (List<PlantillaItem>) intent.getSerializableExtra("data");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Diets details");

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new DetallesPlantillaPagerAdapter(data, getSupportFragmentManager(),getLifecycle()));

        for(int i = 0; i< data.size(); i++){
            if (data.get(i).getId() == item.getId()) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}