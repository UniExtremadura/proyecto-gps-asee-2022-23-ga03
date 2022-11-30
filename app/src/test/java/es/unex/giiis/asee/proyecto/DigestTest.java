package es.unex.giiis.asee.proyecto;

import es.unex.giiis.asee.proyecto.recipesmodel.Digest;
import es.unex.giiis.asee.proyecto.recipesmodel.Sub;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class DigestTest {

    private Digest item;

    @Before
    public void init() {
        item = new Digest();
        List<Sub> listaSub = new ArrayList<>();
        Sub sub = new Sub();
        sub.setLabel("labelSub");
        sub.setTag("tagSub");
        sub.setSchemaOrgTag("schema");
        sub.setTotal(100.0);
        sub.setHasRDI(false);
        sub.setDaily(10.0);
        listaSub.add(sub);
        item.setLabel("label");
        item.setTag("tag");
        Object object = new Object();
        item.setSchemaOrgTag(object);
        item.setTotal(100.0);
        item.setHasRDI(false);
        item.setDaily(10.0);
        item.setSub(listaSub);
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
        assertEquals(Object.class, item.getSchemaOrgTag().getClass());

    }

    @Test
    public void setSchemaOrgTagTest() {
        assertEquals(Object.class, item.getSchemaOrgTag().getClass());
        item.setSchemaOrgTag(new Object());
        assertEquals(Object.class, item.getSchemaOrgTag().getClass());

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

    @Test
    public void getSubTest() {
        assertEquals(1, item.getSub().size());
        assertEquals("labelSub", item.getSub().get(0).getLabel());
        assertEquals("tagSub", item.getSub().get(0).getTag());
        assertEquals("schema", item.getSub().get(0).getSchemaOrgTag());
        assertEquals(100.0, item.getSub().get(0).getTotal(), 0.0);
        assertEquals(false, item.getSub().get(0).getHasRDI());
        assertEquals(10.0, item.getSub().get(0).getDaily(), 0.0);

    }

    @Test
    public void setSubTest() {
        assertEquals(1, item.getSub().size());
        assertEquals("labelSub", item.getSub().get(0).getLabel());
        assertEquals("tagSub", item.getSub().get(0).getTag());
        assertEquals("schema", item.getSub().get(0).getSchemaOrgTag());
        assertEquals(100.0, item.getSub().get(0).getTotal(), 0.0);
        assertEquals(false, item.getSub().get(0).getHasRDI());
        assertEquals(10.0, item.getSub().get(0).getDaily(), 0.0);
        List<Sub> lista = new ArrayList<>();
        Sub sub1 = new Sub();
        sub1.setLabel("labelSub1");
        sub1.setTag("tagSub1");
        sub1.setSchemaOrgTag("schema1");
        sub1.setTotal(300.0);
        sub1.setHasRDI(true);
        sub1.setDaily(30.0);
        lista.add(sub1);
        item.setSub(lista);
        assertEquals(1, item.getSub().size());
        assertEquals("labelSub1", item.getSub().get(0).getLabel());
        assertEquals("tagSub1", item.getSub().get(0).getTag());
        assertEquals("schema1", item.getSub().get(0).getSchemaOrgTag());
        assertEquals(300.0, item.getSub().get(0).getTotal(), 0.0);
        assertEquals(true, item.getSub().get(0).getHasRDI());
        assertEquals(30.0, item.getSub().get(0).getDaily(), 0.0);



    }
}
