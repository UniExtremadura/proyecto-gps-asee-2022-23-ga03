package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;
import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

public class AddRecipeToDietaActivityViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<PlantillaItem>> userDiets;

    public AddRecipeToDietaActivityViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userDiets = repository.getUserDiets();
    }

    public LiveData<List<PlantillaItem>> getUserDiets() {
        return userDiets;
    }

    public void insert(RecipePlantillaItem item) {
        mRepository.insertRecipeDiet(item);
    }
}
