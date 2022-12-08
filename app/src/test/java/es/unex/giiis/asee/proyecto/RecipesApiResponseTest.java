package es.unex.giiis.asee.proyecto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.recipesmodel.Daily;
import es.unex.giiis.asee.proyecto.recipesmodel.Digest;
import es.unex.giiis.asee.proyecto.recipesmodel.Hit;
import es.unex.giiis.asee.proyecto.recipesmodel.Image;
import es.unex.giiis.asee.proyecto.recipesmodel.Images;
import es.unex.giiis.asee.proyecto.recipesmodel.Ingredient;
import es.unex.giiis.asee.proyecto.recipesmodel.Link;
import es.unex.giiis.asee.proyecto.recipesmodel.Links;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.recipesmodel.RecipesApiResponse;
import es.unex.giiis.asee.proyecto.recipesmodel.TotalDaily;
import es.unex.giiis.asee.proyecto.recipesmodel.TotalNutrients;

public class RecipesApiResponseTest {

    private RecipesApiResponse item;

    @Before
    public void init() throws Exception {
        item = new RecipesApiResponse();
        item.setFrom(1);
        item.setTo(20);
        item.setCount(20);

        Links links1 = new Links();
        Link link = new Link();
        link.setTitle("Pagina principal");
        link.setHref("enlace");
        links1.setNext(link);
        item.setLinks(links1);

        Hit hit = new Hit();
        Links links = new Links();
        Link linkSig = new Link();
        linkSig.setTitle("link");
        linkSig.setHref("href");
        links.setNext(linkSig);
        hit.setLinks(links);

        Recipe recipe = new Recipe();
        recipe.setCalories(10.0);
        recipe.setLabel("label");
        recipe.setCo2EmissionsClass("emisiones");
        recipe.setExternalId("1");
        recipe.setGlycemicIndex(10.0);
        recipe.setImage("image.png");
        recipe.setShareAs("compartir");
        recipe.setSource("source");
        recipe.setTotalCO2Emissions(100.0);
        recipe.setTotalTime(5);
        recipe.setTotalWeight(10.0);
        recipe.setUri("https://www.uri.com");
        recipe.setUrl("https://www.url.com");
        recipe.setYield(5);
        List<String> cautions = new ArrayList<>();
        cautions.add("caution1");
        recipe.setCautions(cautions);
        List<String> cuisineList = new ArrayList<>();
        cuisineList.add("cuisine1");
        recipe.setCuisineType(cuisineList);
        List<String> dietList = new ArrayList<>();
        dietList.add("diet1");
        recipe.setDietLabels(dietList);
        List<String> dishList = new ArrayList<>();
        dishList.add("dish1");
        recipe.setDishType(dishList);
        List<String> healthList = new ArrayList<>();
        healthList.add("health1");
        recipe.setHealthLabels(healthList);
        List<String> ingrList = new ArrayList<>();
        ingrList.add("ingr1");
        recipe.setIngredientLines(ingrList);
        List<String> instrList = new ArrayList<>();
        instrList.add("instr1");
        recipe.setInstructions(instrList);
        List<String> mealList = new ArrayList<>();
        mealList.add("meal1");
        recipe.setMealType(mealList);
        List<String> tagList = new ArrayList<>();
        tagList.add("tag1");
        recipe.setTags(tagList);
        TotalDaily totalDaily = new TotalDaily();
        Daily ca = new Daily();
        ca.setLabel("ca");
        totalDaily.setCa(ca);
        recipe.setTotalDaily(totalDaily);
        TotalNutrients totalNutrients = new TotalNutrients();
        Daily ca2 = new Daily();
        totalNutrients.setCa(ca2);
        ca2.setLabel("ca2");
        recipe.setTotalNutrients(totalNutrients);
        Images images = new Images();
        Image large2 = new Image();
        large2.setUrl("large");
        images.setLarge(large2);
        recipe.setImages(images);
        List<Digest> listaDigest = new ArrayList<>();
        Digest digest = new Digest();
        digest.setLabel("digest");
        listaDigest.add(digest);
        recipe.setDigest(listaDigest);
        List<Ingredient> listaIngr = new ArrayList<>();
        Ingredient ingr = new Ingredient();
        ingr.setText("ingr");
        listaIngr.add(ingr);
        recipe.setIngredients(listaIngr);
        hit.setRecipe(recipe);

        List<Hit> hits = new ArrayList<>();
        hits.add(hit);
        item.setHits(hits);
    }

