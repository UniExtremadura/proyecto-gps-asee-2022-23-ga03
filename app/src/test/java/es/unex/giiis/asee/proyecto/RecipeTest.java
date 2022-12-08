package es.unex.giiis.asee.proyecto;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.recipesmodel.Daily;
import es.unex.giiis.asee.proyecto.recipesmodel.Digest;
import es.unex.giiis.asee.proyecto.recipesmodel.Image;
import es.unex.giiis.asee.proyecto.recipesmodel.Images;
import es.unex.giiis.asee.proyecto.recipesmodel.Ingredient;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.recipesmodel.TotalDaily;
import es.unex.giiis.asee.proyecto.recipesmodel.TotalNutrients;

public class RecipeTest {

    private Recipe item;

    @Before
    public void init() {
        item = new Recipe();
        item.setCalories(10.0);
        item.setLabel("label");
        item.setCo2EmissionsClass("emisiones");
        item.setExternalId("1");
        item.setGlycemicIndex(10.0);
        item.setImage("image.png");
        item.setShareAs("compartir");
        item.setSource("source");
        item.setTotalCO2Emissions(100.0);
        item.setTotalTime(5);
        item.setTotalWeight(10.0);
        item.setUri("https://www.uri.com");
        item.setUrl("https://www.url.com");
        item.setYield(5);
        List<String> cautions = new ArrayList<>();
        cautions.add("caution1");
        item.setCautions(cautions);
        List<String> cuisineList = new ArrayList<>();
        cuisineList.add("cuisine1");
        item.setCuisineType(cuisineList);
        List<String> dietList = new ArrayList<>();
        dietList.add("diet1");
        item.setDietLabels(dietList);
        List<String> dishList = new ArrayList<>();
        dishList.add("dish1");
        item.setDishType(dishList);
        List<String> healthList = new ArrayList<>();
        healthList.add("health1");
        item.setHealthLabels(healthList);
        List<String> ingrList = new ArrayList<>();
        ingrList.add("ingr1");
        item.setIngredientLines(ingrList);
        List<String> instrList = new ArrayList<>();
        instrList.add("instr1");
        item.setInstructions(instrList);
        List<String> mealList = new ArrayList<>();
        mealList.add("meal1");
        item.setMealType(mealList);
        List<String> tagList = new ArrayList<>();
        tagList.add("tag1");
        item.setTags(tagList);
        TotalDaily totalDaily = new TotalDaily();
        Daily ca = new Daily();
        ca.setLabel("ca");
        totalDaily.setCa(ca);
        item.setTotalDaily(totalDaily);
        TotalNutrients totalNutrients = new TotalNutrients();
        Daily ca2 = new Daily();
        totalNutrients.setCa(ca2);
        ca2.setLabel("ca2");
        item.setTotalNutrients(totalNutrients);
        Images images = new Images();
        Image large2 = new Image();
        large2.setUrl("large");
        images.setLarge(large2);
        item.setImages(images);
        List<Digest> listaDigest = new ArrayList<>();
        Digest digest = new Digest();
        digest.setLabel("digest");
        listaDigest.add(digest);
        item.setDigest(listaDigest);
        List<Ingredient> listaIngr = new ArrayList<>();
        Ingredient ingr = new Ingredient();
        ingr.setText("ingr");
        listaIngr.add(ingr);
        item.setIngredients(listaIngr);
    }

    @Test
    public void getUriTest() {
        assertEquals("https://www.uri.com", item.getUri());
    }

    @Test
    public void setUriTest() {
        assertEquals("https://www.uri.com", item.getUri());
        item.setUri("https://www.uri1.com");
        assertEquals("https://www.uri1.com", item.getUri());
    }

    @Test
    public void getLabelTest() {
        assertEquals("label", item.getLabel());
    }

    @Test
    public void setLabelTest() {
        assertEquals("label", item.getLabel());
        item.setLabel("label1");
        assertEquals("label1", item.getLabel());
    }

    @Test
    public void getImageTest() {
        assertEquals("image.png", item.getImage());
    }

    @Test
    public void setImageTest() {
        assertEquals("image.png", item.getImage());
        item.setImage("image1.png");
        assertEquals("image1.png", item.getImage());

    }

    @Test
    public void getImagesTest() {
        assertEquals("large", item.getImages().getLarge().getUrl());
    }

    @Test
    public void setImagesTest() {
        assertEquals("large", item.getImages().getLarge().getUrl());
        Images images = new Images();
        Image image = new Image();
        image.setUrl("regular");
        images.setRegular(image);
        item.setImages(images);
        assertEquals("regular", item.getImages().getRegular().getUrl());
    }

    @Test
    public void getSourceTest() {
        assertEquals("source", item.getSource());
    }

