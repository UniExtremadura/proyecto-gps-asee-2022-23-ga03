package es.unex.giiis.asee.proyecto;

import org.junit.*;

import static org.junit.Assert.*;

import es.unex.giiis.asee.proyecto.recipesmodel.Ingredient;

public class IngredientTest {

    private Ingredient item;

    @Before
    public void init() {
        item = new Ingredient();
        item.setText("berenjena");
        item.setQuantity(10.0);
        item.setFoodCategory("cena");
        item.setImage("fotos/berenjena.png");
        item.setMeasure("mg");
        item.setWeight(35.0);
        item.setFoodId("1");
        item.setFood("comida");
    }

    @Test
    public void getTextTest() {
        assertEquals("berenjena", item.getText());
    }

    @Test
    public void setTextTest() {
        assertEquals("berenjena", item.getText());
        item.setText("berenjena1");
        assertEquals("berenjena1", item.getText());

    }

    @Test
    public void getQuantityTest() {
        assertEquals(10.0, item.getQuantity(), 0.0);
    }

    @Test
    public void setQuantityTest() {
        assertEquals(10.0, item.getQuantity(), 0.0);
        item.setQuantity(20.0);
        assertEquals(20.0, item.getQuantity(), 0.0);
    }

    @Test
    public void getMeasureTest() {
        assertEquals("mg", item.getMeasure());
    }

    @Test
    public void setMeasureTest() {
        assertEquals("mg", item.getMeasure());
        item.setMeasure("kg");
        assertEquals("kg", item.getMeasure());
    }

    @Test
    public void getFoodTest() {
        assertEquals("comida", item.getFood());
    }

    @Test
    public void setFoodTest() {
        assertEquals("comida", item.getFood());
        item.setFood("comida1");
        assertEquals("comida1", item.getFood());
    }

    @Test
    public void getWeightTest() {
        assertEquals(35.0, item.getWeight(), 0.0);
    }

    @Test
    public void setWeightTest() {
        assertEquals(35.0, item.getWeight(), 0.0);
        item.setWeight(40.0);
        assertEquals(40.0, item.getWeight(), 0.0);
    }

    @Test
    public void getFoodCategoryTest() {
        assertEquals("cena", item.getFoodCategory());
    }

    @Test
    public void setFoodCategoryTest() {
        assertEquals("cena", item.getFoodCategory());
        item.setFoodCategory("desayuno");
        assertEquals("desayuno", item.getFoodCategory());
    }

    @Test
    public void getFoodIdTest() {
        assertEquals("1", item.getFoodId());
    }

    @Test
    public void setFoodIdTest() {
        assertEquals("1", item.getFoodId());
        item.setFoodId("2");
        assertEquals("2", item.getFoodId());
    }

    @Test
    public void getImageTest() {
        assertEquals("fotos/berenjena.png", item.getImage());
    }

    @Test
    public void setImageTest() {
        assertEquals("fotos/berenjena.png", item.getImage());
        item.setImage("fotos/berenjena1.png");
        assertEquals("fotos/berenjena1.png", item.getImage());

    }


}
