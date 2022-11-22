package es.unex.giiis.asee.proyecto.recipesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TotalDaily implements Serializable {

    @SerializedName("ENERC_KCAL")
    @Expose
    private Daily enercKcal;
    @SerializedName("FAT")
    @Expose
    private Daily fat;
    @SerializedName("FASAT")
    @Expose
    private Daily fasat;
    @SerializedName("CHOCDF")
    @Expose
    private Daily chocdf;
    @SerializedName("FIBTG")
    @Expose
    private Daily fibtg;
    @SerializedName("PROCNT")
    @Expose
    private Daily procnt;
    @SerializedName("CHOLE")
    @Expose
    private Daily chole;
    @SerializedName("NA")
    @Expose
    private Daily na;
    @SerializedName("CA")
    @Expose
    private Daily ca;
    @SerializedName("MG")
    @Expose
    private Daily mg;
    @SerializedName("K")
    @Expose
    private Daily k;
    @SerializedName("FE")
    @Expose
    private Daily fe;
    @SerializedName("ZN")
    @Expose
    private Daily zn;
    @SerializedName("VITA_RAE")
    @Expose
    private Daily vitaRae;
    @SerializedName("P")
    @Expose
    private Daily p;
    @SerializedName("VITC")
    @Expose
    private Daily vitc;
    @SerializedName("THIA")
    @Expose
    private Daily thia;
    @SerializedName("RIBF")
    @Expose
    private Daily ribf;
    @SerializedName("NIA")
    @Expose
    private Daily nia;
    @SerializedName("VITB6A")
    @Expose
    private Daily vitb6a;
    @SerializedName("FOLDFE")
    @Expose
    private Daily foldfe;
    @SerializedName("VITB12")
    @Expose
    private Daily vitb12;
    @SerializedName("VITD")
    @Expose
    private Daily vitd;
    @SerializedName("TOCPHA")
    @Expose
    private Daily tocpha;
    @SerializedName("VITK1")
    @Expose
    private Daily vitk1;

    public Daily getEnercKcal() {
        return enercKcal;
    }

    public void setEnercKcal(Daily enercKcal) {
        this.enercKcal = enercKcal;
    }

    public Daily getFat() {
        return fat;
    }

    public void setFat(Daily fat) {
        this.fat = fat;
    }

    public Daily getFasat() {
        return fasat;
    }

    public void setFasat(Daily fasat) {
        this.fasat = fasat;
    }

    public Daily getChocdf() {
        return chocdf;
    }

    public void setChocdf(Daily chocdf) {
        this.chocdf = chocdf;
    }

    public Daily getFibtg() {
        return fibtg;
    }

    public void setFibtg(Daily fibtg) {
        this.fibtg = fibtg;
    }

    public Daily getProcnt() {
        return procnt;
    }

    public void setProcnt(Daily procnt) {
        this.procnt = procnt;
    }

    public Daily getChole() {
        return chole;
    }

    public void setChole(Daily chole) {
        this.chole = chole;
    }

    public Daily getNa() {
        return na;
    }

    public void setNa(Daily na) {
        this.na = na;
    }

    public Daily getCa() {
        return ca;
    }

    public void setCa(Daily ca) {
        this.ca = ca;
    }

    public Daily getMg() {
        return mg;
    }

    public void setMg(Daily mg) {
        this.mg = mg;
    }

    public Daily getK() {
        return k;
    }

    public void setK(Daily k) {
        this.k = k;
    }

    public Daily getFe() {
        return fe;
    }

    public void setFe(Daily fe) {
        this.fe = fe;
    }

    public Daily getZn() {
        return zn;
    }

    public void setZn(Daily zn) {
        this.zn = zn;
    }

    public Daily getP() {
        return p;
    }

    public void setP(Daily p) {
        this.p = p;
    }

    public Daily getVitaRae() {
        return vitaRae;
    }

    public void setVitaRae(Daily vitaRae) {
        this.vitaRae = vitaRae;
    }

    public Daily getVitc() {
        return vitc;
    }

    public void setVitc(Daily vitc) {
        this.vitc = vitc;
    }

    public Daily getThia() {
        return thia;
    }

    public void setThia(Daily thia) {
        this.thia = thia;
    }

    public Daily getRibf() {
        return ribf;
    }

    public void setRibf(Daily ribf) {
        this.ribf = ribf;
    }

    public Daily getNia() {
        return nia;
    }

    public void setNia(Daily nia) {
        this.nia = nia;
    }

    public Daily getVitb6a() {
        return vitb6a;
    }

    public void setVitb6a(Daily vitb6a) {
        this.vitb6a = vitb6a;
    }

    public Daily getFoldfe() {
        return foldfe;
    }

    public void setFoldfe(Daily foldfe) {
        this.foldfe = foldfe;
    }

    public Daily getVitb12() {
        return vitb12;
    }

    public void setVitb12(Daily vitb12) {
        this.vitb12 = vitb12;
    }

    public Daily getVitd() {
        return vitd;
    }

    public void setVitd(Daily vitd) {
        this.vitd = vitd;
    }

    public Daily getTocpha() {
        return tocpha;
    }

    public void setTocpha(Daily tocpha) {
        this.tocpha = tocpha;
    }

    public Daily getVitk1() {
        return vitk1;
    }

    public void setVitk1(Daily vitk1) {
        this.vitk1 = vitk1;
    }

}
