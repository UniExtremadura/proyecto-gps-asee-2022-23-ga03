package es.unex.giiis.asee.proyecto.repository.network;

import es.unex.giiis.asee.proyecto.recipesmodel.Hit;
import es.unex.giiis.asee.proyecto.recipesmodel.RecipesApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EdamanService {

    @GET("recipes/v2")
    Call<RecipesApiResponse> listRecipes(@Query("type") String type,
                                         @Query("q") String query,
                                         @Query("app_id") String app_id,
                                         @Query("app_key") String app_key,
                                         @Query("random") String random);


    @GET("recipes/v2/{id}")
    Call<Hit> getRecipe(@Path("id") String id,
                        @Query("type") String type,
                        @Query("app_id") String app_id,
                        @Query("app_key") String app_key);
}
