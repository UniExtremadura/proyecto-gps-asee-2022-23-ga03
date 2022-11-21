package es.unex.giiis.asee.proyecto.ui.recetas;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class DetallesRecetaFragment extends Fragment {



    private SharedPreferences sp;
    private TextView mRecipeName,mCaloriasRacion, mRaciones, mMealType, mCuisineType, mDishType, mTime, mWeight,
            mIngredientLines, mDietLabels, mHealthLabels, mDietLines;
    private ImageView mImageView;
    private Recipe recipe;
    private Button bEnlace;
    private DecimalFormat df = new DecimalFormat("0.00");
    private Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_receta, container, false);

        Bundle args = getArguments();
        recipe = (Recipe) args.getSerializable("Details");

        String url = recipe.getUri();

        mToolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipe details");
        setHasOptionsMenu(true);

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        bindViews(v);
        DetallesRecetaPresenter detallesRecetaPresenter = new DetallesRecetaPresenter(mRecipeName, mCaloriasRacion, mRaciones,
                mMealType, mCuisineType, mDishType, mTime, mWeight,
                mIngredientLines, mDietLabels, mHealthLabels, mDietLines,
                mImageView, recipe);

        detallesRecetaPresenter.fillInformation();

        bEnlace.setOnClickListener(view -> {
            Uri webpage = Uri.parse(recipe.getUrl());
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        });


        return v;
    }



    private void bindViews(View v) {
        mRecipeName = v.findViewById(R.id.t_recipeName);
        mImageView = v.findViewById(R.id.recipeView);
        mCaloriasRacion = v.findViewById(R.id.t_caloriasRacion);
        mRaciones = v.findViewById(R.id.t_raciones);

        mMealType = v.findViewById(R.id.t_mealType);
        mCuisineType = v.findViewById(R.id.t_cuisineType);
        mDishType = v.findViewById(R.id.t_dishType);
        mTime = v.findViewById(R.id.t_time);
        mWeight = v.findViewById(R.id.t_weight);

        mIngredientLines = v.findViewById(R.id.t_ingredientLines);

        mDietLabels = v.findViewById(R.id.t_dietLabels);
        mHealthLabels = v.findViewById(R.id.t_healthLabels);
        mDietLines = v.findViewById(R.id.t_dietLines);

        bEnlace = v.findViewById(R.id.b_enlace);

    }

}