package es.unex.giiis.asee.proyecto.repository.network;

import java.io.IOException;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.recipesmodel.Hit;
import es.unex.giiis.asee.proyecto.repository.network.EdamanService;
import es.unex.giiis.asee.proyecto.repository.network.OnSingleRecipeLoaderListener;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleRecipeNetworkLoaderRunnable implements Runnable {

    private final OnSingleRecipeLoaderListener mRecipesLoaderListener;
    private final String id;

    public SingleRecipeNetworkLoaderRunnable(String id, OnSingleRecipeLoaderListener mRecipesLoaderListener) {
        this.id = id;
        this.mRecipesLoaderListener = mRecipesLoaderListener;
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
            Hit data = service.getRecipe(
                    id,
                    "public",
                    "a56f2412",
                    "9cf126ab9672d65c0b64eeba9c44b84b").execute().body();

            // Llamada al Listener con los datos obtenidos
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    AppExecutors.getInstance().mainThread().execute(() -> mRecipesLoaderListener.onRecipeLoader(data.getRecipe()));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

