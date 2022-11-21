package es.unex.giiis.asee.proyecto.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.ui.ejercicios.FavoriteExcerciseItem;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;
import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;
import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;

/**
 * Define la Base de Datos de la aplicación
 */

@Database(entities = {UserItem.class, WeightRecordItem.class, PlantillaItem.class, CalendarDayItem.class, FavoriteRecipeItem.class, FavoriteExcerciseItem.class}, version = 2)
public abstract class NutrifitDatabase extends RoomDatabase {
    private static NutrifitDatabase instance;

    public static NutrifitDatabase getDatabase(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), NutrifitDatabase.class, "nutrifit.db")
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract UserItemDao userItemDao();

    public abstract WeightRecordItemDao weightRecordItemDao();

    public abstract PlantillaItemDao plantillaItemDao();

    public abstract CalendarDayItemDao calendarDayItemDao ();

    public abstract FavoriteRecipeItemDao favoriteRecipeItemDao ();

    public abstract FavoriteExcerciseItemDao favoriteExcerciseItemDao ();
}
