package es.unex.giiis.asee.proyecto;

import android.content.Intent;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import es.unex.giiis.asee.proyecto.ui.recetas.FavoriteRecipeItem;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteRecipeItemTest {


    private FavoriteRecipeItem item;

    @Mock
    private Intent intent;

    @Before
    public void init() {
        item = new FavoriteRecipeItem(1, "cachopo", 200.0, "q!",
                "https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", 3);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void FavoriteRecipeItemTest1() {
        FavoriteRecipeItem favoriteRecipeItem = new FavoriteRecipeItem("torrijas", 150.0, "torr!", "https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", 4);
        assertEquals("torrijas", favoriteRecipeItem.getTitle());
        assertEquals(150.0, favoriteRecipeItem.getCalories(), 0.0);
        assertEquals("torr!", favoriteRecipeItem.getWebid());
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", favoriteRecipeItem.getImageurl());
        assertEquals(4, favoriteRecipeItem.getUserid());


    }

    @Test
    public void FavoriteRecipeItemTest2() {
        FavoriteRecipeItem favoriteRecipeItem = new FavoriteRecipeItem(21, "torrijas", 150.0, "torr!", "https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", 4);
        assertEquals("torrijas", favoriteRecipeItem.getTitle());
        assertEquals(150.0, favoriteRecipeItem.getCalories(), 0.0);
        assertEquals("torr!", favoriteRecipeItem.getWebid());
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", favoriteRecipeItem.getImageurl());
        assertEquals(4, favoriteRecipeItem.getUserid());
        assertEquals(21, favoriteRecipeItem.getId());

    }

    @Test
    public void FavoriteRecipeItemTest3() {
        when(intent.getStringExtra("title")).thenReturn("torrijas");
        when(intent.getDoubleExtra("calories", 0.0)).thenReturn(150.0);
        when(intent.getStringExtra("webid")).thenReturn("torr!");
        when(intent.getStringExtra("imageurl")).thenReturn("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg");
        when(intent.getLongExtra("userid", 0)).thenReturn(4L);
        FavoriteRecipeItem.packageIntent(intent, item.getId(), item.getTitle(), item.getCalories(),
                item.getWebid(), item.getImageurl(), item.getUserid());
        FavoriteRecipeItem favoriteRecipeItem = new FavoriteRecipeItem(intent);
        assertEquals("torrijas", favoriteRecipeItem.getTitle());
        assertEquals(150.0, favoriteRecipeItem.getCalories(), 0.0);
        assertEquals("torr!", favoriteRecipeItem.getWebid());
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", favoriteRecipeItem.getImageurl());
        assertEquals(4, favoriteRecipeItem.getUserid());

    }

    @Test
    public void getIdTest() {
        assertEquals(1, item.getId());

    }

    @Test
    public void setIdTest() {
        assertEquals(1, item.getId());
        item.setId(2);
        assertEquals(2, item.getId());

    }

    @Test
    public void getTitleTest() {
        assertEquals("cachopo", item.getTitle());

    }

    @Test
    public void setTitleTest() {
        assertEquals("cachopo", item.getTitle());
        item.setTitle("gambas de huelva");
        assertEquals("gambas de huelva", item.getTitle());
    }

    @Test
    public void getCaloriesTest() {
        assertEquals(200.0, item.getCalories(), 0.0);

    }

    @Test
    public void setCaloriesTest() {
        assertEquals(200.0, item.getCalories(), 0.0);
        item.setCalories(1000.0);
        assertEquals(1000.0, item.getCalories(), 0.0);

    }

    @Test
    public void getWebidTest() {
        assertEquals("q!", item.getWebid());

    }

    @Test
    public void setWebidTest() {
        assertEquals("q!", item.getWebid());
        item.setWebid("q!6");
        assertEquals("q!6", item.getWebid());

    }

    @Test
    public void getImageurlTest() {
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", item.getImageurl());

    }

    @Test
    public void setImageurlTest() {
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", item.getImageurl());
        item.setImageurl("https://www.nutrifit.com/fotos/Torrijas-1-1.jpg");
        assertEquals("https://www.nutrifit.com/fotos/Torrijas-1-1.jpg", item.getImageurl());


    }

    @Test
    public void getPlantillaidTest() {
        assertEquals(3, item.getUserid());

    }

    @Test
    public void setPlantillaidTest() {
        assertEquals(3, item.getUserid());
        item.setUserid(4);
        assertEquals(4, item.getUserid());


    }

    @Test
    public void packageIntentTest() {
        when(intent.getStringExtra("title")).thenReturn(item.getTitle());
        when(intent.getDoubleExtra("calories", 0.0)).thenReturn(item.getCalories());
        when(intent.getStringExtra("webid")).thenReturn(item.getWebid());
        when(intent.getStringExtra("imageurl")).thenReturn(item.getImageurl());
        when(intent.getLongExtra("userid", 0)).thenReturn(item.getUserid());
        FavoriteRecipeItem.packageIntent(intent, item.getId(), item.getTitle(), item.getCalories(),
                item.getWebid(), item.getImageurl(), item.getUserid());
        FavoriteRecipeItem favoriteRecipeItem = new FavoriteRecipeItem(intent);
        assertEquals(item.getTitle(), favoriteRecipeItem.getTitle());
        assertEquals(item.getCalories(), favoriteRecipeItem.getCalories());
        assertEquals(item.getWebid(), favoriteRecipeItem.getWebid());
        assertEquals(item.getImageurl(), favoriteRecipeItem.getImageurl());
        assertEquals(item.getUserid(), favoriteRecipeItem.getUserid());
    }

    @Test
    public void toStringTest() {
        assertEquals(item.getId() + System.getProperty("line.separator") + item.getUserid() + System.getProperty("line.separator")
                + item.getTitle() + System.getProperty("line.separator") + item.getCalories() + System.getProperty("line.separator") +
                item.getWebid() + System.getProperty("line.separator") + item.getImageurl(), item.toString());
    }

    @Test
    public void toLogTest() {
        assertEquals("ID: " + item.getId() + System.getProperty("line.separator") + "Userid:" + item.getUserid() + System.getProperty("line.separator")
                + "Title:" + item.getTitle() + System.getProperty("line.separator") + "Calories:" + item.getCalories() + System.getProperty("line.separator") +
                "Webid:" + item.getWebid() + System.getProperty("line.separator") + "Imageurl:" + item.getImageurl(), item.toLog());

    }
}