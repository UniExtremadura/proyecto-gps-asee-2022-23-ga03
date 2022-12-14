package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class RegisterActivityViewModel extends ViewModel {

    private final NutrifitRepository mRepository;
    private LiveData<List<UserItem>> mAllUsers;

    public RegisterActivityViewModel(NutrifitRepository repository) {
        mRepository = repository;
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<UserItem>> getAllUsers() {
        return mAllUsers;
    }

    public long insertUser(UserItem item) {
        return mRepository.insertUser(item);
    }

    public void setSessionId(long userId) {
        mRepository.setUserId(userId);
    }

    public void insertWeightRecord(WeightRecordItem item) {
        mRepository.insertWeightRecord(item);
    }
}
