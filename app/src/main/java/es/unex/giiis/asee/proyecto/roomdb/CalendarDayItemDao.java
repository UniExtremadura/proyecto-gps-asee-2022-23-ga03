package es.unex.giiis.asee.proyecto.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

@Dao
public interface CalendarDayItemDao {

    @Query("SELECT * FROM events WHERE userid = :userid")
    public List<CalendarDayItem> getAllFromUser(long userid);

    @Query("SELECT * FROM events WHERE date = :date")
    public List<CalendarDayItem> getAllFromDate(String date);

    @Query("SELECT * FROM events WHERE userid = :userid")
    public LiveData<List<CalendarDayItem>> getAllFromUserLv(long userid);

    @Query("SELECT * FROM events WHERE userid = :userid AND date = :date")
    public LiveData<List<CalendarDayItem>> getAllFromDateLv(long userid, String date);

    @Insert
    public long insert(CalendarDayItem item);

    @Update
    public int update(CalendarDayItem item);

    @Delete
    public int delete(CalendarDayItem item);
}
