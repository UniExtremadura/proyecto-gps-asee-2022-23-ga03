package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.ejercicios.FavoriteExcerciseItem;

public class FavoriteExcerciseViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<FavoriteExcerciseItem>> userFavorites;

    public FavoriteExcerciseViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userFavorites = mRepository.getAllUserExcerciseFavorites();
    }

    public LiveData<List<FavoriteExcerciseItem>> getUserFavorites() {
        return userFavorites;
    }

    public void insert(FavoriteExcerciseItem item) {
        mRepository.insertExcerciseFavorite(item);
    }

    public void delete(String name) {
        mRepository.deleteExcerciseFavorite(name);
    }

}
