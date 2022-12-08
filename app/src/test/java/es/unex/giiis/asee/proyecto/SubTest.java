package es.unex.giiis.asee.proyecto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.unex.giiis.asee.proyecto.recipesmodel.Sub;

public class SubTest {

    private Sub item;

    @Before
    public void init() {
        item = new Sub();
        item.setLabel("label");
        item.setTag("tag");
        item.setSchemaOrgTag("schema");
        item.setTotal(100.0);
        item.setHasRDI(false);
        item.setDaily(10.0);
    }


    @Test
    public void getLabelTest() {
        assertEquals("label", item.getLabel());

    }

    @Test
    public void setLabelTest() {
        assertEquals("label", item.getLabel());
        item.setLabel("label2");
        assertEquals("label2", item.getLabel());


    }
    @Test
    public void getTagTest() {
        assertEquals("tag", item.getTag());

    }

    @Test
    public void setTagTest() {
        assertEquals("tag", item.getTag());
        item.setTag("tag2");
        assertEquals("tag2", item.getTag());


    }

    @Test
    public void getSchemaOrgTagTest() {
        assertEquals("schema", item.getSchemaOrgTag());

    }

    @Test
    public void setSchemaOrgTagTest() {
        assertEquals("schema", item.getSchemaOrgTag());
        item.setSchemaOrgTag("tag");
        assertEquals("tag", item.getSchemaOrgTag());

    }

    @Test
    public void getTotalTest() {
        assertEquals(100.0, item.getTotal(), 0.0);

    }

    @Test
    public void setTotalTest() {
        assertEquals(100.0, item.getTotal(), 0.0);
        item.setTotal(200.0);
        assertEquals(200.0, item.getTotal(), 0.0);

    }

    @Test
    public void getHasRDITest() {
        assertEquals(false, item.getHasRDI());

    }

    @Test
    public void setHasRDITest() {
        assertEquals(false, item.getHasRDI());
        item.setHasRDI(true);
        assertEquals(true, item.getHasRDI());

    }

    @Test
    public void getDailyTest() {
        assertEquals(10.0, item.getDaily(), 0.0);

    }

    @Test
    public void setDailyTest() {
        assertEquals(10.0, item.getDaily(), 0.0);
        item.setDaily(20.0);
        assertEquals(20.0, item.getDaily(), 0.0);

    }

}
