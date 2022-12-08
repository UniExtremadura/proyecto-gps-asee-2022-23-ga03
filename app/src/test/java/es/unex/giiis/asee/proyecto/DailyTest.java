package es.unex.giiis.asee.proyecto;

import es.unex.giiis.asee.proyecto.recipesmodel.Daily;

import org.junit.*;

import static org.junit.Assert.*;

public class DailyTest {

    private Daily item;

    @Before
    public void init() {
        item = new Daily();
        item.setLabel("daily1");
        item.setQuantity(10.0);
        item.setUnit("kg");
    }


    @Test
    public void getLabelTest() {
        assertEquals("daily1", item.getLabel());
    }

    @Test
    public void setLabelTest() {
        assertEquals("daily1", item.getLabel());
        item.setLabel("daily2");
        assertEquals("daily2", item.getLabel());
    }

    @Test
    public void getQuantityTest() {
        assertEquals(10.0, item.getQuantity(), 0.0);
    }

    @Test
    public void setQuantityTest() {
        assertEquals(10, item.getQuantity(), 0.0);
        item.setQuantity(20.0);
        assertEquals(20.0, item.getQuantity(), 0.0);
    }

    @Test
    public void getUnitTest() {
        assertEquals("kg", item.getUnit());
    }

    @Test
    public void setUnitTest() {
        assertEquals("kg", item.getUnit());
        item.setUnit("mg");
        assertEquals("mg", item.getUnit());
    }


}
