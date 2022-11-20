package es.unex.giiis.asee.proyecto.ui.horario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.unex.giiis.asee.proyecto.R;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddPlantillaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Add_Plantilla_Activity";

    private Toolbar mToolbar;
    private EditText mTitleText;
    private Spinner mDaySpinner;
    private Spinner mPrioritySpinner;
    private final List<String> dayList = new ArrayList<>(Stream.of(PlantillaItem.Day.values()).map(Enum::name).collect(Collectors.toList()));
    private final List<String> priorityList = new ArrayList<>(Stream.of(PlantillaItem.Priority.values()).map(Enum::name).collect(Collectors.toList()));
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plantilla);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add diet");

        sp = getSharedPreferences("UserPref", MODE_PRIVATE);

        mTitleText = findViewById(R.id.title);
        mDaySpinner = findViewById(R.id.day_spinner);
        mPrioritySpinner = findViewById(R.id.priority_spinner);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(AddPlantillaActivity.this, android.R.layout.simple_spinner_dropdown_item, dayList);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(AddPlantillaActivity.this, android.R.layout.simple_spinner_dropdown_item, priorityList);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mDaySpinner.setAdapter(dayAdapter);
        mPrioritySpinner.setAdapter(priorityAdapter);
        // OnClickListener for the Cancel Button,

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered cancelButton.OnClickListener.onClick()");

                // - Implement onClick().
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();

            }
        });

        //OnClickListener for the Reset Button

        final Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered resetButton.OnClickListener.onClick()");

                // - Reset data fields to default values
                mTitleText.setText("");
                mDaySpinner.setSelection(0);
                mPrioritySpinner.setSelection(0);
            }
        });

        // OnClickListener for the Submit Button
        // Implement onClick().

        final Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");

                // Gather ToDoItem data

                // - Get Priority
                PlantillaItem.Priority priority = getPriority();

                // -  Get Status
                PlantillaItem.Day day = getDay();

                // -  Title
                String titleString = mTitleText.getText().toString();

                // Package ToDoItem data into an Intent
                Intent data = new Intent();
                PlantillaItem.packageIntent(data, 0, titleString, priority, day, sp.getLong("id", 0));

                // - return data Intent and finish
                setResult(RESULT_OK, data);
                finish();

            }
        });

    }

    private PlantillaItem.Priority getPriority() {

        log(priorityList.get(mPrioritySpinner.getSelectedItemPosition()));
        return PlantillaItem.Priority.valueOf(priorityList.get(mPrioritySpinner.getSelectedItemPosition()));
    }

    private PlantillaItem.Day getDay() {

        log(dayList.get(mDaySpinner.getSelectedItemPosition()));
        return PlantillaItem.Day.valueOf(dayList.get(mDaySpinner.getSelectedItemPosition()));
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
}