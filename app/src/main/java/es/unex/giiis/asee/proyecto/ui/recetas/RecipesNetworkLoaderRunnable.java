package es.unex.giiis.asee.proyecto.ui.recetas;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.recipesmodel.Hit;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.recipesmodel.RecipesApiResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesNetworkLoaderRunnable implements Runnable {

    private final OnRecipesLoaderListener mOnRecipesLoaderListener;

    public RecipesNetworkLoaderRunnable(OnRecipesLoaderListener onRecipesLoaderListener) {
        this.mOnRecipesLoaderListener = onRecipesLoaderListener;
    }

    @Override
    public void run() {
        // Instanciación de Retrofit y llamada síncrona
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EdamanService service = retrofit.create(EdamanService.class);
        try {
            RecipesApiResponse data = service.listRecipes("public", "all",
                    "a56f2412",
                    "9cf126ab9672d65c0b64eeba9c44b84b",
                    "true").execute().body();

            List<Recipe> recipes = new ArrayList<>();
            for (Hit hit : data.getHits()) {
                recipes.add(hit.getRecipe());
                Log.d("Recipe", hit.getRecipe().getLabel());
            }

            // Llamada al Listener con los datos obtenidos
            AppExecutors.getInstance().mainThread().execute(() -> mOnRecipesLoaderListener.onRecipesLoader(recipes));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
