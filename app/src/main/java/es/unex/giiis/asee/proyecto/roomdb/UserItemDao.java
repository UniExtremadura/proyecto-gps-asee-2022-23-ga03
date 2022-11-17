package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.proyecto.login_register.UserItem;

/**
 * Define la interfaz de acceso con la Base de Datos.
 */

@Dao
public interface UserItemDao {

    @Query("SELECT * FROM usuarios")
    public List<UserItem> getAll();

    @Query("SELECT * FROM usuarios WHERE id = :id")
    public UserItem getUser(long id);

    @Insert
    public long insert(UserItem item);

    @Update
    public int update(UserItem item);

    @Delete
    public int delete(UserItem item);
}
