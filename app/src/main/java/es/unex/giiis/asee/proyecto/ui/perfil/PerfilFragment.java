package es.unex.giiis.asee.proyecto.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.databinding.FragmentPerfilBinding;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class PerfilFragment extends Fragment {
    private static final int EDIT_PROFILE_REQUEST = 0;
    private FragmentPerfilBinding binding;
    private Toolbar mToolbar;
    private UserItem user;
    private SharedPreferences sp;
    private TextView name, completename, email, age, weight, height;
    private static final int EDIT_WEIGHT_REQUEST = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mToolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");
        setHasOptionsMenu(true);
        name = binding.nameView;
        completename = binding.completeNameView;
        email = binding.emailView;
        age = binding.ageView;
        weight = binding.weightView;
        height = binding.heightView;

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        new AsyncLoad().execute();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_toolbar, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProfile:
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                UserItem.packageIntent(intent, user.getId(), user.getUsername(),
                        user.getCompletename(), user.getEmail(), user.getAge(),
                        user.getWeight(), user.getHeight(), user.getPassword());
                startActivityForResult(intent, EDIT_PROFILE_REQUEST);
                break;
            case R.id.editWeight:
                Intent intent2 = new Intent(getContext(), EditWeightActivity.class);
                UserItem.packageIntent(intent2, user.getId(), user.getUsername(),
                        user.getCompletename(), user.getEmail(), user.getAge(),
                        user.getWeight(), user.getHeight(), user.getPassword());
                intent2.putExtra("Data", user.getWeight());
                startActivityForResult(intent2, EDIT_WEIGHT_REQUEST);
                break;
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST) {
            if (resultCode == RESULT_OK) {
                UserItem updatedUser = new UserItem(data);
                user = updatedUser;

                SharedPreferences.Editor editor = sp.edit();
                editor.remove("username");
                editor.remove("password");

                editor.putString("username", user.getUsername());
                editor.putString("password", user.getPassword());
                editor.apply();

                fillInformation();
            }
        }
        else if (requestCode == EDIT_WEIGHT_REQUEST) {
            if (resultCode == RESULT_OK) {
                UserItem updatedUser = new UserItem(data);
                user = updatedUser;
                fillInformation();
            }
        }
    }

    private void fillInformation() {
        name.setText(user.getUsername());
        completename.setText(user.getCompletename());
        email.setText(user.getEmail());
        age.setText(String.valueOf(user.getAge()));
        weight.setText(String.valueOf(user.getWeight()));
        height.setText(String.valueOf(user.getHeight()));
    }

    class AsyncLoad extends AsyncTask<Void, Void, UserItem> {

        @Override
        protected UserItem doInBackground(Void... voids){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            UserItem item = nutrifitDb.userItemDao().getUser(sp.getLong("id", 0));

            return item;
        }

        @Override
        protected void onPostExecute(UserItem item){
            super.onPostExecute(item);
            user = item;
            fillInformation();
        }
    }


}