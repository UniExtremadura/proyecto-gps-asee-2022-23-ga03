package es.unex.giiis.asee.proyecto.ui.horario;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import es.unex.giiis.asee.proyecto.roomdb.PeriodConverter;

@Entity(tableName = "recipesdiet", foreignKeys = {
        @ForeignKey(entity = PlantillaItem.class,
                parentColumns = "id",
                childColumns = "plantillaid",
                onDelete = ForeignKey.CASCADE)})
public class RecipePlantillaItem {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    public enum Period {
        BREAKFAST, LUNCH, DINNER
    };

    @Ignore
    public final static String ID = "ID";
    @Ignore
    public final static String TITLE = "title";
    @Ignore
    public final static String PERIOD = "period";
    @Ignore
    public final static String CALORIES = "calories";
    @Ignore
    public final static String WEBID = "webid";
    @Ignore
    public final static String IMAGEURL = "imageurl";
    @Ignore
    public final static String PLANTILLAID = "plantillaid";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    @TypeConverters(PeriodConverter.class)
    private Period period;
    private Double calories;
    private String webid;
    private String imageurl;
    private long plantillaid;

    @Ignore
    public RecipePlantillaItem(String title, Period period, Double calories, String webid, String imageurl, long plantillaid) {
        this.title = title;
        this.calories = calories;
        this.period = period;
        this.webid = webid;
        this.imageurl = imageurl;
        this.plantillaid = plantillaid;
    }

    public RecipePlantillaItem(long id, String title, Period period, Double calories, String webid, String imageurl, long plantillaid) {
        this.id = id;
        this.title = title;
        this.calories = calories;
        this.period = period;
        this.webid = webid;
        this.imageurl = imageurl;
        this.plantillaid = plantillaid;
    }

    @Ignore
    public RecipePlantillaItem(long id, String title, String period, Double calories, String webid, String imageurl, long plantillaid) {
        this.id = id;
        this.title = title;
        this.period = Period.valueOf(period);
        this.calories = calories;
        this.webid = webid;
        this.imageurl = imageurl;
        this.plantillaid = plantillaid;
    }

    @Ignore
    public RecipePlantillaItem(Intent intent) {
        this.id = intent.getLongExtra(RecipePlantillaItem.ID,0);
        this.title = intent.getStringExtra(RecipePlantillaItem.TITLE);
        if(intent.getStringExtra(RecipePlantillaItem.PERIOD) == null) {
            this.period = Period.LUNCH;
        } else {
            this.period = Period.valueOf(intent.getStringExtra(RecipePlantillaItem.PERIOD));
        }
        this.calories = intent.getDoubleExtra(RecipePlantillaItem.CALORIES, 0.0);
        this.webid = intent.getStringExtra(RecipePlantillaItem.WEBID);
        this.imageurl = intent.getStringExtra(RecipePlantillaItem.IMAGEURL);
        this.plantillaid = intent.getLongExtra(RecipePlantillaItem.PLANTILLAID,0);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Period getPeriod() { return period; }

    public void setPeriod(Period period) { this.period = period; }

    public Double getCalories() { return calories; }

    public void setCalories(Double calories) { this.calories = calories;}

    public String getWebid() {
        return webid;
    }

    public void setWebid(String webid) {
        this.webid = webid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getPlantillaid() { return plantillaid; }

    public void setPlantillaid(long plantillaid) { this.plantillaid = plantillaid; }

    public static void packageIntent(Intent intent, String title, Double calories,
                                     String webid, String imageurl, Period period, long plantillaid) {
        intent.putExtra(RecipePlantillaItem.TITLE, title);
        intent.putExtra(RecipePlantillaItem.CALORIES, calories);
        intent.putExtra(RecipePlantillaItem.WEBID, webid);
        intent.putExtra(RecipePlantillaItem.IMAGEURL, imageurl);
        intent.putExtra(RecipePlantillaItem.PERIOD, String.valueOf(period));
        intent.putExtra(RecipePlantillaItem.PLANTILLAID, plantillaid);
    }

    public String toString() {
        return id + ITEM_SEP + plantillaid + ITEM_SEP + title + ITEM_SEP + calories + ITEM_SEP + webid + ITEM_SEP + imageurl;
    }

    public String toLog() {
        return "ID: " + id + ITEM_SEP + "Plantillaid:" + plantillaid + ITEM_SEP + "Title:" + title + ITEM_SEP + "Calories:" + calories
                + ITEM_SEP + "Webid:" + webid + ITEM_SEP + "Imageurl:" + imageurl;
    }
}
