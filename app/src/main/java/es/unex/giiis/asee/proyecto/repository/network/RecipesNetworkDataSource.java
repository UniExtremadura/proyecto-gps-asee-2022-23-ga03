package es.unex.giiis.asee.proyecto.repository.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.RecipesNetworkLoaderRunnable;

public class RecipesNetworkDataSource {
    private static final String LOG_TAG = RecipesNetworkDataSource.class.getSimpleName();
    private static RecipesNetworkDataSource sInstance;

    // LiveData storing the latest downloaded recipes
    private final MutableLiveData<List<Recipe>> mDownloadedRecipes;

    private RecipesNetworkDataSource() {
        mDownloadedRecipes = new MutableLiveData<>();
    }

    public synchronized static RecipesNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the recipes network data source");
        if (sInstance == null) {
            sInstance = new RecipesNetworkDataSource();
            Log.d(LOG_TAG, "Made new network recipes data source");
        }
        return sInstance;
    }

    public LiveData<List<Recipe>> getCurrentRecipes() {
        return mDownloadedRecipes;
    }

    /**
     * Gets the newest recipes
     */
    public void fetchRecipes() {
        Log.d(LOG_TAG, "Fetch recipes started");
        // Get gata from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new RecipesNetworkLoaderRunnable(mDownloadedRecipes::postValue));
    }
}
