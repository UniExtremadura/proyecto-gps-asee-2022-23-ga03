package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

public class DietRecipesViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private MutableLiveData<Long> currentPlantillaId;

    public DietRecipesViewModel(NutrifitRepository repository) {
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
        return Transformations.switchMap(currentPlantillaId, mRepository::getAllRecipesFromPlantilla);
    }

    public void insert(RecipePlantillaItem item) {
        mRepository.insertRecipeDiet(item);
    }

    public void delete(RecipePlantillaItem item) {
        mRepository.deleteRecipeDiet(item);
    }
}
