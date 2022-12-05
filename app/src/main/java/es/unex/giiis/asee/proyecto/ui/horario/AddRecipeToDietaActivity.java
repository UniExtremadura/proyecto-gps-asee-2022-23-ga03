package es.unex.giiis.asee.proyecto.ui.horario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.DietRecipesViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.DietViewModel;

public class AddRecipeToDietaActivity extends AppCompatActivity {

    private static final String TAG = "Add/MODIFY_Receta_Dieta_Activity";

    private List<PlantillaItem> diets;
    private Toolbar mToolbar;
    private Spinner mDietSpinner;
    private Spinner mPeriodSpinner;
    private final List<String> periodList = new ArrayList<>(Stream.of(RecipePlantillaItem.Period.values()).map(Enum::name).collect(Collectors.toList()));
    private RecipePlantillaItem item;

    private DietViewModel mDietViewModel;
    private DietRecipesViewModel mDietRecipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_to_dieta);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mDietViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.dietFactory).get(DietViewModel.class);
        mDietRecipesViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.recipesDietFactory).get(DietRecipesViewModel.class);

        item = new RecipePlantillaItem(getIntent());

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add recipe to diet");

        mDietSpinner = findViewById(R.id.diet_spinner);
        mPeriodSpinner  = findViewById(R.id.period_spinner);

        mDietViewModel.getUserDiets().observe(this, new Observer<List<PlantillaItem>>() {
            @Override
            public void onChanged(List<PlantillaItem> plantillaItems) {
                loadDietSpinner(plantillaItems);
            }
        });

        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(AddRecipeToDietaActivity.this, android.R.layout.simple_spinner_dropdown_item, periodList);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mPeriodSpinner.setAdapter(periodAdapter);

        // OnClickListener for the Cancel Button,

        final Button cancelButton =  findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            log("Entered cancelButton.OnClickListener.onClick()");

            Intent data = new Intent();
            setResult(RESULT_CANCELED, data);
            finish();
        });

        final Button submitButton =  findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            log("Entered submitButton.OnClickListener.onClick()");

            item.setPeriod(getPeriod());
            item.setPlantillaid(getPlantillaid());

            mDietRecipesViewModel.insert(item);
            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
        });

    }

    private RecipePlantillaItem.Period getPeriod(){
        log(periodList.get(mPeriodSpinner.getSelectedItemPosition()));
        return RecipePlantillaItem.Period.valueOf(periodList.get(mPeriodSpinner.getSelectedItemPosition()));
    }

    private long getPlantillaid() {
        return diets.get(mDietSpinner.getSelectedItemPosition()).getId();
    }

    private void loadDietSpinner(List<PlantillaItem> items) {
        diets = items;
        List<String> namesList = new ArrayList<>();
        for (PlantillaItem item : items) {
            namesList.add(item.getTitle());
        }

        ArrayAdapter<String> dietAdapter = new ArrayAdapter<>(AddRecipeToDietaActivity.this, android.R.layout.simple_spinner_dropdown_item, namesList);
        dietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mDietSpinner.setAdapter(dietAdapter);
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}