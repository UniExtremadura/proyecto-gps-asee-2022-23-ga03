package es.unex.giiis.asee.proyecto.ui.recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.gson.Gson;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public class DetallesRecetaActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_receta);

        Gson gson = new Gson();
        recipe = gson.fromJson(getIntent().getStringExtra("Recipe"), Recipe.class);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, newInstance());
        fragmentTransaction.commit();
    }

    public DetallesRecetaFragment newInstance() {
        DetallesRecetaFragment fragment = new DetallesRecetaFragment();
        Bundle args = new Bundle();
        args.putSerializable("Details", recipe);
        fragment.setArguments(args);
        return fragment;
    }
}
