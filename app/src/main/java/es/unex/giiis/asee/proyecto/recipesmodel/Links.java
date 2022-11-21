package es.unex.giiis.asee.proyecto.recipesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Links implements Serializable {

    @SerializedName("self")
    @Expose
    private Link self;

    @SerializedName("next")
    @Expose
    private Link next;

    public Link getNext() {
        return next;
    }

    public void setNext(Link next) {
        this.next = next;
    }

}
