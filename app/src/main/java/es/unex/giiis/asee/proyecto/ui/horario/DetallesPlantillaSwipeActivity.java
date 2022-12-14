package es.unex.giiis.asee.proyecto.ui.horario;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.viewmodels.DietListViewModel;

public class DetallesPlantillaSwipeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private List<PlantillaItem> data;
    private PlantillaItem item;
    private ViewPager2 viewPager;
    private DetallesPlantillaPagerAdapter pAdapter;

    private DietListViewModel mDietListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_plantilla_swipe);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mDietListViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.dietFactory).get(DietListViewModel.class);

        Intent intent = getIntent();
        item = new PlantillaItem(intent);
        data = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Diets details");

        pAdapter = new DetallesPlantillaPagerAdapter(data, getSupportFragmentManager(),getLifecycle());

        mDietListViewModel.getUserDiets().observe(this, new Observer<List<PlantillaItem>>() {
            @Override
            public void onChanged(List<PlantillaItem> plantillaItems) {
                data = plantillaItems;
                pAdapter.setData(data);

                for(int i = 0; i< data.size(); i++){
                    if (data.get(i).getId() == item.getId()) {
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