package es.unex.giiis.asee.proyecto.login_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.MainActivity;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private static final int SIGN_UP_REQUEST = 0;
    private static final String TAG = "Dietas_fragment";

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

        new AsyncLoad().execute();

        sp = getSharedPreferences("UserPref", MODE_PRIVATE);
        mLoginValidator = new LoginValidator(this);

        bindViews();
        recover();
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

    private void recover() {
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");

        Log.d("Username recover:", username);
        Log.d("Password recover:", password);

        if (!username.equals("")) {
            this.username.setText(username);
            if (!password.equals("")) {
                this.password.setText(password);
            }
        }
    }

    private void launchRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, SIGN_UP_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("Entered onActivityResult()");

        if (requestCode == SIGN_UP_REQUEST) {
            if (resultCode == RESULT_OK) {
                new AsyncLoad().execute();
                recover();
            }
        }
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
        password.setText("");
        username.setText("");
    }

    @Override
    public void loginSuccessfull(UserItem user) {
        gif.setVisibility(View.INVISIBLE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("id", user.getId());
        editor.putString("username",user.getUsername());
        editor.putString("password",user.getPassword());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<UserItem>> {

        @Override
        protected List<UserItem> doInBackground(Void... voids){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(LoginActivity.this);
            List<UserItem> items = nutrifitDb.userItemDao().getAll();

            return items;
        }

        @Override
        protected void onPostExecute(List<UserItem> items){
            super.onPostExecute(items);
            users = items;
        }
    }
}