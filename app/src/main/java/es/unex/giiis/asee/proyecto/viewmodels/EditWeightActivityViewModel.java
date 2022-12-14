package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class EditWeightActivityViewModel extends ViewModel {

    private final NutrifitRepository mRepository;
    private LiveData<UserItem> mCurrentUser;

    public EditWeightActivityViewModel (NutrifitRepository repository) {
        mRepository = repository;
        mCurrentUser = mRepository.getUser();
    }

    public LiveData<UserItem> getCurrentUser() {
        return mCurrentUser;
    }

    public void updateUser(UserItem item) {
        mRepository.updateUser(item);
    }

    public void insertRecord(WeightRecordItem item) {
        mRepository.insertWeightRecord(item);
    }
}
