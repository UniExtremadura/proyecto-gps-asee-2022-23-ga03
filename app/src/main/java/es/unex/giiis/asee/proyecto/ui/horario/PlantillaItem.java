package es.unex.giiis.asee.proyecto.ui.horario;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.roomdb.DayConverter;
import es.unex.giiis.asee.proyecto.roomdb.PriorityConverter;


@Entity(tableName = "plantillas", foreignKeys = {
        @ForeignKey(entity = UserItem.class,
                parentColumns = "id",
                childColumns = "userid",
                onDelete = ForeignKey.CASCADE)})
public class PlantillaItem implements Serializable {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    public enum Priority {
        LOW, MED, HIGH
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    @Ignore
    public final static String ID = "ID_ATTR";
    @Ignore
    public final static String TITLE = "title";
    @Ignore
    public final static String PRIORITY = "priority";
    @Ignore
    public final static String DAY = "day";
    @Ignore
    public final static String USERID = "userid";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    @TypeConverters(PriorityConverter.class)
    private Priority priority = Priority.LOW;
    @TypeConverters(DayConverter.class)
    private Day day = Day.MONDAY;
    private long userid;

    @Ignore
    public PlantillaItem(String title, Priority priority, Day day, long userid) {
        this.title = title;
        this.priority = priority;
        this.day = day;
        this.userid = userid;
    }

    public PlantillaItem(long id, String title, Priority priority, Day day, long userid) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.day = day;
        this.userid = userid;
    }

    @Ignore
    public PlantillaItem(long id, String title, String priority, String day, long userid) {
        this.id = id;
        this.title = title;
        this.priority = Priority.valueOf(priority);
        this.day = Day.valueOf(day);
        this.userid = userid;
    }

    @Ignore
    public PlantillaItem(Intent intent) {
        this.id = intent.getLongExtra(PlantillaItem.ID, 0);
        this.title = intent.getStringExtra(PlantillaItem.TITLE);
        this.priority = Priority.valueOf(intent.getStringExtra(PlantillaItem.PRIORITY));
        this.day = Day.valueOf(intent.getStringExtra(PlantillaItem.DAY));
        this.userid = intent.getLongExtra(PlantillaItem.USERID, 0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Day getDay() {
        return day;
    }

    public void setStatus(Day day) {
        this.day = day;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }


    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, long id, String title,
                                     Priority priority, Day day, long userid) {

        intent.putExtra(PlantillaItem.ID, id);
        intent.putExtra(PlantillaItem.TITLE, title);
        intent.putExtra(PlantillaItem.PRIORITY, priority.toString());
        intent.putExtra(PlantillaItem.DAY, day.toString());
        intent.putExtra(PlantillaItem.USERID, userid);
    }

    public String toString() {
        return id + ITEM_SEP + userid + ITEM_SEP + title + ITEM_SEP + priority + ITEM_SEP + day;
    }

    public String toLog() {
        return "ID_ATTR: " + id + ITEM_SEP + "Userid:" + userid + ITEM_SEP + "Title:" + title + ITEM_SEP + "Priority:" + priority
                + ITEM_SEP + "Day:" + day;
    }
}


