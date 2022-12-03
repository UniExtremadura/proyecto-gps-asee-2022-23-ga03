package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class UserViewModel extends ViewModel {

    private final NutrifitRepository mRepository;
    private LiveData<List<UserItem>> mAllUsers;
    private LiveData<UserItem> mCurrentUser;

    public UserViewModel (NutrifitRepository repository) {
        mRepository = repository;
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<UserItem>> getAllUsers() {
        return mAllUsers;
    }

    public LiveData<UserItem> getCurrentUser() {
        return mCurrentUser;
    }

    public void setSessionId(long userId) {
        mRepository.setUserId(userId);
        mCurrentUser = mRepository.getUser();
    }

    public long insert(UserItem item) {
        return mRepository.insertUser(item);
    }
}
