package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.TypeConverter;

import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

public class StatusConverter {
    @TypeConverter
    public static CalendarDayItem.Status toStatus(String status) {
        return  CalendarDayItem.Status.valueOf(status);
    }

    @TypeConverter
    public static String toString(CalendarDayItem.Status status){
        return status.name();
    }
}
