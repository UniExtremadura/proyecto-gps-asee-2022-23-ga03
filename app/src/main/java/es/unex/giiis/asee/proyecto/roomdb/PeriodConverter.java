package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.TypeConverter;

import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

public class PeriodConverter {
    @TypeConverter
    public static RecipePlantillaItem.Period toPriority(String period) {
        return RecipePlantillaItem.Period.valueOf(period);
    }

    @TypeConverter
    public static String toString(RecipePlantillaItem.Period period) {
        return period.name();
    }
}
