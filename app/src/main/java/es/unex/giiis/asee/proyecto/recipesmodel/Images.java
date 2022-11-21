package es.unex.giiis.asee.proyecto.recipesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Images implements Serializable {

    @SerializedName("THUMBNAIL")
    @Expose
    private Image thumbnail;
    @SerializedName("SMALL")
    @Expose
    private Image small;
    @SerializedName("REGULAR")
    @Expose
    private Image regular;
    @SerializedName("LARGE")
    @Expose
    private Image large;

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Image getSmall() {
        return small;
    }

    public void setSmall(Image small) {
        this.small = small;
    }

    public Image getRegular() {
        return regular;
    }

    public void setRegular(Image regular) {
        this.regular = regular;
    }

    public Image getLarge() {
        return large;
    }

    public void setLarge(Image large) {
        this.large = large;
    }

}