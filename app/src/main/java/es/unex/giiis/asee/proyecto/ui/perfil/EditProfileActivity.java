package es.unex.giiis.asee.proyecto.ui.perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import es.unex.giiis.asee.proyecto.login_register.RegisterActivity;
import es.unex.giiis.asee.proyecto.login_register.RegisterValidator;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.UserViewModel;

public class EditProfileActivity extends AppCompatActivity implements EditProfileView{

    private EditProfileValidator editProfileValidator;
    private EditText username, completename, email, age, height, old_password, password, repeat_password;
    private Button edit;
    private List<UserItem> users;
    private Toolbar mToolbar;
    private UserItem currentUser;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mUserViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.userFactory).get(UserViewModel.class);

        bindViews();

        mUserViewModel.getCurrentUser().observe(this, new Observer<UserItem>() {
            @Override
            public void onChanged(UserItem item) {
                username.setText(item.getUsername());
                completename.setText(item.getCompletename());
                email.setText(item.getEmail());
                age.setText(String.valueOf(item.getAge()));
                height.setText(String.valueOf(item.getHeight()));
                currentUser = item;
            }
        });

        mUserViewModel.getAllUsers().observe(this, new Observer<List<UserItem>>() {
            @Override
            public void onChanged(List<UserItem> userItems) {
                users = userItems;
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit profile");

        editProfileValidator = new EditProfileValidator(this);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", currentUser.toLog());
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
    public UserItem getCurrentUser() {
        return currentUser;
    }

    @Override
    public void editSuccessful() {
        modifyUser();
        mUserViewModel.update(currentUser);
        finish();
    }

    private void modifyUser() {
        currentUser.setUsername(String.valueOf(username.getText()));
        currentUser.setCompletename(String.valueOf(completename.getText()));
        currentUser.setEmail(String.valueOf(email.getText()));
        currentUser.setAge(Math.round(Long.parseLong(String.valueOf(age.getText()))));
        currentUser.setHeight(Double.parseDouble(String.valueOf(height.getText())));
        if(!String.valueOf(password.getText()).equals("")) {
            currentUser.setPassword(String.valueOf(password.getText()));
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
}