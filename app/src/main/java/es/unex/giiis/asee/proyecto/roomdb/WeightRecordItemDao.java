package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;

@Dao
public interface WeightRecordItemDao {

    @Query("SELECT * FROM records WHERE userid = :userid")
    public List<WeightRecordItem> getAllFromUser(long userid);

    @Insert
    public long insert(WeightRecordItem item);
}