    @Test
    public void getFromTest() {
        assertEquals(1, item.getFrom(), 0.0);
    }

    @Test
    public void setFromTest() {
        assertEquals(1, item.getFrom(), 0.0);
        item.setFrom(2);
        assertEquals(2, item.getFrom(), 0.0);
    }

    @Test
    public void getToTest() {
        assertEquals(20, item.getTo(), 0.0);
    }

    @Test
    public void setToTest() {
        assertEquals(20, item.getTo(), 0.0);
        item.setTo(30);
        assertEquals(30, item.getTo(), 0.0);
    }

    @Test
    public void getCountTest() {
        assertEquals(20, item.getCount(), 0.0);
    }

    @Test
    public void setCountTest() {
        assertEquals(20, item.getCount(), 0.0);
        item.setCount(30);
        assertEquals(30, item.getCount(), 0.0);
    }

    @Test
    public void getLinksTest() {
        assertNotNull(item.getLinks());
        assertEquals("Pagina principal", item.getLinks().getNext().getTitle());
        assertEquals("enlace", item.getLinks().getNext().getHref());
    }

    @Test
    public void setLinksTest() {
        assertNotNull(item.getLinks());
        assertEquals("Pagina principal", item.getLinks().getNext().getTitle());
        assertEquals("enlace", item.getLinks().getNext().getHref());
        Links links1 = new Links();
        Link link = new Link();
        link.setTitle("Pagina secundaria");
        link.setHref("enlace2");
        links1.setNext(link);
        item.setLinks(links1);
        assertEquals("Pagina secundaria", item.getLinks().getNext().getTitle());
        assertEquals("enlace2", item.getLinks().getNext().getHref());
    }

    @Test
    public void getHitsTest() {
        assertNotNull(item.getHits());
        assertEquals(1, item.getHits().size());
        Hit hit = item.getHits().get(0);
        assertEquals(10.0, hit.getRecipe().getCalories(), 0.0);
        assertEquals("label", hit.getRecipe().getLabel());
        assertEquals("emisiones", hit.getRecipe().getCo2EmissionsClass());
        assertEquals("1", hit.getRecipe().getExternalId());
        assertEquals(10.0, hit.getRecipe().getGlycemicIndex(), 0.0);
        assertEquals("image.png", hit.getRecipe().getImage());
        assertEquals("compartir", hit.getRecipe().getShareAs());
        assertEquals("source", hit.getRecipe().getSource());
        assertEquals(100.0, hit.getRecipe().getTotalCO2Emissions(), 0.0);
        assertEquals(Integer.parseInt("5"), hit.getRecipe().getTotalTime(), 0.0);
        assertEquals(10.0, hit.getRecipe().getTotalWeight(), 0.0);
        assertEquals("https://www.uri.com", hit.getRecipe().getUri());
        assertEquals("https://www.url.com", hit.getRecipe().getUrl());
        assertEquals(Integer.parseInt("5"), hit.getRecipe().getYield(), 0.0);
        assertEquals("caution1", hit.getRecipe().getCautions().get(0));
        assertEquals("cuisine1", hit.getRecipe().getCuisineType().get(0));
        assertEquals("diet1", hit.getRecipe().getDietLabels().get(0));
        assertEquals("dish1", hit.getRecipe().getDishType().get(0));
        assertEquals("health1", hit.getRecipe().getHealthLabels().get(0));
        assertEquals("ingr1", hit.getRecipe().getIngredientLines().get(0));
        assertEquals("instr1", hit.getRecipe().getInstructions().get(0));
        assertEquals("meal1", hit.getRecipe().getMealType().get(0));
        assertEquals("tag1", hit.getRecipe().getTags().get(0));
        assertEquals("ca", hit.getRecipe().getTotalDaily().getCa().getLabel());
        assertEquals("ca2", hit.getRecipe().getTotalNutrients().getCa().getLabel());
        assertEquals("large", hit.getRecipe().getImages().getLarge().getUrl());
        assertEquals("digest", hit.getRecipe().getDigest().get(0).getLabel());
        assertEquals("ingr", hit.getRecipe().getIngredients().get(0).getText());
        assertEquals("link", hit.getLinks().getNext().getTitle());
        assertEquals("href", hit.getLinks().getNext().getHref());
    }

