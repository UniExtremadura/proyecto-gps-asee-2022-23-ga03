package es.unex.giiis.asee.proyecto.repository.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public class RecipesNetworkDataSource {
    private static final String LOG_TAG = RecipesNetworkDataSource.class.getSimpleName();
    private static RecipesNetworkDataSource sInstance;

    // LiveData storing the latest downloaded recipes
    private final MutableLiveData<List<Recipe>> mDownloadedRecipes;

    // LiveData storing the latest downloaded recipe
    private final MutableLiveData<Recipe> mFetchedRecipe;

    private RecipesNetworkDataSource() {
        mDownloadedRecipes = new MutableLiveData<>();
        mFetchedRecipe = new MutableLiveData<>();
        fetchRecipes();
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

    public LiveData<Recipe> getFetchedRecipe() {
        return mFetchedRecipe;
    }


    /**
     * Gets the newest recipes
     */
    public void fetchRecipes() {
        Log.d(LOG_TAG, "Fetch recipes started");
        // Get gata from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new RecipesNetworkLoaderRunnable(mDownloadedRecipes::postValue));
    }

    /**
     * Gets the recipe that has the specified webid
     */
    public void fetchOneRecipe(String webid) {
        Log.d(LOG_TAG, "Fetch recipe started");
        // Get gata from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new SingleRecipeNetworkLoaderRunnable(webid, mFetchedRecipe::postValue));
    }
}
