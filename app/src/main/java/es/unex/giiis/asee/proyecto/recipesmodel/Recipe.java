package es.unex.giiis.asee.proyecto.recipesmodel;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe implements Serializable {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("images")
    @Expose
    private Images images;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("shareAs")
    @Expose
    private String shareAs;
    @SerializedName("yield")
    @Expose
    private Integer yield;
    @SerializedName("dietLabels")
    @Expose
    private List<String> dietLabels = null;
    @SerializedName("healthLabels")
    @Expose
    private List<String> healthLabels = null;
    @SerializedName("cautions")
    @Expose
    private List<String> cautions = null;
    @SerializedName("ingredientLines")
    @Expose
    private List<String> ingredientLines = null;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("calories")
    @Expose
    private Double calories;
    @SerializedName("glycemicIndex")
    @Expose
    private Double glycemicIndex;
    @SerializedName("totalCO2Emissions")
    @Expose
    private Double totalCO2Emissions;
    @SerializedName("co2EmissionsClass")
    @Expose
    private String co2EmissionsClass;
    @SerializedName("totalWeight")
    @Expose
    private Double totalWeight;
    @SerializedName("totalTime")
    @Expose
    private Integer totalTime;
    @SerializedName("cuisineType")
    @Expose
    private List<String> cuisineType = null;
    @SerializedName("mealType")
    @Expose
    private List<String> mealType = null;
    @SerializedName("dishType")
    @Expose
    private List<String> dishType = null;
    @SerializedName("instructions")
    @Expose
    private List<String> instructions = null;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("externalId")
    @Expose
    private String externalId;
    @SerializedName("totalNutrients")
    @Expose
    private TotalNutrients totalNutrients;
    @SerializedName("totalDaily")
    @Expose
    private TotalDaily totalDaily;
    @SerializedName("digest")
    @Expose
    private List<Digest> digest = null;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    public Integer getYield() {
        return yield;
    }

    public void setYield(Integer yield) {
        this.yield = yield;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public List<String> getCautions() {
        return cautions;
    }

    public void setCautions(List<String> cautions) {
        this.cautions = cautions;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Double getGlycemicIndex() {
        return glycemicIndex;
    }

    public void setGlycemicIndex(Double glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }

    public Double getTotalCO2Emissions() {
        return totalCO2Emissions;
    }

    public void setTotalCO2Emissions(Double totalCO2Emissions) {
        this.totalCO2Emissions = totalCO2Emissions;
    }

    public String getCo2EmissionsClass() {
        return co2EmissionsClass;
    }

    public void setCo2EmissionsClass(String co2EmissionsClass) {
        this.co2EmissionsClass = co2EmissionsClass;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public List<String> getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(List<String> cuisineType) {
        this.cuisineType = cuisineType;
    }

    public List<String> getMealType() {
        return mealType;
    }

    public void setMealType(List<String> mealType) {
        this.mealType = mealType;
    }

    public List<String> getDishType() {
        return dishType;
    }

    public void setDishType(List<String> dishType) {
        this.dishType = dishType;
    }

    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(TotalNutrients totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public TotalDaily getTotalDaily() {
        return totalDaily;
    }

    public void setTotalDaily(TotalDaily totalDaily) {
        this.totalDaily = totalDaily;
    }

    public List<Digest> getDigest() {
        return digest;
    }

    public void setDigest(List<Digest> digest) {
        this.digest = digest;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

}
