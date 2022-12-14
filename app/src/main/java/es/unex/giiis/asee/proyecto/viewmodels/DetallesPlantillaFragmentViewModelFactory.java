package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class DetallesPlantillaFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final NutrifitRepository mRepository;

    public DetallesPlantillaFragmentViewModelFactory(NutrifitRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetallesPlantillaFragmentViewModel(mRepository);
    }
}
