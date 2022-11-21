package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.TypeConverter;

import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

public class PriorityConverter {

    @TypeConverter
    public static PlantillaItem.Priority toPriority(String priority) {
        return PlantillaItem.Priority.valueOf(priority);
    }

    @TypeConverter
    public static String toString(PlantillaItem.Priority priority) {
        return priority.name();
    }
}