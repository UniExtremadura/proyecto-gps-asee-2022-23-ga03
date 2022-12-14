package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;

public class EditProfileActivityViewModel extends ViewModel {

    private final NutrifitRepository mRepository;
    private LiveData<List<UserItem>> mAllUsers;
    private LiveData<UserItem> mCurrentUser;

    public EditProfileActivityViewModel (NutrifitRepository repository) {
        mRepository = repository;
        mAllUsers = mRepository.getAllUsers();
        mCurrentUser = mRepository.getUser();
    }

    public LiveData<List<UserItem>> getAllUsers() {
        return mAllUsers;
    }

    public LiveData<UserItem> getCurrentUser() {
        return mCurrentUser;
    }

    public void updateUser(UserItem item) {
        mRepository.updateUser(item);
    }
}
