package es.unex.giiis.asee.proyecto.ui.horario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

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

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class AddRecipeToDietaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Add/MODIFY_Receta_Dieta_Activity";

    private List<PlantillaItem> diets;
    private Toolbar mToolbar;
    private Spinner mDietSpinner;
    private Spinner mPeriodSpinner;
    private final List<String> periodList = new ArrayList<>(Stream.of(RecipePlantillaItem.Period.values()).map(Enum::name).collect(Collectors.toList()));
    private SharedPreferences sp;
    private RecipePlantillaItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_to_dieta);

        item = new RecipePlantillaItem(getIntent());

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add recipe to diet");



        sp = getSharedPreferences("UserPref", MODE_PRIVATE);

        mDietSpinner = findViewById(R.id.diet_spinner);
        mPeriodSpinner  = findViewById(R.id.period_spinner);

        new AsyncLoad().execute();

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

            log(item.toLog());
            new AsyncInsert().execute(item);
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
        Log.i(TAG, msg);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //nothing
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //nothing
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<PlantillaItem>> {

        @Override
        protected List<PlantillaItem> doInBackground(Void... voids){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(AddRecipeToDietaActivity.this);
            List<PlantillaItem> items = nutrifitDb.plantillaItemDao().getAll(sp.getLong("id", 0));

            return items;
        }

        @Override
        protected void onPostExecute(List<PlantillaItem> items){
            super.onPostExecute(items);
            loadDietSpinner(items);
        }
    }

    class AsyncInsert extends AsyncTask<RecipePlantillaItem, Void, RecipePlantillaItem> {

        @Override
        protected RecipePlantillaItem doInBackground(RecipePlantillaItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(AddRecipeToDietaActivity.this);
            long id = nutrifitDb.recipePlantillaItemDao().insert(items[0]);

            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(RecipePlantillaItem item){
            super.onPostExecute(item);

            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
        }
    }
}