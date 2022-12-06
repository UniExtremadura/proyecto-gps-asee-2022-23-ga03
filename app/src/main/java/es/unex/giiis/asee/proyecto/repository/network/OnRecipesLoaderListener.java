package es.unex.giiis.asee.proyecto.repository.network;

import java.util.List;

import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public interface OnRecipesLoaderListener {

    void onRecipesLoader(List<Recipe> data);
}
