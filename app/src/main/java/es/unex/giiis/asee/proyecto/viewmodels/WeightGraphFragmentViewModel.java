package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class WeightGraphFragmentViewModel extends ViewModel {

    private final NutrifitRepository mRepository;
    private LiveData<List<WeightRecordItem>> mAllRecords;

    public WeightGraphFragmentViewModel(NutrifitRepository repository) {
        mRepository = repository;
        mAllRecords = mRepository.getWeightRecords();
    }

    public LiveData<List<WeightRecordItem>> getAllRecords() {
        return mAllRecords;
    }
}
