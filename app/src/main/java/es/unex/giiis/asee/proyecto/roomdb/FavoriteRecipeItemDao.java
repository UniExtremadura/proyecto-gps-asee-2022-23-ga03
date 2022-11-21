package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;

@Dao
public interface FavoriteRecipeItemDao {

    @Query("SELECT * FROM recipefavorites WHERE userid = :userid")
    public List<FavoriteRecipeItem> getAll(long userid);

    @Insert
    public long insert(FavoriteRecipeItem item);

    @Delete
    public int delete(FavoriteRecipeItem item);

    @Query("DELETE FROM recipefavorites WHERE webid = :webid")
    public int delete(String webid);
}
