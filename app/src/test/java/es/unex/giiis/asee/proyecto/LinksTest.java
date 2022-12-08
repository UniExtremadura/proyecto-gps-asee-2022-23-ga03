package es.unex.giiis.asee.proyecto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.unex.giiis.asee.proyecto.recipesmodel.Link;
import es.unex.giiis.asee.proyecto.recipesmodel.Links;

public class LinksTest {

    private Links item;

    @Before
    public void init() {
        item = new Links();
        Link link1 = new Link();
        link1.setHref("https://recetas.com/pescado");
        link1.setTitle("Recetas pescados");

        item.setNext(link1);
    }

    @Test
    public void getNextTest() {
        assertEquals("https://recetas.com/pescado", item.getNext().getHref());
        assertEquals("Recetas pescados", item.getNext().getTitle());
    }

    @Test
    public void setNextTest() {
        assertEquals("https://recetas.com/pescado", item.getNext().getHref());
        assertEquals("Recetas pescados", item.getNext().getTitle());

        Link link1 = new Link();
        link1.setHref("https://recetas.com/carne");
        link1.setTitle("Recetas carne");
        item.setNext(link1);

        assertEquals("https://recetas.com/carne", item.getNext().getHref());
        assertEquals("Recetas carne", item.getNext().getTitle());
    }
}
