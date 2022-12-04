package es.unex.giiis.asee.proyecto.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

@Dao
public interface PlantillaItemDao {

    @Query("SELECT * FROM plantillas WHERE userid = :userid")
    public List<PlantillaItem> getAll(long userid);

    @Query("SELECT * FROM plantillas WHERE userid = :userid")
    public LiveData<List<PlantillaItem>> getAllFromUserLv(long userid);

    @Insert
    public long insert(PlantillaItem item);

    @Update
    public int update(PlantillaItem item);

    @Delete
    public int delete(PlantillaItem item);

}