package es.unex.giiis.asee.proyecto.ui.perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.login_register.RegisterActivity;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class EditWeightActivity extends AppCompatActivity {

    private EditText edWeight;
    private Toolbar mToolbar;
    private Double weight;
    private Button edit;
    private UserItem user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_weight);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit weight");

        edWeight = findViewById(R.id.weight_edit_editTex);
        edit = findViewById(R.id.sign_up);

        weight = getIntent().getDoubleExtra("Data", 0.0);
        user = new UserItem(getIntent());

        edWeight.setText(String.valueOf(weight));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateChange();
            }
        });
    }

    private void validateChange() {
        Double newWeight = Double.parseDouble(String.valueOf(edWeight.getText()));
        if (newWeight >= 20.0 && newWeight <= 200.0){
            user.setWeight(newWeight);
            new AsyncUserUpdate().execute(user);
        } else {
            Toast toast = Toast.makeText(EditWeightActivity.this, R.string.weight_format, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void finishEdit() {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        UserItem.packageIntent(data, user.getId(), user.getUsername(),
                user.getCompletename(), user.getEmail(), user.getAge(),
                user.getWeight(), user.getHeight(), user.getPassword());
        finish();
    }

    class AsyncUserUpdate extends AsyncTask<UserItem, Void, UserItem> {

        @Override
        protected UserItem doInBackground(UserItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(EditWeightActivity.this);
            int rows = nutrifitDb.userItemDao().update(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(UserItem item){
            super.onPostExecute(item);
            new AsyncRecordInsert().execute(new WeightRecordItem(item.getId(), item.getWeight(), new Date()));
        }
    }

    class AsyncRecordInsert extends AsyncTask<WeightRecordItem, Void, WeightRecordItem>{

        @Override
        protected WeightRecordItem doInBackground(WeightRecordItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(EditWeightActivity.this);
            Long id = nutrifitDb.weightRecordItemDao().insert(items[0]);

            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(WeightRecordItem item){
            super.onPostExecute(item);
            finishEdit();
        }
    }
}