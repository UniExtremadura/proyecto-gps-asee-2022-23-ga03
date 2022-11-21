package es.unex.giiis.asee.proyecto.roomdb;

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

    @Insert
    public long insert(FavoriteExcerciseItem item);

    @Delete
    public int delete(FavoriteExcerciseItem item);
}
