package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.TypeConverter;

import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

public class DayConverter {

    @TypeConverter
    public static PlantillaItem.Day toDay(String day) {
        return PlantillaItem.Day.valueOf(day);
    }

    @TypeConverter
    public static String toString(PlantillaItem.Day day) {
        return day.name();
    }

}