    @Test
    public void setSourceTest() {
        assertEquals("source", item.getSource());
        item.setSource("source1");
        assertEquals("source1", item.getSource());
    }

    @Test
    public void getUrlTest() {
        assertEquals("https://www.url.com", item.getUrl());
    }

    @Test
    public void setUrlTest() {
        assertEquals("https://www.url.com", item.getUrl());
        item.setUrl("https://www.url1.com");
        assertEquals("https://www.url1.com", item.getUrl());
    }

    @Test
    public void getShareAsTest() {
        assertEquals("compartir", item.getShareAs());
    }

    @Test
    public void setShareAsTest() {
        assertEquals("compartir", item.getShareAs());
        item.setShareAs("compartir1");
        assertEquals("compartir1", item.getShareAs());
    }

    @Test
    public void getYieldTest() {
        assertEquals(Integer.parseInt("5"), item.getYield(), 0.0);
    }

    @Test
    public void setYieldTest() {
        assertEquals(Integer.parseInt("5"), item.getYield(), 0.0);
        item.setYield(10);
        assertEquals(Integer.parseInt("10"), item.getYield(), 0.0);
    }

    @Test
    public void getDietLabelsTest() {
        assertEquals("diet1", item.getDietLabels().get(0));
    }

    @Test
    public void setDietLabelsTest() {
        assertEquals("diet1", item.getDietLabels().get(0));
        List<String> dietList = new ArrayList<>();
        dietList.add("diet2");
        item.setDietLabels(dietList);
        assertEquals("diet2", item.getDietLabels().get(0));
    }

    @Test
    public void getHealthLabelsTest() {
        assertEquals("health1", item.getHealthLabels().get(0));
    }

    @Test
    public void setHealthLabelsTest() {
        assertEquals("health1", item.getHealthLabels().get(0));
        List<String> healthList = new ArrayList<>();
        healthList.add("health2");
        item.setHealthLabels(healthList);
        assertEquals("health2", item.getHealthLabels().get(0));
    }

    @Test
    public void getCautionsTest() {
        assertEquals("caution1", item.getCautions().get(0));
    }

    @Test
    public void setCautionsTest() {
        assertEquals("caution1", item.getCautions().get(0));
        List<String> cautions = new ArrayList<>();
        cautions.add("caution2");
        item.setCautions(cautions);
        assertEquals("caution2", item.getCautions().get(0));
    }

    @Test
    public void getIngredientLinesTest() {
        assertEquals("ingr1", item.getIngredientLines().get(0));
    }

    @Test
    public void setInstructionsTest() {
        assertEquals("instr1", item.getInstructions().get(0));
        List<String> instrList = new ArrayList<>();
        instrList.add("instr2");
        item.setInstructions(instrList);
        assertEquals("instr2", item.getInstructions().get(0));
    }

    @Test
    public void getInstructionsTest() {
        assertEquals("instr1", item.getInstructions().get(0));
    }

    @Test
    public void setTagsTest() {
        assertEquals("tag1", item.getTags().get(0));
        List<String> tagList = new ArrayList<>();
        tagList.add("tag2");
        item.setTags(tagList);
        assertEquals("tag2", item.getTags().get(0));
    }

    @Test
    public void getTagsTest() {
        assertEquals("tag1", item.getTags().get(0));
    }

    @Test
    public void setIngredientLinesTest() {
        assertEquals("ingr1", item.getIngredientLines().get(0));
        List<String> ingrList = new ArrayList<>();
        ingrList.add("ingr2");
        item.setIngredientLines(ingrList);
        assertEquals("ingr2", item.getIngredientLines().get(0));
    }

    @Test
    public void getIngredientsTest() {
        assertEquals("ingr", item.getIngredients().get(0).getText());
    }

    @Test
    public void setIngredientsTest() {
        assertEquals("ingr", item.getIngredients().get(0).getText());
        List<Ingredient> listaIngr = new ArrayList<>();
        Ingredient ingr = new Ingredient();
        ingr.setText("ingr1");
        listaIngr.add(ingr);
        item.setIngredients(listaIngr);
        assertEquals("ingr1", item.getIngredients().get(0).getText());
    }

    @Test
    public void getGlycemicIndexTest() {
        assertEquals(10, item.getGlycemicIndex(), 0.0);
    }

    @Test
    public void setGlycemicIndexTest() {
        assertEquals(10.0, item.getGlycemicIndex(), 0.0);
        item.setGlycemicIndex(20.0);
        assertEquals(20.0, item.getGlycemicIndex(), 0.0);
    }

    @Test
    public void getTotalCO2EmissionsTest() {
        assertEquals(100.0, item.getTotalCO2Emissions(), 0.0);
    }

