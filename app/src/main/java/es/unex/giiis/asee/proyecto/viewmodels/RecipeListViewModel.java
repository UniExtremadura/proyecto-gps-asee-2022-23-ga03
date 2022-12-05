package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class RecipeListViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<Recipe>> mDownloadedRecipes;

    public RecipeListViewModel(NutrifitRepository repository) {
        mRepository = repository;
        mDownloadedRecipes = mRepository.getCurrentDownloadedRecipes();
    }

    public LiveData<List<Recipe>> getDownloadedRecipes() {
        return mDownloadedRecipes;
    }

    public void fetchRecipes() {
        mRepository.doFetchRecipes();
    }
}
