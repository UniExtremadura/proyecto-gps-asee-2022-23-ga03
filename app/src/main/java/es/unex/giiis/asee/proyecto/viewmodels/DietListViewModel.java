package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

public class DietListViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<PlantillaItem>> userDiets;

    public DietListViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userDiets = mRepository.getUserDiets();
    }

    public LiveData<List<PlantillaItem>> getUserDiets() {
        return userDiets;
    }

    public void insert(PlantillaItem item) {
        mRepository.insertDiet(item);
    }

    public void delete(PlantillaItem item) {
        mRepository.deleteDiet(item);
    }
}
