package es.unex.giiis.asee.proyecto.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.UserItemDao;
import es.unex.giiis.asee.proyecto.roomdb.WeightRecordItemDao;

public class NutrifitRepository {
    private static final String LOG_TAG = NutrifitRepository.class.getSimpleName();

    // For Singleton instantiation
    private static NutrifitRepository sInstance;

    private final MutableLiveData<Long> userId;
    private UserItemDao mUserDao;
    private WeightRecordItemDao mWeightDao;

    public synchronized static NutrifitRepository getInstance(UserItemDao userDao, WeightRecordItemDao weightDao) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new NutrifitRepository(userDao, weightDao);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    private NutrifitRepository(UserItemDao userDao, WeightRecordItemDao weightDao) {
        mUserDao = userDao;
        mWeightDao = weightDao;
        userId = new MutableLiveData<>();
    }

    public LiveData<List<UserItem>> getAllUsers() {
        return mUserDao.getAllLv();
    }

    public LiveData<Long> getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId.setValue(userId);
    }

    public LiveData<UserItem> getUser(){
        return Transformations.switchMap(getUserId(), mUserDao::getUserLv);
    }

    public LiveData<List<WeightRecordItem>> getWeightRecords(){
        return Transformations.switchMap(getUserId(), mWeightDao::getAllFromUserLv);
    }

    public long insertUser(UserItem item) {
        return mUserDao.insert(item);
    }

    public void insertWeightRecord(WeightRecordItem item) {
        mWeightDao.insert(item);
    }
}
