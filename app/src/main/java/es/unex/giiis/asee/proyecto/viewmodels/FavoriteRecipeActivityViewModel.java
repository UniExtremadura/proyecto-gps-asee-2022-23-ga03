package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;

public class FavoriteRecipeActivityViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<FavoriteRecipeItem>> userFavorites;

    public FavoriteRecipeActivityViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userFavorites = mRepository.getAllUserRecipeFavorites();
    }

    public LiveData<List<FavoriteRecipeItem>> getUserFavorites() {
        return userFavorites;
    }

    public void deleteFavorite(String webid) {
        mRepository.deleteRecipeFavorite(webid);
    }

    public void fetchOneRecipe(String webid) {
        mRepository.doFetchSingleRecipe(webid);
    }
}
