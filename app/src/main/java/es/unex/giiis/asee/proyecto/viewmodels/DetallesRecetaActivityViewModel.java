package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class DetallesRecetaActivityViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    public DetallesRecetaActivityViewModel(NutrifitRepository repository) {
        mRepository = repository;
    }

    public LiveData<Recipe> getFetchedRecipe() {
        return mRepository.getCurrentFetchedRecipe();
    }
}