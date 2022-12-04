package es.unex.giiis.asee.proyecto.ui.perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.login_register.RegisterActivity;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.UserViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.WeightViewModel;

public class EditWeightActivity extends AppCompatActivity {

    private EditText edWeight;
    private Toolbar mToolbar;
    private Button edit;
    private UserItem user;

    private UserViewModel mUserViewModel;
    private WeightViewModel mWeightRecordItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_weight);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mUserViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.userFactory).get(UserViewModel.class);
        mWeightRecordItem = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.weightFactory).get(WeightViewModel.class);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit weight");

        edWeight = findViewById(R.id.weight_edit_editTex);
        edit = findViewById(R.id.sign_up);

        mUserViewModel.getCurrentUser().observe(this, new Observer<UserItem>() {
            @Override
            public void onChanged(UserItem item) {
                edWeight.setText(String.valueOf(item.getWeight()));
                user = item;
            }
        });

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
            mUserViewModel.update(user);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mWeightRecordItem.insert(new WeightRecordItem(user.getId(), user.getWeight(), new Date()));
                    finish();
                }
            });
        } else {
            Toast toast = Toast.makeText(EditWeightActivity.this, R.string.weight_format, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}