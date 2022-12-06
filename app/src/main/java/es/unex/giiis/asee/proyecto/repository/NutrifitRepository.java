package es.unex.giiis.asee.proyecto.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.repository.network.ExcercisesNetworkDataSource;
import es.unex.giiis.asee.proyecto.repository.network.RecipesNetworkDataSource;
import es.unex.giiis.asee.proyecto.roomdb.CalendarDayItemDao;
import es.unex.giiis.asee.proyecto.roomdb.FavoriteExcerciseItemDao;
import es.unex.giiis.asee.proyecto.roomdb.FavoriteRecipeItemDao;
import es.unex.giiis.asee.proyecto.roomdb.PlantillaItemDao;
import es.unex.giiis.asee.proyecto.roomdb.RecipePlantillaItemDao;
import es.unex.giiis.asee.proyecto.roomdb.UserItemDao;
import es.unex.giiis.asee.proyecto.roomdb.WeightRecordItemDao;
import es.unex.giiis.asee.proyecto.ui.ejercicios.FavoriteExcerciseItem;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;
import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;

public class NutrifitRepository {
    private static final String LOG_TAG = NutrifitRepository.class.getSimpleName();

    // For Singleton instantiation
    private static NutrifitRepository sInstance;

    private final MutableLiveData<Long> userId;
    private UserItemDao mUserDao;
    private WeightRecordItemDao mWeightDao;
    private PlantillaItemDao mDietDao;
    private RecipePlantillaItemDao mRecipeDietDao;
    private CalendarDayItemDao mCalendarDayItemDao;
    private FavoriteExcerciseItemDao mFavoriteExcerciseDao;
    private FavoriteRecipeItemDao mFavoriteRecipeDao;

    private final RecipesNetworkDataSource mRecipesNetworkDataSource;
    private final ExcercisesNetworkDataSource mExcercisesNetworkDataSource;

    public synchronized static NutrifitRepository getInstance(UserItemDao userDao, WeightRecordItemDao weightDao,
                                                              PlantillaItemDao dietDao, RecipePlantillaItemDao recipeDietDao,
                                                              CalendarDayItemDao calendarDayItemDao, FavoriteExcerciseItemDao favoriteExcerciseDao,
                                                              FavoriteRecipeItemDao favoriteRecipeDao,
                                                              RecipesNetworkDataSource recipesNetworkDataSource,
                                                              ExcercisesNetworkDataSource excercisesNetworkDataSource) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new NutrifitRepository(userDao, weightDao, dietDao, recipeDietDao, calendarDayItemDao, favoriteExcerciseDao, favoriteRecipeDao, recipesNetworkDataSource, excercisesNetworkDataSource);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    private NutrifitRepository(UserItemDao userDao, WeightRecordItemDao weightDao, PlantillaItemDao dietDao,
                               RecipePlantillaItemDao recipeDietDao, CalendarDayItemDao calendarDayItemDao,
                               FavoriteExcerciseItemDao favoriteExcerciseDao,
                               FavoriteRecipeItemDao favoriteRecipeDao,
                               RecipesNetworkDataSource recipesNetworkDataSource,
                               ExcercisesNetworkDataSource excercisesNetworkDataSource) {
        mUserDao = userDao;
        mWeightDao = weightDao;
        mDietDao = dietDao;
        mRecipeDietDao = recipeDietDao;
        mCalendarDayItemDao = calendarDayItemDao;
        mFavoriteExcerciseDao = favoriteExcerciseDao;
        mFavoriteRecipeDao = favoriteRecipeDao;
        mRecipesNetworkDataSource = recipesNetworkDataSource;
        mExcercisesNetworkDataSource = excercisesNetworkDataSource;
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

    public LiveData<List<PlantillaItem>> getUserDiets(){
        return Transformations.switchMap(getUserId(), mDietDao::getAllFromUserLv);
    }

    public LiveData<List<Recipe>> getCurrentDownloadedRecipes() {
        return mRecipesNetworkDataSource.getCurrentRecipes();
    }

    public LiveData<List<Excercise>> getCurrentDownloadedExcercises() {
        return mExcercisesNetworkDataSource.getCurrentExcercises();
    }

    public LiveData<List<RecipePlantillaItem>> getAllRecipesFromPlantilla(long id) {
        return mRecipeDietDao.getAllFromPlantillaLv(id);
    }

    public LiveData<List<CalendarDayItem>> getAllUserEvents() {
        return Transformations.switchMap(getUserId(), mCalendarDayItemDao::getAllFromUserLv);
    }

    public LiveData<List<CalendarDayItem>> getAllEventsFromDate(String date) {
        return Transformations.switchMap(getUserId(), input -> mCalendarDayItemDao.getAllFromDateLv(input, date));
    }

    public LiveData<List<FavoriteExcerciseItem>> getAllUserExcerciseFavorites() {
        return Transformations.switchMap(getUserId(), mFavoriteExcerciseDao::getAllLv);
    }

    public LiveData<List<FavoriteRecipeItem>> getAllUserRecipeFavorites() {
        return Transformations.switchMap(getUserId(), mFavoriteRecipeDao::getAllLv);
    }

    public void doFetchRecipes(){
        Log.d(LOG_TAG, "Fetching Recipes from Edamam API");
        mRecipesNetworkDataSource.fetchRecipes();
    }

    public void doFetchExcercises(){
        Log.d(LOG_TAG, "Fetching Excercises from Ninja API");
        mExcercisesNetworkDataSource.fetchExcercises();
    }

    public void doFetchSingleRecipe(String webid){
        Log.d(LOG_TAG, "Fetching Recipe from Edamam API");
        mRecipesNetworkDataSource.fetchOneRecipe(webid);
    }

    public LiveData<Recipe> getCurrentFetchedRecipe() {
        return mRecipesNetworkDataSource.getFetchedRecipe();
    }

    public void insertWeightRecord(WeightRecordItem item) {
        mWeightDao.insert(item);
    }

    public long insertUser(UserItem item) {
        return mUserDao.insert(item);
    }

    public void updateUser(UserItem item) {
        AppExecutors.getInstance().diskIO().execute(() ->mUserDao.update(item));
    }

    public void insertDiet(PlantillaItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mDietDao.insert(item));
    }

    public void updateDiet(PlantillaItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mDietDao.update(item));
    }

    public void deleteDiet(PlantillaItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mDietDao.delete(item));
    }

    public void insertRecipeDiet(RecipePlantillaItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mRecipeDietDao.insert(item));
    }

    public void deleteRecipeDiet(RecipePlantillaItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mRecipeDietDao.delete(item));
    }

    public void insertEvent(CalendarDayItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mCalendarDayItemDao.insert(item));
    }

    public void updateEvent(CalendarDayItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mCalendarDayItemDao.update(item));
    }

    public void deleteEvent(CalendarDayItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mCalendarDayItemDao.delete(item));
    }

    public void insertExcerciseFavorite(FavoriteExcerciseItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mFavoriteExcerciseDao.insert(item));
    }


    public void deleteExcerciseFavorite(String name) {
        AppExecutors.getInstance().diskIO().execute(() -> mFavoriteExcerciseDao.delete(name));
    }

    public void insertRecipeFavorite(FavoriteRecipeItem item) {
        AppExecutors.getInstance().diskIO().execute(() -> mFavoriteRecipeDao.insert(item));
    }

    public void deleteRecipeFavorite(String webid) {
        AppExecutors.getInstance().diskIO().execute(() -> mFavoriteRecipeDao.delete(webid));
    }
}
