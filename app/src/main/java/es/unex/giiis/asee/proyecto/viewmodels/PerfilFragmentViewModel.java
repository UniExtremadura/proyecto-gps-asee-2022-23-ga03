package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class PerfilFragmentViewModel extends ViewModel {

    private final NutrifitRepository mRepository;
    private LiveData<UserItem> mCurrentUser;

    public PerfilFragmentViewModel (NutrifitRepository repository) {
        mRepository = repository;
        mCurrentUser = mRepository.getUser();
    }

    public LiveData<UserItem> getCurrentUser() {
        return mCurrentUser;
    }
}
