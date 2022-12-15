package es.unex.giiis.asee.proyecto.login_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private RegisterValidator mRegisterValidator;
    private EditText username, completename, email, age, weight, height, password, repeat_password;
    private Button singUp;
    private ConstraintLayout gif;
    private SharedPreferences sp;
    private List<UserItem> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new AsyncLoad().execute();

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
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",user.getUsername());
        editor.putString("password",user.getPassword());
        editor.apply();

        new AsyncUserInsert().execute(user);
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

    class AsyncLoad extends AsyncTask<Void, Void, List<UserItem>> {

        @Override
        protected List<UserItem> doInBackground(Void... voids){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(RegisterActivity.this);
            List<UserItem> items = nutrifitDb.userItemDao().getAll();

            return items;
        }

        @Override
        protected void onPostExecute(List<UserItem> items){
            super.onPostExecute(items);
            users = items;
        }
    }

    class AsyncUserInsert extends AsyncTask<UserItem, Void, UserItem>{

        @Override
        protected UserItem doInBackground(UserItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(RegisterActivity.this);
            Long id = nutrifitDb.userItemDao().insert(items[0]);

            items[0].setId(id);

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
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(RegisterActivity.this);
            Long id = nutrifitDb.weightRecordItemDao().insert(items[0]);

            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(WeightRecordItem item){
            super.onPostExecute(item);
            finishRegister();
        }
    }
}