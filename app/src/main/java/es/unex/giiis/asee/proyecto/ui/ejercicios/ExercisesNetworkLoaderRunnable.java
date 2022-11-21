package es.unex.giiis.asee.proyecto.ui.ejercicios;


import java.io.IOException;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExercisesNetworkLoaderRunnable implements Runnable {

    private final OnExcersisesLoaderListener listener;

    public ExercisesNetworkLoaderRunnable(OnExcersisesLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.api-ninjas.com/").
                addConverterFactory(GsonConverterFactory.create()).build();
        ExcerciseService excerciseService = retrofit.create(ExcerciseService.class);
        try {
            List<Excercise> excercisesList = excerciseService.getAll("F4h5DfTKq50KiHaA7ZyiGg==Dm1nZzgKXnmysWOY")
                    .execute().body();
            AppExecutors.getInstance().mainThread().execute(() -> listener.onExcercisesLoader(excercisesList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
