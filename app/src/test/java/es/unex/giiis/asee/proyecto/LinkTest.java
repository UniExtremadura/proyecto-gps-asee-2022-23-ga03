package es.unex.giiis.asee.proyecto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.unex.giiis.asee.proyecto.recipesmodel.Link;

public class LinkTest {

    private Link item;

    @Before
    public void init() {
        item = new Link();
        item.setHref("https://recetas.com/pescado");
        item.setTitle("Recetas pescados");
    }

    @Test
    public void getHrefTest() {
        assertEquals("https://recetas.com/pescado", item.getHref());
    }

    @Test
    public void setHrefTest() {
        assertEquals("https://recetas.com/pescado", item.getHref());
        item.setHref("https://recetas.com/carne");
        assertEquals("https://recetas.com/carne", item.getHref());
    }

    @Test
    public void getTitleTest() {
        assertEquals("Recetas pescados", item.getTitle());
    }

    @Test
    public void setTitleTest() {
        assertEquals("Recetas pescados", item.getTitle());
        item.setTitle("Recetas carne");
        assertEquals("Recetas carne", item.getTitle());
    }
}
