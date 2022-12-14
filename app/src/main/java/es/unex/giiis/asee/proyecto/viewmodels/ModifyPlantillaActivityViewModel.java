package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

public class ModifyPlantillaActivityViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    public ModifyPlantillaActivityViewModel(NutrifitRepository repository) {
        mRepository = repository;
    }

    public void update(PlantillaItem item) {
        mRepository.updateDiet(item);
    }

}