    @Test
    public void setHitsTest() {
        assertNotNull(item.getHits());
        assertEquals(1, item.getHits().size());
        Hit hit = item.getHits().get(0);
        assertEquals(10.0, hit.getRecipe().getCalories(), 0.0);
        assertEquals("label", hit.getRecipe().getLabel());
        assertEquals("emisiones", hit.getRecipe().getCo2EmissionsClass());
        assertEquals("1", hit.getRecipe().getExternalId());
        assertEquals(10.0, hit.getRecipe().getGlycemicIndex(), 0.0);
        assertEquals("image.png", hit.getRecipe().getImage());
        assertEquals("compartir", hit.getRecipe().getShareAs());
        assertEquals("source", hit.getRecipe().getSource());
        assertEquals(100.0, hit.getRecipe().getTotalCO2Emissions(), 0.0);
        assertEquals(Integer.parseInt("5"), hit.getRecipe().getTotalTime(), 0.0);
        assertEquals(10.0, hit.getRecipe().getTotalWeight(), 0.0);
        assertEquals("https://www.uri.com", hit.getRecipe().getUri());
        assertEquals("https://www.url.com", hit.getRecipe().getUrl());
        assertEquals(Integer.parseInt("5"), hit.getRecipe().getYield(), 0.0);
        assertEquals("caution1", hit.getRecipe().getCautions().get(0));
        assertEquals("cuisine1", hit.getRecipe().getCuisineType().get(0));
        assertEquals("diet1", hit.getRecipe().getDietLabels().get(0));
        assertEquals("dish1", hit.getRecipe().getDishType().get(0));
        assertEquals("health1", hit.getRecipe().getHealthLabels().get(0));
        assertEquals("ingr1", hit.getRecipe().getIngredientLines().get(0));
        assertEquals("instr1", hit.getRecipe().getInstructions().get(0));
        assertEquals("meal1", hit.getRecipe().getMealType().get(0));
        assertEquals("tag1", hit.getRecipe().getTags().get(0));
        assertEquals("ca", hit.getRecipe().getTotalDaily().getCa().getLabel());
        assertEquals("ca2", hit.getRecipe().getTotalNutrients().getCa().getLabel());
        assertEquals("large", hit.getRecipe().getImages().getLarge().getUrl());
        assertEquals("digest", hit.getRecipe().getDigest().get(0).getLabel());
        assertEquals("ingr", hit.getRecipe().getIngredients().get(0).getText());
        assertEquals("link", hit.getLinks().getNext().getTitle());
        assertEquals("href", hit.getLinks().getNext().getHref());

        Hit hit2 = new Hit();
        Links links = new Links();
        Link linkSig = new Link();
        linkSig.setTitle("link1");
        linkSig.setHref("href1");
        links.setNext(linkSig);
        item.setLinks(links);
        hit2.setLinks(links);

        Recipe recipe2 = new Recipe();
        recipe2.setCalories(2.0);
        recipe2.setLabel("label2");
        recipe2.setCo2EmissionsClass("emisiones2");
        recipe2.setExternalId("2");
        recipe2.setGlycemicIndex(2.0);
        recipe2.setImage("image2.png");
        recipe2.setShareAs("compartir2");
        recipe2.setSource("source2");
        recipe2.setTotalCO2Emissions(2.0);
        recipe2.setTotalTime(2);
        recipe2.setTotalWeight(2.0);
        recipe2.setUri("https://www.uri2.com");
        recipe2.setUrl("https://www.url2.com");
        recipe2.setYield(2);
        List<String> cautions2 = new ArrayList<>();
        cautions2.add("caution2");
        recipe2.setCautions(cautions2);
        List<String> cuisineList2 = new ArrayList<>();
        cuisineList2.add("cuisine2");
        recipe2.setCuisineType(cuisineList2);
        List<String> dietList2 = new ArrayList<>();
        dietList2.add("diet2");
        recipe2.setDietLabels(dietList2);
        List<String> dishList2 = new ArrayList<>();
        dishList2.add("dish2");
        recipe2.setDishType(dishList2);
        List<String> healthList2 = new ArrayList<>();
        healthList2.add("health2");
        recipe2.setHealthLabels(healthList2);
        List<String> ingrList2 = new ArrayList<>();
        ingrList2.add("ingr2");
        recipe2.setIngredientLines(ingrList2);
        List<String> instrList2 = new ArrayList<>();
        instrList2.add("instr2");
        recipe2.setInstructions(instrList2);
        List<String> mealList2 = new ArrayList<>();
        mealList2.add("meal2");
        recipe2.setMealType(mealList2);
        List<String> tagList2 = new ArrayList<>();
        tagList2.add("tag2");
        recipe2.setTags(tagList2);
        TotalDaily totalDaily2 = new TotalDaily();
        Daily ca2 = new Daily();
        ca2.setLabel("ca2");
        totalDaily2.setCa(ca2);
        recipe2.setTotalDaily(totalDaily2);
        TotalNutrients totalNutrients2 = new TotalNutrients();
        Daily ca22 = new Daily();
        totalNutrients2.setCa(ca22);
        ca22.setLabel("ca22");
        recipe2.setTotalNutrients(totalNutrients2);
        Images images2 = new Images();
        Image large22 = new Image();
        large22.setUrl("large2");
        images2.setLarge(large22);
        recipe2.setImages(images2);
        List<Digest> listaDigest2 = new ArrayList<>();
        Digest digest2 = new Digest();
        digest2.setLabel("digest2");
        listaDigest2.add(digest2);
        recipe2.setDigest(listaDigest2);
        List<Ingredient> listaIngr2 = new ArrayList<>();
        Ingredient ingr2 = new Ingredient();
        ingr2.setText("ingr2");
        listaIngr2.add(ingr2);
        recipe2.setIngredients(listaIngr2);
        hit2.setRecipe(recipe2);

        List<Hit> hits = new ArrayList<>();
        hits.add(hit);
        hits.add(hit2);

        item.setHits(hits);

        assertNotNull(item.getHits());
        assertEquals(2, item.getHits().size());
        Hit hit3 = item.getHits().get(1);

        assertEquals(2.0, hit3.getRecipe().getCalories(), 0.0);
        assertEquals("label2", hit3.getRecipe().getLabel());
        assertEquals("emisiones2", hit3.getRecipe().getCo2EmissionsClass());
        assertEquals("2", hit3.getRecipe().getExternalId());
        assertEquals(2.0, hit3.getRecipe().getGlycemicIndex(), 0.0);
        assertEquals("image2.png", hit3.getRecipe().getImage());
        assertEquals("compartir2", hit3.getRecipe().getShareAs());
        assertEquals("source2", hit3.getRecipe().getSource());
        assertEquals(2.0, hit3.getRecipe().getTotalCO2Emissions(), 0.0);
        assertEquals(Integer.parseInt("2"), hit3.getRecipe().getTotalTime(), 0.0);
        assertEquals(2.0, hit3.getRecipe().getTotalWeight(), 0.0);
        assertEquals("https://www.uri2.com", hit3.getRecipe().getUri());
        assertEquals("https://www.url2.com", hit3.getRecipe().getUrl());
        assertEquals(Integer.parseInt("2"), hit3.getRecipe().getYield(), 0.0);
        assertEquals("caution2", hit3.getRecipe().getCautions().get(0));
        assertEquals("cuisine2", hit3.getRecipe().getCuisineType().get(0));
        assertEquals("diet2", hit3.getRecipe().getDietLabels().get(0));
        assertEquals("dish2", hit3.getRecipe().getDishType().get(0));
        assertEquals("health2", hit3.getRecipe().getHealthLabels().get(0));
        assertEquals("ingr2", hit3.getRecipe().getIngredientLines().get(0));
        assertEquals("instr2", hit3.getRecipe().getInstructions().get(0));
        assertEquals("meal2", hit3.getRecipe().getMealType().get(0));
        assertEquals("tag2", hit3.getRecipe().getTags().get(0));
        assertEquals("ca2", hit3.getRecipe().getTotalDaily().getCa().getLabel());
        assertEquals("ca22", hit3.getRecipe().getTotalNutrients().getCa().getLabel());
        assertEquals("large2", hit3.getRecipe().getImages().getLarge().getUrl());
        assertEquals("digest2", hit3.getRecipe().getDigest().get(0).getLabel());
        assertEquals("ingr2", hit3.getRecipe().getIngredients().get(0).getText());
        assertEquals("link1", hit3.getLinks().getNext().getTitle());
        assertEquals("href1", hit3.getLinks().getNext().getHref());
    }
}