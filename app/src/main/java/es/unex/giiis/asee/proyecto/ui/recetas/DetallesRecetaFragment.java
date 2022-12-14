package es.unex.giiis.asee.proyecto.ui.recetas;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.NetworkImageLoaderRunnable;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Digest;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.horario.AddEventToHorarioActivity;
import es.unex.giiis.asee.proyecto.ui.horario.AddRecipeToDietaActivity;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;
import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;
import es.unex.giiis.asee.proyecto.viewmodels.DetallesRecipeFragmentViewModel;

public class DetallesRecetaFragment extends Fragment {

    private SharedPreferences sp;
    private TextView mRecipeName,mCaloriasRacion, mRaciones, mMealType, mCuisineType, mDishType, mTime, mWeight,
            mIngredientLines, mDietLabels, mHealthLabels, mDietLines;
    private ImageView mImageView;
    private Button bEnlace;
    private Toolbar mToolbar;
    private ImageButton bHorario, bDieta, bFavorite;
    private String webid;
    private boolean favoriteState = false;
    private static final int ADD_TO_CALENDAR = 1;
    private static final int ADD_TO_DIET = 0;
    private Recipe recipe;

    private DetallesRecipeFragmentViewModel mDetallesRecipeFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_receta, container, false);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mDetallesRecipeFragmentViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.detallesRecipeFragmentFactory).get(DetallesRecipeFragmentViewModel.class);

        bindViews(v);

        Bundle args = getArguments();
        recipe = (Recipe) args.getSerializable("Details");

        String url = recipe.getUri();
        webid = url.substring(url.lastIndexOf("_") + 1);

        fillInformation();

        mToolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipe details");
        setHasOptionsMenu(true);

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        mDetallesRecipeFragmentViewModel.getUserFavorites().observe(getViewLifecycleOwner(), new Observer<List<FavoriteRecipeItem>>() {
            @Override
            public void onChanged(List<FavoriteRecipeItem> favoriteRecipeItems) {
                if(favoriteRecipeItems != null) {
                    for(FavoriteRecipeItem item : favoriteRecipeItems) {
                        if(item.getWebid().equals(webid)) {
                            favoriteState = true;
                            break;
                        }
                    }
                }
            }
        });

        bEnlace.setOnClickListener(view -> launchWebIntent());
        bHorario.setOnClickListener(v1 -> agregarAHorario());
        bDieta.setOnClickListener(v12 -> agregarADieta());
        bFavorite.setOnClickListener(v13 -> cambiarFavorito());

        return v;
    }

    private void fillInformation() {
        AppExecutors.getInstance().networkIO().execute(new NetworkImageLoaderRunnable(recipe.getImage(), mImageView::setImageDrawable));
        DecimalFormat df = new DecimalFormat("0.00");
        mRecipeName.setText(recipe.getLabel());
        mCaloriasRacion.setText(String.format("Calories/ration: %s %s",df.format(recipe.getCalories()/recipe.getYield()), " cal"));
        mRaciones.setText(String.format("Rations: %s",recipe.getYield()));

        if(recipe.getMealType() == null) {
            mMealType.setVisibility(View.GONE);
        } else {
            mMealType.setVisibility(View.VISIBLE);
            mMealType.setText(String.format("Meal Type: %s", String.join(", ", recipe.getMealType())));
        }

        if(recipe.getCuisineType() == null) {
            mCuisineType.setVisibility(View.GONE);
        } else {
            mCuisineType.setVisibility(View.VISIBLE);
            mCuisineType.setText(String.format("Cuisine Type: %s", String.join(", ", recipe.getCuisineType())));
        }

        if(recipe.getDishType() == null) {
            mDishType.setVisibility(View.GONE);
        } else {
            mDishType.setVisibility(View.VISIBLE);
            mDishType.setText(String.format("Dish Type: %s", String.join(", ", recipe.getDishType())));
        }

        if(recipe.getTotalTime() == null) {
            mTime.setVisibility(View.GONE);
        } else {
            mTime.setVisibility(View.VISIBLE);
            mTime.setText(String.format("Total Time: %s", recipe.getTotalTime()));
        }

        if(recipe.getTotalWeight() == null) {
            mWeight.setVisibility(View.GONE);
        } else {
            mWeight.setVisibility(View.VISIBLE);
            mWeight.setText(String.format("Weight: %s %s", df.format(recipe.getTotalWeight()), "g"));
        }

        if(recipe.getIngredientLines() == null) {
            mIngredientLines.setVisibility(View.GONE);
        } else {
            mIngredientLines.setVisibility(View.VISIBLE);
            mIngredientLines.setText(String.join("\n", recipe.getIngredientLines()));
        }

        if(recipe.getDietLabels() == null) {
            mDietLabels.setVisibility(View.GONE);
        } else {
            mDietLabels.setVisibility(View.VISIBLE);
            mDietLabels.setText(String.join(", ", recipe.getDietLabels()));
        }

        if(recipe.getHealthLabels() == null) {
            mHealthLabels.setVisibility(View.GONE);
        } else {
            mHealthLabels.setVisibility(View.VISIBLE);
            mHealthLabels.setText(String.join(", ", recipe.getHealthLabels()));
        }

        if(recipe.getDigest() == null) {
            mDietLines.setVisibility(View.GONE);
        } else {
            mDietLines.setVisibility(View.VISIBLE);
            StringBuilder digestString = new StringBuilder();

            for (Digest digest : recipe.getDigest()) {
                digestString.append(digest.getLabel());
                digestString.append(" - ");
                digestString.append(df.format(digest.getTotal()));
                digestString.append(" ");
                digestString.append(digest.getUnit());
                digestString.append("\n");
            }

            mDietLines.setText(digestString);
        }
    }

    public void launchWebIntent() {
        Uri webpage = Uri.parse(recipe.getUrl());
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
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
            if(favoriteState) {
                eliminarFavorito();
                favoriteState = false;
            } else {
                agregarFavorito();
                favoriteState = true;
            }
    }

    private void eliminarFavorito(){
        mDetallesRecipeFragmentViewModel.delete(webid);
        Toast toast = Toast.makeText(getContext(), "Recipe deleted from favorites", Toast.LENGTH_LONG);
        toast.show();
    }

    private void agregarFavorito(){
        long userid = sp.getLong("id", 0);
        FavoriteRecipeItem item = new FavoriteRecipeItem(recipe.getLabel(),
                recipe.getCalories()/recipe.getYield(),
                webid, recipe.getImage(),userid);
        mDetallesRecipeFragmentViewModel.insert(item);
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
}