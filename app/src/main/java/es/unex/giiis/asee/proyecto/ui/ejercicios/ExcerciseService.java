package es.unex.giiis.asee.proyecto.ui.ejercicios;

import java.util.List;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ExcerciseService {

    @GET("v1/exercises")
    public Call <List<Excercise>> getAll(@Header("X-Api-Key") String key);
}
