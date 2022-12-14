package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

public class DetallesPlantillaFragmentViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private MutableLiveData<Long> currentPlantillaId;

    public DetallesPlantillaFragmentViewModel(NutrifitRepository repository) {
        mRepository = repository;
        currentPlantillaId = new MutableLiveData<>();
    }

    public LiveData<Long> getCurrentPlantillaId() {
        return currentPlantillaId;
    }

    public void setCurrentPlantillaId(long id) {
        currentPlantillaId.setValue(id);
    }

    public LiveData<List<RecipePlantillaItem>> getCurrentDietRecipes() {
        return Transformations.switchMap(getCurrentPlantillaId(), mRepository::getAllRecipesFromPlantilla);
    }

    public void delete(RecipePlantillaItem item) {
        mRepository.deleteRecipeDiet(item);
    }

    public void fetchOneRecipe(String webid) {
        mRepository.doFetchSingleRecipe(webid);
    }
}
