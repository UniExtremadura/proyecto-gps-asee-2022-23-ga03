package es.unex.giiis.asee.proyecto.login_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.UserViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.WeightViewModel;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private RegisterValidator mRegisterValidator;
    private EditText username, completename, email, age, weight, height, password, repeat_password;
    private Button singUp;
    private ConstraintLayout gif;
    private SharedPreferences sp;
    private List<UserItem> users;

    private UserViewModel mUserViewModel;
    private WeightViewModel mWeightRecordItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mUserViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.userFactory).get(UserViewModel.class);
        mWeightRecordItem = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.weightFactory).get(WeightViewModel.class);

        mUserViewModel.getAllUsers().observe(this, new Observer<List<UserItem>>() {
            @Override
            public void onChanged(List<UserItem> userItems) {
                users = userItems;
            }
        });

        mRegisterValidator = new RegisterValidator(this);

        bindViews();
        gif.setVisibility(View.INVISIBLE);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gif.setVisibility(View.VISIBLE);
                mRegisterValidator.validateRegister(String.valueOf(username.getText()).trim(), String.valueOf(completename.getText()).trim(), String.valueOf(email.getText()).trim(), formatAge(String.valueOf(age.getText())), formatWeight(String.valueOf(weight.getText())), formatHeight(String.valueOf(height.getText())), String.valueOf(password.getText()).trim(), String.valueOf(repeat_password.getText()).trim());
            }
        });
        sp = getSharedPreferences("UserPref", MODE_PRIVATE);
    }

    private int formatAge(String age) {
        int ageNumber;
        if(age.equals("")) {
            ageNumber = 0;
        } else {
            ageNumber = Integer.parseInt(age);
        }
        return ageNumber;
    }

    private Double formatWeight(String weight) {
        double weightNumber;
        if(weight.equals("")) {
            weightNumber = 0.0;
        } else {
            weightNumber = Double.parseDouble(weight);
        }
        return weightNumber;
    }

    private Double formatHeight(String height) {
        double heightNumber;
        if(height.equals("")) {
            heightNumber = 0.0;
        } else {
            heightNumber = Double.parseDouble(height);
        }
        return heightNumber;
    }

    @Override
    public List<UserItem> getUsers() {
        return users;
    }

    @Override
    public void registerSuccessful(UserItem user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long id = mUserViewModel.insert(user);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        mUserViewModel.setSessionId(id);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putLong("id", id);
                        editor.apply();
                    }
                });
                mWeightRecordItem.insert(new WeightRecordItem(id, formatWeight(String.valueOf(weight.getText())), new Date()));
                finishRegister();
            }
        });
    }

    private void finishRegister() {
        gif.setVisibility(View.INVISIBLE);
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void registerFailureUsername() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.user_used, Toast.LENGTH_LONG);
        toast.show();
        username.setText("");
    }

    @Override
    public void registerFailureEmail() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.email_used, Toast.LENGTH_LONG);
        toast.show();
        email.setText("");
    }

    @Override
    public void registerFailurePasswords() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.not_same_passwords, Toast.LENGTH_LONG);
        toast.show();
        password.setText("");
        repeat_password.setText("");
    }

    @Override
    public void fillFields() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.fill_all_fields, Toast.LENGTH_LONG);
        toast.show();
    }

    public void bindViews() {
        username = findViewById(R.id.username_register_editTex);
        completename = findViewById(R.id.complete_name_editText_register);
        email = findViewById(R.id.email_editText_register);
        age = findViewById(R.id.age_editText_register);
        weight = findViewById(R.id.weight_editTex);
        height = findViewById(R.id.height_editTex);
        password = findViewById(R.id.password_editText_register);
        repeat_password = findViewById(R.id.repeat_password_editText_register);
        singUp = findViewById(R.id.sign_up);
        gif = findViewById(R.id.gif);
    }

    @Override
    public void failureEmailFormat() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.email_format, Toast.LENGTH_LONG);
        toast.show();
        email.setText("");
    }

    @Override
    public void registerFailureAge() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.age_format, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void registerFailureWeight() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.weight_format, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void registerFailureHeight() {
        gif.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(RegisterActivity.this, R.string.height_format, Toast.LENGTH_LONG);
        toast.show();
    }
}