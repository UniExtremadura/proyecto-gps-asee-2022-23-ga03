package es.unex.giiis.asee.proyecto.ui.perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.login_register.RegisterActivity;
import es.unex.giiis.asee.proyecto.login_register.RegisterValidator;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class EditProfileActivity extends AppCompatActivity implements EditProfileView{

    private EditProfileValidator editProfileValidator;
    private EditText username, completename, email, age, height, old_password, password, repeat_password;
    private Button edit;
    private List<UserItem> users;
    private UserItem user;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        user = new UserItem(getIntent());

        new AsyncLoad().execute();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit profile");

        editProfileValidator = new EditProfileValidator(this, user);
        bindViews();
        fillInformation();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileValidator.validateEdit(String.valueOf(username.getText()).trim(), String.valueOf(completename.getText()).trim(), String.valueOf(email.getText()).trim(), formatAge(String.valueOf(age.getText())), formatHeight(String.valueOf(height.getText())), String.valueOf(old_password.getText()).trim(), String.valueOf(password.getText()).trim(), String.valueOf(repeat_password.getText()).trim());
            }
        });
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
    public void editSuccessful() {
        modifyUser();
        new AsyncUpdate().execute(user);
    }

    private void fillInformation() {
        username.setText(user.getUsername());
        completename.setText(user.getCompletename());
        email.setText(user.getEmail());
        age.setText(String.valueOf(user.getAge()));
        height.setText(String.valueOf(user.getHeight()));
    }

    private void modifyUser() {
        user.setUsername(String.valueOf(username.getText()));
        user.setCompletename(String.valueOf(completename.getText()));
        user.setEmail(String.valueOf(email.getText()));
        user.setAge(Math.round(Long.parseLong(String.valueOf(age.getText()))));
        user.setHeight(Double.parseDouble(String.valueOf(height.getText())));
        if(!String.valueOf(password.getText()).equals("")) {
            user.setPassword(String.valueOf(password.getText()));
        }
    }

    @Override
    public void editFailureUsername() {
        Toast toast = Toast.makeText(EditProfileActivity.this, R.string.user_used, Toast.LENGTH_LONG);
        toast.show();
        username.setText("");
    }

    @Override
    public void editFailureEmail() {
        Toast toast = Toast.makeText(EditProfileActivity.this, R.string.email_used, Toast.LENGTH_LONG);
        toast.show();
        email.setText("");
    }

    @Override
    public void editFailurePasswords() {
        Toast toast = Toast.makeText(EditProfileActivity.this, R.string.not_same_passwords, Toast.LENGTH_LONG);
        toast.show();
        password.setText("");
        repeat_password.setText("");
    }

    @Override
    public void fillFields() {
        Toast toast = Toast.makeText(EditProfileActivity.this, R.string.fill_all_fields, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void bindViews() {
        username = findViewById(R.id.username_register_editTex);
        completename = findViewById(R.id.complete_name_editText_register);
        email = findViewById(R.id.email_editText_register);
        age = findViewById(R.id.age_editText_register);
        height = findViewById(R.id.height_editTex);
        old_password = findViewById(R.id.old_password_editText_register);
        password = findViewById(R.id.password_editText_register);
        repeat_password = findViewById(R.id.repeat_password_editText_register);
        edit = findViewById(R.id.sign_up);
    }

    @Override
    public void failureEmailFormat() {
        Toast toast = Toast.makeText(EditProfileActivity.this, R.string.email_format, Toast.LENGTH_LONG);
        toast.show();
        email.setText("");
    }

    @Override
    public void editFailureAge() {
        Toast toast = Toast.makeText(EditProfileActivity.this, R.string.age_format, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void editFailurePasswordOld() {
        Toast toast = Toast.makeText(EditProfileActivity.this, "Wrong password", Toast.LENGTH_LONG);
        toast.show();
        old_password.setText("");
    }

    @Override
    public void registerFailureHeight() {
        Toast toast = Toast.makeText(EditProfileActivity.this, R.string.height_format, Toast.LENGTH_LONG);
        toast.show();
    }

    private void finishEdit() {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        UserItem.packageIntent(data, user.getId(), user.getUsername(),
                user.getCompletename(), user.getEmail(), user.getAge(),
                user.getWeight(), user.getHeight(), user.getPassword());
        finish();
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<UserItem>> {

        @Override
        protected List<UserItem> doInBackground(Void... voids){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(EditProfileActivity.this);
            List<UserItem> items = nutrifitDb.userItemDao().getAll();

            return items;
        }

        @Override
        protected void onPostExecute(List<UserItem> items){
            super.onPostExecute(items);
            users = items;
        }
    }

    class AsyncUpdate extends AsyncTask<UserItem, Void, UserItem>{

        @Override
        protected UserItem doInBackground(UserItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(EditProfileActivity.this);
            int rows = nutrifitDb.userItemDao().update(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(UserItem item){
            super.onPostExecute(item);
            finishEdit();
        }
    }
}