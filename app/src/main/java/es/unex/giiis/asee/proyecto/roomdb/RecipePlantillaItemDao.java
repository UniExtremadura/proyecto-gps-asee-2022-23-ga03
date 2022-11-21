package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

@Dao
public interface RecipePlantillaItemDao {
    @Query("SELECT * FROM recipesdiet WHERE plantillaid = :plantillaid")
    public List<RecipePlantillaItem> getAllFromPlantilla(long plantillaid);

    @Insert
    public long insert(RecipePlantillaItem item);

    @Update
    public int update(RecipePlantillaItem item);

    @Delete
    public int delete(RecipePlantillaItem item);
}
