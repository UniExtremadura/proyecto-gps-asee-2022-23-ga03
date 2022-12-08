package es.unex.giiis.asee.proyecto;

import org.junit.*;

import static org.junit.Assert.*;

import es.unex.giiis.asee.proyecto.recipesmodel.Image;
import es.unex.giiis.asee.proyecto.recipesmodel.Images;

public class ImagesTest {

    private Images item;

    @Before
    public void init() {
        item = new Images();
        Image large = new Image();
        Image regular = new Image();
        Image small = new Image();
        Image thumbnail = new Image();
        large.setUrl("https://www.large.com");
        large.setHeight(1);
        large.setWidth(1);
        regular.setUrl("https://www.regular.com");
        regular.setHeight(2);
        regular.setWidth(2);
        small.setUrl("https://www.small.com");
        small.setHeight(3);
        small.setWidth(3);
        thumbnail.setUrl("https://www.thumbnail.com");
        thumbnail.setHeight(4);
        thumbnail.setWidth(4);
        item.setLarge(large);
        item.setRegular(regular);
        item.setSmall(small);
        item.setThumbnail(thumbnail);

    }

    @Test
    public void getThumbnailTest() {
        assertEquals("https://www.thumbnail.com", item.getThumbnail().getUrl());
        assertEquals(4, item.getThumbnail().getHeight(), 0.0);
        assertEquals(4, item.getThumbnail().getWidth(), 0.0);
    }

    @Test
    public void setThumbnailTest() {
        assertEquals("https://www.thumbnail.com", item.getThumbnail().getUrl());
        assertEquals(4, item.getThumbnail().getHeight(), 0.0);
        assertEquals(4, item.getThumbnail().getWidth(), 0.0);
        Image thumbnail2 = new Image();
        thumbnail2.setUrl("https://www.thumbnail.com2");
        thumbnail2.setHeight(44);
        thumbnail2.setWidth(44);
        item.setThumbnail(thumbnail2);
        assertEquals("https://www.thumbnail.com2", item.getThumbnail().getUrl());
        assertEquals(44, item.getThumbnail().getHeight(), 0.0);
        assertEquals(44, item.getThumbnail().getWidth(), 0.0);

    }

    @Test
    public void getSmallTest() {
        assertEquals("https://www.small.com", item.getSmall().getUrl());
        assertEquals(3, item.getSmall().getHeight(), 0.0);
        assertEquals(3, item.getSmall().getWidth(), 0.0);
    }

    @Test
    public void setSmallTest() {
        assertEquals("https://www.small.com", item.getSmall().getUrl());
        assertEquals(3, item.getSmall().getHeight(), 0.0);
        assertEquals(3, item.getSmall().getWidth(), 0.0);
        Image small2 = new Image();
        small2.setUrl("https://www.small.com2");
        small2.setHeight(33);
        small2.setWidth(33);
        item.setSmall(small2);
        assertEquals("https://www.small.com2", item.getSmall().getUrl());
        assertEquals(33, item.getSmall().getHeight(), 0.0);
        assertEquals(33, item.getSmall().getWidth(), 0.0);

    }

    @Test
    public void getRegularTest() {
        assertEquals("https://www.regular.com", item.getRegular().getUrl());
        assertEquals(2, item.getRegular().getHeight(), 0.0);
        assertEquals(2, item.getRegular().getWidth(), 0.0);
    }

    @Test
    public void setRegularTest() {
        assertEquals("https://www.regular.com", item.getRegular().getUrl());
        assertEquals(2, item.getRegular().getHeight(), 0.0);
        assertEquals(2, item.getRegular().getWidth(), 0.0);
        Image regular2 = new Image();
        regular2.setUrl("https://www.regular.com2");
        regular2.setHeight(22);
        regular2.setWidth(22);
        item.setRegular(regular2);
        assertEquals("https://www.regular.com2", item.getRegular().getUrl());
        assertEquals(22, item.getRegular().getHeight(), 0.0);
        assertEquals(22, item.getRegular().getWidth(), 0.0);

    }

    @Test
    public void getLargeTest() {
        assertEquals("https://www.large.com", item.getLarge().getUrl());
        assertEquals(1, item.getLarge().getHeight(), 0.0);
        assertEquals(1, item.getLarge().getWidth(), 0.0);
    }

    @Test
    public void setLargeTest() {
        assertEquals("https://www.large.com", item.getLarge().getUrl());
        assertEquals(1, item.getLarge().getHeight(), 0.0);
        assertEquals(1, item.getLarge().getWidth(), 0.0);
        Image large2 = new Image();
        large2.setUrl("https://www.large.com2");
        large2.setHeight(11);
        large2.setWidth(11);
        item.setLarge(large2);
        assertEquals("https://www.large.com2", item.getLarge().getUrl());
        assertEquals(11, item.getLarge().getHeight(), 0.0);
        assertEquals(11, item.getLarge().getWidth(), 0.0);
    }
}
