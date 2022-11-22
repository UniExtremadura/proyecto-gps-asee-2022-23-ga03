package es.unex.giiis.asee.proyecto.ui.recetas;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import es.unex.giiis.asee.proyecto.login_register.UserItem;

@Entity(tableName = "recipefavorites" , foreignKeys = {
        @ForeignKey(entity = UserItem.class,
                parentColumns = "id",
                childColumns = "userid",
                onDelete = ForeignKey.CASCADE)})
public class FavoriteRecipeItem {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @Ignore
    public final static String ID = "ID";
    @Ignore
    public final static String TITLE = "title";
    @Ignore
    public final static String CALORIES = "calories";
    @Ignore
    public final static String WEBID = "webid";
    @Ignore
    public final static String IMAGEURL = "imageurl";
    @Ignore
    public final static String USERID = "userid";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private Double calories;
    private String webid;
    private String imageurl;
    private long userid;

    @Ignore
    public FavoriteRecipeItem(String title, Double calories, String webid, String imageurl, long userid) {
        this.title = title;
        this.calories = calories;
        this.webid = webid;
        this.imageurl = imageurl;
        this.userid = userid;
    }

    public FavoriteRecipeItem(long id, String title, Double calories, String webid, String imageurl, long userid) {
        this.id = id;
        this.title = title;
        this.calories = calories;
        this.webid = webid;
        this.imageurl = imageurl;
        this.userid = userid;
    }

    @Ignore
    public FavoriteRecipeItem(Intent intent) {
        this.id = intent.getLongExtra(FavoriteRecipeItem.ID,0);
        this.title = intent.getStringExtra(FavoriteRecipeItem.TITLE);
        this.calories = intent.getDoubleExtra(FavoriteRecipeItem.CALORIES, 0.0);
        this.webid = intent.getStringExtra(FavoriteRecipeItem.WEBID);
        this.imageurl = intent.getStringExtra(FavoriteRecipeItem.IMAGEURL);
        this.userid = intent.getLongExtra(FavoriteRecipeItem.USERID,0);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public long getUserid() { return userid; }

    public void setUserid(long userid) { this.userid = userid; }

    public static void packageIntent(Intent intent, long id, String title, Double calories, String webid, String imageurl, long userid) {
        intent.putExtra(FavoriteRecipeItem.ID, id);
        intent.putExtra(FavoriteRecipeItem.TITLE, title);
        intent.putExtra(FavoriteRecipeItem.CALORIES, calories);
        intent.putExtra(FavoriteRecipeItem.WEBID, webid);
        intent.putExtra(FavoriteRecipeItem.IMAGEURL, imageurl);
        intent.putExtra(FavoriteRecipeItem.USERID, userid);
    }

    public String toString() {
        return id + ITEM_SEP + userid + ITEM_SEP + title + ITEM_SEP + calories + ITEM_SEP + webid + ITEM_SEP + imageurl;
    }

    public String toLog() {
        return "ID: " + id + ITEM_SEP + "Userid:" + userid + ITEM_SEP + "Title:" + title + ITEM_SEP + "Calories:" + calories
                + ITEM_SEP + "Webid:" + webid + ITEM_SEP + "Imageurl:" + imageurl;
    }
}
