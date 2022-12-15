package es.unex.giiis.asee.proyecto.login_register;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.unex.giiis.asee.proyecto.roomdb.DateConverter;

@Entity(tableName = "records", foreignKeys = {
        @ForeignKey(entity = UserItem.class,
                parentColumns = "id",
                childColumns = "userid",
                onDelete = ForeignKey.CASCADE)})
public class WeightRecordItem {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");
    @Ignore
    public final static String ID = "ID_ATTR";
    @Ignore
    public final static String USERID = "userid";
    @Ignore
    public final static String WEIGHT = "weight";
    @Ignore
    public final static String DATE = "date";
    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.US);

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userid;
    private Double weight;
    @TypeConverters(DateConverter.class)
    private Date date;

    @Ignore
    public WeightRecordItem(long userid, Double weight, Date date) {
        this.userid = userid;
        this.weight = weight;
        this.date = date;
    }

    public WeightRecordItem(long id, long userid, Double weight, Date date) {
        this.id = id;
        this.userid = userid;
        this.weight = weight;
        this.date = date;
    }

    @Ignore
    public WeightRecordItem(long id, long userid, Double weight, String date) {
        this.id = id;
        this.userid = userid;
        this.weight = weight;
        try {
            this.date = WeightRecordItem.FORMAT.parse(date);
        } catch (ParseException e) {
            this.date = new Date();
        }
    }

    @Ignore
    public WeightRecordItem(Intent intent) {
        this.id = intent.getLongExtra(WeightRecordItem.ID,0);
        this.userid = intent.getLongExtra(WeightRecordItem.USERID,0);
        this.weight = intent.getDoubleExtra(WeightRecordItem.WEIGHT,0.0);
        try {
            this.date = WeightRecordItem.FORMAT.parse(intent.getStringExtra(WeightRecordItem.DATE));
        } catch (ParseException e) {
            this.date = new Date();
        }
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getUserid() { return userid; }

    public void setUserid(long userid) { this.userid = userid; }

    public Double getWeight() { return weight; }

    public void setWeight(Double weight) { this.weight = weight; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, long id, long userid, Double weight, String date) {
        intent.putExtra(WeightRecordItem.ID, id);
        intent.putExtra(WeightRecordItem.USERID, userid);
        intent.putExtra(WeightRecordItem.WEIGHT, weight);
        intent.putExtra(WeightRecordItem.DATE, date);
    }

    public String toString() {
        return id + ITEM_SEP + userid + ITEM_SEP + weight + ITEM_SEP + FORMAT.format(date);
    }

    public String toLog() {
        return "ID_ATTR: " + id + ITEM_SEP + "Userid:" + userid + ITEM_SEP + "Weight:" + weight
                + ITEM_SEP + "Date:" + FORMAT.format(date);
    }
}
