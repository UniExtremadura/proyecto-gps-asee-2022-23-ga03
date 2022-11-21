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
import es.unex.giiis.asee.proyecto.ui.horario.AddEventToHorarioActivity;
import es.unex.giiis.asee.proyecto.ui.horario.AddRecipeToDietaActivity;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;
import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

public class DetallesRecetaFragment extends Fragment {

    private List<FavoriteRecipeItem> favoritesList;
    private SharedPreferences sp;
    private TextView mRecipeName,mCaloriasRacion, mRaciones, mMealType, mCuisineType, mDishType, mTime, mWeight,
            mIngredientLines, mDietLabels, mHealthLabels, mDietLines;
    private ImageView mImageView;
    private Recipe recipe;
    private Button bEnlace;
    private DecimalFormat df = new DecimalFormat("0.00");
    private Toolbar mToolbar;
    private ImageButton bHorario, bDieta, bFavorite;
    private String webid;
    private boolean favoriteState = false;
    private boolean executing = false;
    private static final int ADD_TO_CALENDAR = 1;
    private static final int ADD_TO_DIET = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_receta, container, false);

        Bundle args = getArguments();
        recipe = (Recipe) args.getSerializable("Details");

        String url = recipe.getUri();
        webid = url.substring(url.lastIndexOf("_") + 1);

        mToolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipe details");
        setHasOptionsMenu(true);

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        new AsyncLoad().execute();

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

        bHorario.setOnClickListener(v1 -> agregarAHorario());
        bDieta.setOnClickListener(v12 -> agregarADieta());
        bFavorite.setOnClickListener(v13 -> cambiarFavorito());

        return v;
    }

    public void agregarAHorario(){
        String date = String.valueOf(LocalDate.now());
        Date time = new Date();
        Intent intent = new Intent(getContext(), AddEventToHorarioActivity.class);
        CalendarDayItem.packageIntent(intent, 0, recipe.getLabel(), webid, CalendarDayItem.Status.NOTDONE,
                date, time, sp.getLong("id", 0), "Recipe");
        intent.putExtra("Mode", "Insert");
        startActivityForResult(intent, ADD_TO_CALENDAR);
    }

    public void agregarADieta() {
        Intent intent = new Intent(getContext(), AddRecipeToDietaActivity.class);
        RecipePlantillaItem.packageIntent(intent, recipe.getLabel(),
                recipe.getCalories() / recipe.getYield(), webid, recipe.getImage(), RecipePlantillaItem.Period.LUNCH, 0);
        startActivityForResult(intent, ADD_TO_DIET);
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
        bHorario = v.findViewById (R.id.horarioButton);
        bDieta = v.findViewById(R.id.dietaButton);
        bFavorite = v.findViewById(R.id.favoriteButton);
    }

    public void cambiarFavorito(){
        Log.d("Estado", String.valueOf(favoriteState));
        if(!executing) {
            executing = true;
            if(favoriteState) {
                eliminarFavorito();
                favoriteState = false;
            } else {
                agregarFavorito();
                favoriteState = true;
            }
        }
    }

    private void eliminarFavorito(){
        new AsyncDelete().execute(webid);
        Toast toast = Toast.makeText(getContext(), "Recipe deleted from favorites", Toast.LENGTH_LONG);
        toast.show();
    }

    private void agregarFavorito(){
        long userid = sp.getLong("id", 0);
        FavoriteRecipeItem item = new FavoriteRecipeItem(recipe.getLabel(),
                recipe.getCalories()/recipe.getYield(),
                webid, recipe.getImage(),userid);
        new AsyncInsert().execute(item);
        Toast toast = Toast.makeText(getContext(), "Recipe added to favorites", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast toast;
        if (requestCode == ADD_TO_CALENDAR) {
            if (resultCode == RESULT_OK) {
                toast = Toast.makeText(getContext(), "Recipe added to diet", Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG);
            }
            toast.show();
        } else if (requestCode == ADD_TO_DIET) {
            if (resultCode == RESULT_OK) {
                toast = Toast.makeText(getContext(), "Recipe added to diet", Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG);
            }
            toast.show();
        }
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<FavoriteRecipeItem>> {

        @Override
        protected List<FavoriteRecipeItem> doInBackground(Void... voids){
            long userid = sp.getLong("id", 0);
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            List<FavoriteRecipeItem> items = nutrifitDb.favoriteRecipeItemDao().getAll(userid);

            return items;
        }

        @Override
        protected void onPostExecute(List<FavoriteRecipeItem> items){
            super.onPostExecute(items);
            Log.d("Lista de favoritos", items.toString());
            favoritesList = items;
            if(favoritesList != null) {
                for(FavoriteRecipeItem item : favoritesList) {
                    if(item.getWebid().equals(webid)) {
                        favoriteState = true;
                        break;
                    }
                }
            }
        }
    }

    class AsyncInsert extends AsyncTask<FavoriteRecipeItem, Void, FavoriteRecipeItem> {

        @Override
        protected FavoriteRecipeItem doInBackground(FavoriteRecipeItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            Long id = nutrifitDb.favoriteRecipeItemDao().insert(items[0]);

            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(FavoriteRecipeItem items){
            super.onPostExecute(items);
            executing = false;
        }
    }

    class AsyncDelete extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            int rows = nutrifitDb.favoriteRecipeItemDao().delete(items[0]);

            return rows;
        }

        @Override
        protected void onPostExecute(Integer rows){
            super.onPostExecute(rows);
            executing = false;
        }
    }
}