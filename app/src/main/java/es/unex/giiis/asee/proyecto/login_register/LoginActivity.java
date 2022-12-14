package es.unex.giiis.asee.proyecto.login_register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MainActivity;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.viewmodels.LoginActivityViewModel;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private static final String TAG = "LOGIN_ACTIVITY";

    private LoginActivityViewModel mLoginActivityViewModel;

    private LoginValidator mLoginValidator;
    private EditText username, password;
    private Button log, SignUp;
    private ConstraintLayout gif;
    private SharedPreferences sp;
    private List<UserItem> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mLoginActivityViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.loginFactory).get(LoginActivityViewModel.class);

        mLoginActivityViewModel.getAllUsers().observe(this, new Observer<List<UserItem>>() {
            @Override
            public void onChanged(List<UserItem> userItems) {
                users = userItems;
            }
        });

        sp = getSharedPreferences("UserPref", MODE_PRIVATE);
        mLoginValidator = new LoginValidator(this);

        bindViews();

        long id = sp.getLong("id", 0);
        mLoginActivityViewModel.setSessionId(id);
        mLoginActivityViewModel.getCurrentUser().observe(this, new Observer<UserItem>() {
            @Override
            public void onChanged(UserItem userItem) {
                if (userItem != null) {
                    username.setText(userItem.getUsername());
                    password.setText(userItem.getPassword());
                }
            }
        });


        gif.setVisibility(View.INVISIBLE);

        log.setOnClickListener(v -> {
            gif.setVisibility(View.VISIBLE);
            mLoginValidator.validateLogin(String.valueOf(username.getText()), String.valueOf(password.getText()));
        });

        SignUp.setOnClickListener(v -> launchRegister());
    }

    @Override
    public List<UserItem> getUsers() {
        return users;
    }

    private void bindViews() {
        username = findViewById(R.id.login_field);
        password = findViewById(R.id.password_field);
        log = findViewById(R.id.buttonLogin);
        SignUp = findViewById(R.id.sign_up_button_login);
        gif = findViewById(R.id.gif);
    }

    private void launchRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void log(String msg){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

    @Override
    public void loginFailure() {
        gif.setVisibility(View.INVISIBLE);

        Toast.makeText(LoginActivity.this, "Username or password invalid", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessfull(UserItem user) {
        gif.setVisibility(View.INVISIBLE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("id", user.getId());
        editor.apply();

        mLoginActivityViewModel.setSessionId(user.getId());

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}