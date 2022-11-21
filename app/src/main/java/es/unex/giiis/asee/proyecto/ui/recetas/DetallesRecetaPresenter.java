package es.unex.giiis.asee.proyecto.ui.recetas;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.NetworkImageLoaderRunnable;
import es.unex.giiis.asee.proyecto.recipesmodel.Digest;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public class DetallesRecetaPresenter {
    private final TextView mRecipeName;
    private final TextView mCaloriasRacion;
    private final TextView mRaciones;
    private final TextView mMealType;
    private final TextView mCuisineType;
    private final TextView mDishType;
    private final TextView mTime;
    private final TextView mWeight;
    private final TextView mIngredientLines;
    private final TextView mDietLabels;
    private final TextView mHealthLabels;
    private final TextView mDietLines;
    private final ImageView mImageView;
    private final Recipe recipe;

    public DetallesRecetaPresenter(TextView mRecipeName, TextView mCaloriasRacion, TextView mRaciones, TextView mMealType,
                                   TextView mCuisineType, TextView mDishType, TextView mTime,  TextView mWeight,
                                   TextView mIngredientLines, TextView mDietLabels, TextView mHealthLabels,
                                   TextView mDietLines, ImageView mImageView, Recipe recipe) {

        this.mRecipeName=mRecipeName;
        this.mCaloriasRacion=mCaloriasRacion;
        this.mRaciones=mRaciones;
        this.mMealType=mMealType;
        this.mCuisineType=mCuisineType;
        this.mDishType=mDishType;
        this.mTime=mTime;
        this.mIngredientLines=mIngredientLines;
        this.mDietLabels=mDietLabels;
        this.mHealthLabels=mHealthLabels;
        this.mWeight=mWeight;
        this.mDietLines=mDietLines;
        this.mImageView=mImageView;
        this.recipe=recipe;
    }

    public void fillInformation(){
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
}
