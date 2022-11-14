package es.unex.giiis.asee.proyecto.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gestion")
public class GestionModel {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public GestionModel(long id) {
        this.id = id;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }
}
