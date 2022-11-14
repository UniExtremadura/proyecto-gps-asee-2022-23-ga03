package es.unex.giiis.asee.proyecto.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GestionModel.class}, version = 2)
public abstract class NutrifitDatabase extends RoomDatabase {
    private static NutrifitDatabase instance;

    public static NutrifitDatabase getDatabase(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), NutrifitDatabase.class, "nutrifit.db")
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }
}
