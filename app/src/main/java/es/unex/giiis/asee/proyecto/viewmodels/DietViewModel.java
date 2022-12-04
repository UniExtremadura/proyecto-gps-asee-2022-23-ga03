package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

public class DietViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<PlantillaItem>> userDiets;

    public DietViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userDiets = mRepository.getUserDiets();
    }

    public LiveData<List<PlantillaItem>> getUserDiets() {
        return userDiets;
    }

    public void insert(PlantillaItem item) {
        mRepository.insertDiet(item);
    }

    public void update(PlantillaItem item) {
        mRepository.updateDiet(item);
    }

    public void delete(PlantillaItem item) {
        mRepository.deleteDiet(item);
    }
}