    @Test
    public void setTotalCO2EmissionsTest() {
        assertEquals(100.0, item.getTotalCO2Emissions(), 0.0);
        item.setTotalCO2Emissions(300.0);
        assertEquals(300.0, item.getTotalCO2Emissions(), 0.0);
    }

    @Test
    public void getCo2EmissionsClassTest() {
        assertEquals("emisiones", item.getCo2EmissionsClass());
    }

    @Test
    public void setCo2EmissionsClassTest() {
        assertEquals("emisiones", item.getCo2EmissionsClass());
        item.setCo2EmissionsClass("emisiones1");
        assertEquals("emisiones1", item.getCo2EmissionsClass());
    }

    @Test
    public void getCaloriesTest() {
        assertEquals(10.0, item.getCalories(), 0.0);
    }

    @Test
    public void setCaloriesTest() {
        assertEquals(10.0, item.getCalories(), 0.0);
        item.setCalories(1000.0);
        assertEquals(1000.0, item.getCalories(), 0.0);
    }

    @Test
    public void getTotalWeightTest() {
        assertEquals(10.0, item.getTotalWeight(), 0.0);
    }

    @Test
    public void setTotalWeightTest() {
        assertEquals(10.0, item.getTotalWeight(), 0.0);
        item.setTotalWeight(2000.0);
        assertEquals(2000.0, item.getTotalWeight(), 0.0);
    }

    @Test
    public void getTotalTimeTest() {
        assertEquals(5, item.getTotalTime(), 0.0);
    }

    @Test
    public void setTotalTimeTest() {
        assertEquals(5, item.getTotalTime(), 0.0);
        item.setTotalTime(90);
        assertEquals(90, item.getTotalTime(), 0.0);
    }

    @Test
    public void getCuisineTypeTest() {
        assertEquals("cuisine1", item.getCuisineType().get(0));
    }

    @Test
    public void setCuisineTypeTest() {
        assertEquals("cuisine1", item.getCuisineType().get(0));
        List<String> cuisineList = new ArrayList<>();
        cuisineList.add("cuisine2");
        item.setCuisineType(cuisineList);
        assertEquals("cuisine2", item.getCuisineType().get(0));
    }

    @Test
    public void getMealTypeTest() {
        assertEquals("meal1", item.getMealType().get(0));
    }

    @Test
    public void setMealTypeTest() {
        assertEquals("meal1", item.getMealType().get(0));
        List<String> mealList = new ArrayList<>();
        mealList.add("meal2");
        item.setMealType(mealList);
        assertEquals("meal2", item.getMealType().get(0));
    }

    @Test
    public void getDishTypeTest() {
        assertEquals("dish1", item.getDishType().get(0));
    }

    @Test
    public void setDishTypeTest() {
        assertEquals("dish1", item.getDishType().get(0));
        List<String> dishList = new ArrayList<>();
        dishList.add("dish2");
        item.setDishType(dishList);
        assertEquals("dish2", item.getDishType().get(0));
    }

    @Test
    public void getTotalNutrientsTest() {
        assertEquals("ca2", item.getTotalNutrients().getCa().getLabel());
    }

    @Test
    public void setTotalNutrientsTest() {
        assertEquals("ca2", item.getTotalNutrients().getCa().getLabel());
        TotalNutrients totalNutrients = new TotalNutrients();
        Daily fe = new Daily();
        fe.setLabel("fe");
        totalNutrients.setFe(fe);
        item.setTotalNutrients(totalNutrients);
        assertEquals("fe", item.getTotalNutrients().getFe().getLabel());
    }

    @Test
    public void getTotalDailyTest() {
        assertEquals("ca", item.getTotalDaily().getCa().getLabel());
    }

    @Test
    public void setTotalDailyTest() {
        assertEquals("ca", item.getTotalDaily().getCa().getLabel());
        TotalDaily totalDaily = new TotalDaily();
        Daily fe = new Daily();
        fe.setLabel("fe");
        totalDaily.setFe(fe);
        item.setTotalDaily(totalDaily);
        assertEquals("fe", item.getTotalDaily().getFe().getLabel());
    }

    @Test
    public void getDigestTest() {
        assertEquals("digest", item.getDigest().get(0).getLabel());
    }

    @Test
    public void setDigestTest() {
        assertEquals("digest", item.getDigest().get(0).getLabel());
        List<Digest> listaDigest = new ArrayList<>();
        Digest digest = new Digest();
        digest.setLabel("digest1");
        listaDigest.add(digest);
        item.setDigest(listaDigest);
        assertEquals("digest1", item.getDigest().get(0).getLabel());
    }

    @Test
    public void getExternalIdTest() {
        assertEquals("1", item.getExternalId());
    }

    @Test
    public void setExternalIdTest() {
        assertEquals("1", item.getExternalId());
        item.setExternalId("123456789");
        assertEquals("123456789", item.getExternalId());
    }
}