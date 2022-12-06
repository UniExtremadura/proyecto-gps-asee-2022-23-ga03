package es.unex.giiis.asee.proyecto.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.giiis.asee.proyecto.ui.ejercicios.FavoriteExcerciseItem;

@Dao
public interface FavoriteExcerciseItemDao {

    @Query("SELECT * FROM excercisefavorites WHERE userid = :userid")
    public List<FavoriteExcerciseItem> getAll(long userid);

    @Query("SELECT * FROM excercisefavorites WHERE userid = :userid")
    public LiveData<List<FavoriteExcerciseItem>> getAllLv(long userid);

    @Insert
    public long insert(FavoriteExcerciseItem item);

    @Query("DELETE FROM excercisefavorites WHERE tittle = :name")
    public int delete(String name);

    @Delete
    public int delete(FavoriteExcerciseItem item);
}
