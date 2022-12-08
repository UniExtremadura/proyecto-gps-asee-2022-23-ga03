package es.unex.giiis.asee.proyecto.ui.horario;

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

import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.roomdb.DateConverter;
import es.unex.giiis.asee.proyecto.roomdb.StatusConverter;

@Entity(tableName = "events", foreignKeys = {
        @ForeignKey(entity = UserItem.class,
                parentColumns = "id",
                childColumns = "userid",
                onDelete = ForeignKey.CASCADE)})
public class CalendarDayItem {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    public enum Status {
        NOTDONE, DONE
    };

    @Ignore
    public final static String ID = "ID";
    @Ignore
    public final static String TITLE = "title";
    @Ignore
    public final static String STATUS = "status";
    @Ignore
    public final static String DATE = "date";
    @Ignore
    public final static String TIME = "time";
    @Ignore
    public final static String WEBID = "webid";
    @Ignore
    public final static String USERID = "userid";
    @Ignore
    public final static String TYPE = "type";
    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "HH:mm:ss", Locale.US);

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String webid;
    private long userid;

    @TypeConverters(StatusConverter.class)
    private Status status;

    private String date;

    @TypeConverters(DateConverter.class)
    private Date time = new Date();

    private String type;

    @Ignore
    public CalendarDayItem(String title, String webid, Status status, String date, Date time, long userid, String type) {
        this.title = title;
        this.webid = webid;
        this.status = status;
        this.date = date;
        this.time = time;
        this.userid = userid;
        this.type = type;
    }


    public CalendarDayItem(long id, String title, String webid, long userid, Status status, String date, Date time, String type) {
        this.id = id;
        this.title = title;
        this.webid = webid;
        this.userid = userid;
        this.status = status;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    @Ignore
    public CalendarDayItem(long id, String title, String webid, String status, String date, String time, long userid, String type) {
        this.id = id;
        this.title = title;
        this.webid = webid;
        this.status = Status.valueOf(status);
        this.date = date;
        try {
            this.time = FORMAT.parse(time);
        } catch (ParseException e) {
            this.time = new Date();
        }
        this.userid = userid;
        this.type = type;
    }

    @Ignore
    public CalendarDayItem(Intent intent) {
        id = intent.getLongExtra(ID,0);
        title = intent.getStringExtra(TITLE);
        webid = intent.getStringExtra(WEBID);
        status = Status.valueOf(intent.getStringExtra(STATUS));

        try {
            time = FORMAT.parse(intent.getStringExtra(TIME));
        } catch (ParseException e) {
            time = new Date();
        }
        this.date = intent.getStringExtra(DATE);;
        this.userid = intent.getLongExtra(USERID,0);
        this.type = intent.getStringExtra(TYPE);
    }

    public long getId() { return id; }

    public void setId(long ID) { this.id = ID; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getWebid() { return webid; }

    public void setWebid(String webid) { this.webid = webid; }

    public long getUserid() { return userid; }

    public void setUserid(long userid) { this.userid = userid; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public static void packageIntent(Intent intent, long id, String title,
                                     String webid, Status status, String date, Date time, long userid, String type) {

        intent.putExtra(ID, id);
        intent.putExtra(TITLE, title);
        intent.putExtra(WEBID, webid);
        intent.putExtra(STATUS, status.toString());
        intent.putExtra(DATE, date);
        intent.putExtra(TIME, FORMAT.format(time));
        intent.putExtra(USERID, userid);
        intent.putExtra(TYPE, type);
    }

    public String toString() {
        return id + ITEM_SEP + title + ITEM_SEP + webid + ITEM_SEP + status + ITEM_SEP + date +
                ITEM_SEP + FORMAT.format(time) + ITEM_SEP + userid + ITEM_SEP + type;
    }

    public String toLog() {
        return "ID: " + id + ITEM_SEP + "Title:" + title + ITEM_SEP + "Webid:" + webid
                + ITEM_SEP + "Status:" + status + ITEM_SEP + "Date:" + date + ITEM_SEP + "Time:"
                + FORMAT.format(time) + ITEM_SEP + "Userid:" + userid + ITEM_SEP + "Type:" + type;
    }
}

