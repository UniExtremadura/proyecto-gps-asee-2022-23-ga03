package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import es.unex.giiis.asee.proyecto.login_register.UserItem;

@Entity(tableName = "excercisefavorites" , foreignKeys = {
        @ForeignKey(entity = UserItem.class,
                parentColumns = "id",
                childColumns = "userid",
                onDelete = ForeignKey.CASCADE)})
public class FavoriteExcerciseItem implements Serializable {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @Ignore
    public final static String ID = "ID_ATTR";
    @Ignore
    public final static String TITTLE = "title";
    @Ignore
    public final static String TYPE = "type";
    @Ignore
    public final static String MUSCLE_ATTR = "muscle";
    @Ignore
    public final static String EQUIPMENT_ATTR = "equipment";
    @Ignore
    public final static String DIFFICULTY_ATTR = "difficulty";
    @Ignore
    public final static String INSTRUCTIONS_ATTR = "instructions";
    @Ignore
    public final static String USERID_ATTR = "userid";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String tittle;
    private String type;
    private String muscle;
    private String equipment;
    private String difficulty;
    private String instructions;
    private long userid;

    @Ignore
    public FavoriteExcerciseItem(String tittle, String type, String muscle, String equipment, String difficulty, String instructions, long userid) {
        this.tittle = tittle;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.userid = userid;
    }

    public FavoriteExcerciseItem(long id, String tittle, String type, String muscle, String equipment, String difficulty, String instructions, long userid) {
        this.id = id;
        this.tittle = tittle;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.userid = userid;
    }

    @Ignore
    public FavoriteExcerciseItem(Intent intent) {
        this.id = intent.getLongExtra(FavoriteExcerciseItem.ID,0);
        this.tittle = intent.getStringExtra(FavoriteExcerciseItem.TITTLE);
        this.type = intent.getStringExtra(FavoriteExcerciseItem.TYPE);
        this.muscle = intent.getStringExtra(FavoriteExcerciseItem.MUSCLE_ATTR);
        this.equipment = intent.getStringExtra(FavoriteExcerciseItem.EQUIPMENT_ATTR);
        this.difficulty = intent.getStringExtra(FavoriteExcerciseItem.DIFFICULTY_ATTR);
        this.instructions = intent.getStringExtra(FavoriteExcerciseItem.INSTRUCTIONS_ATTR);
        this.userid = intent.getLongExtra(FavoriteExcerciseItem.USERID_ATTR,0);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String name) {
        this.tittle = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public long getUserid() { return userid; }

    public void setUserid(long userid) { this.userid = userid; }

}
