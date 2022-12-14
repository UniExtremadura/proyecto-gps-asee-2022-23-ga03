package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;

public class DetallesRecipeFragmentViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<FavoriteRecipeItem>> userFavorites;

    public DetallesRecipeFragmentViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userFavorites = mRepository.getAllUserRecipeFavorites();
    }

    public LiveData<List<FavoriteRecipeItem>> getUserFavorites() {
        return userFavorites;
    }

    public void insert(FavoriteRecipeItem item) {
        mRepository.insertRecipeFavorite(item);
    }

    public void delete(String webid) {
        mRepository.deleteRecipeFavorite(webid);
    }
}
