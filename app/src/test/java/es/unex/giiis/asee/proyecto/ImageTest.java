package es.unex.giiis.asee.proyecto;

import org.junit.*;

import static org.junit.Assert.*;

import es.unex.giiis.asee.proyecto.recipesmodel.Image;

public class ImageTest {

    private Image item;

    @Before
    public void init() {
        item = new Image();
        item.setHeight(3);
        item.setUrl("https://www.apruebanosPorFavor.com");
        item.setWidth(5);
    }

    @Test
    public void getUrlTest() {
        assertEquals("https://www.apruebanosPorFavor.com", item.getUrl());

    }

    @Test
    public void setUrlTest() {
        assertEquals("https://www.apruebanosPorFavor.com", item.getUrl());
        item.setUrl("https://www.vivaGPS.com");
        assertEquals("https://www.vivaGPS.com", item.getUrl());


    }

    @Test
    public void getWidthTest() {
        assertEquals(5, item.getWidth(), 0.0);


    }

    @Test
    public void setWidthTest() {
        assertEquals(5, item.getWidth(), 0.0);
        item.setWidth(6);
        assertEquals(6, item.getWidth(), 0.0);

    }

    @Test
    public void getHeightTest() {
        assertEquals(3, item.getHeight(), 0.0);

    }

    @Test
    public void setHeightTest() {
        assertEquals(3, item.getHeight(), 0.0);
        item.setHeight(5);
        assertEquals(5, item.getHeight(), 0.0);

    }
}
