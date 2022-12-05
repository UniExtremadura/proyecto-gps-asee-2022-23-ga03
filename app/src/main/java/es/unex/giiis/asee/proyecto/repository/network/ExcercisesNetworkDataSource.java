package es.unex.giiis.asee.proyecto.repository.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.ui.ejercicios.ExercisesNetworkLoaderRunnable;

public class ExcercisesNetworkDataSource {
    private static final String LOG_TAG = RecipesNetworkDataSource.class.getSimpleName();
    private static ExcercisesNetworkDataSource sInstance;

    // LiveData storing the latest downloaded excercises
    private final MutableLiveData<List<Excercise>> mDownloadedExcercises;

    private ExcercisesNetworkDataSource() {
        mDownloadedExcercises = new MutableLiveData<>();
        fetchExcercises();
    }

    public synchronized static ExcercisesNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the excercises network data source");
        if (sInstance == null) {
            sInstance = new ExcercisesNetworkDataSource();
            Log.d(LOG_TAG, "Made new excercises network data source");
        }
        return sInstance;
    }

    public LiveData<List<Excercise>> getCurrentExcercises() {
        return mDownloadedExcercises;
    }

    /**
     * Gets the newest excercises
     */
    public void fetchExcercises() {
        Log.d(LOG_TAG, "Fetch excercises started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new ExercisesNetworkLoaderRunnable(mDownloadedExcercises::postValue));
    }
}
