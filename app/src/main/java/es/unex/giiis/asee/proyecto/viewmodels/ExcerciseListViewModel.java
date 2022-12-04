package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class ExcerciseListViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<Excercise>> mDownloadedExcercises;

    public ExcerciseListViewModel(NutrifitRepository repository) {
        mRepository = repository;
        fetchExcercises();
        mDownloadedExcercises = mRepository.getCurrentDownloadedExcercises();
    }

    public LiveData<List<Excercise>> getDownloadedExcercises() {
        return mDownloadedExcercises;
    }

    public void fetchExcercises() {
        mRepository.doFetchExcercises();
    }
}
