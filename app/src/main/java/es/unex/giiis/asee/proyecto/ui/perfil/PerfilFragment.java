package es.unex.giiis.asee.proyecto.ui.perfil;

import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.databinding.FragmentPerfilBinding;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.viewmodels.PerfilFragmentViewModel;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding binding;
    private Toolbar mToolbar;
    private TextView name, completename, email, age, weight, height;

    private PerfilFragmentViewModel mPerfilFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mPerfilFragmentViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity(), (ViewModelProvider.Factory) appContainer.perfilFactory).get(PerfilFragmentViewModel.class);

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

        FragmentManager fm = getChildFragmentManager();
        WeightGraphFragment weightGraphFragment = new WeightGraphFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerView2, weightGraphFragment);
        fragmentTransaction.commit();

        mPerfilFragmentViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<UserItem>() {
            @Override
            public void onChanged(UserItem item) {
                name.setText(item.getUsername());
                completename.setText(item.getCompletename());
                email.setText(item.getEmail());
                age.setText(String.valueOf(item.getAge()));
                weight.setText(String.valueOf(item.getWeight()));
                height.setText(String.valueOf(item.getHeight()));
            }
        });

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
                startActivity(intent);
                break;
            case R.id.settings:
                Intent intent3 = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent3);
                break;
            case R.id.editWeight:
                Intent intent2 = new Intent(getContext(), EditWeightActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}