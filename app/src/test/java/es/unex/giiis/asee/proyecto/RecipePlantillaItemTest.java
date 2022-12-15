package es.unex.giiis.asee.proyecto;

import android.content.Context;
import android.content.Intent;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import es.unex.giiis.asee.proyecto.ui.horario.RecipePlantillaItem;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import androidx.room.Ignore;

@RunWith(MockitoJUnitRunner.class)
public class RecipePlantillaItemTest {

    private RecipePlantillaItem item;

    @Mock
    private Intent intent;

    @Before
    public void init() {
        item = new RecipePlantillaItem(1, "cachopo", RecipePlantillaItem.Period.LUNCH, 200.0, "q!",
                "https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", 3);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void RecipePlantillaItemTest1() {
        RecipePlantillaItem recipePlantillaItem = new RecipePlantillaItem("torrijas", RecipePlantillaItem.Period.DINNER, 150.0, "torr!", "https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", 4);
        assertEquals("torrijas", recipePlantillaItem.getTitle());
        assertEquals(150.0, recipePlantillaItem.getCalories(), 0.0);
        assertEquals("torr!", recipePlantillaItem.getWebid());
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", recipePlantillaItem.getImageurl());
        assertEquals(RecipePlantillaItem.Period.DINNER, recipePlantillaItem.getPeriod());
        assertEquals(4, recipePlantillaItem.getPlantillaid());


    }

    @Test
    public void RecipePlantillaItemTest2() {
        RecipePlantillaItem recipePlantillaItem = new RecipePlantillaItem(21, "torrijas", RecipePlantillaItem.Period.DINNER, 150.0, "torr!", "https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", 4);
        assertEquals("torrijas", recipePlantillaItem.getTitle());
        assertEquals(150.0, recipePlantillaItem.getCalories(), 0.0);
        assertEquals("torr!", recipePlantillaItem.getWebid());
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", recipePlantillaItem.getImageurl());
        assertEquals(RecipePlantillaItem.Period.DINNER, recipePlantillaItem.getPeriod());
        assertEquals(4, recipePlantillaItem.getPlantillaid());
        assertEquals(21, recipePlantillaItem.getId());

    }

    @Test
    public void RecipePlantillaItemTest3() {
        RecipePlantillaItem recipePlantillaItem = new RecipePlantillaItem(21, "torrijas", "DINNER", 150.0, "torr!", "https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", 4);
        assertEquals("torrijas", recipePlantillaItem.getTitle());
        assertEquals(150.0, recipePlantillaItem.getCalories(), 0.0);
        assertEquals("torr!", recipePlantillaItem.getWebid());
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", recipePlantillaItem.getImageurl());
        assertEquals(RecipePlantillaItem.Period.DINNER, recipePlantillaItem.getPeriod());
        assertEquals(4, recipePlantillaItem.getPlantillaid());
        assertEquals(21, recipePlantillaItem.getId());

    }

    @Test
    public void RecipePlantillaItemTest4() {
        when(intent.getStringExtra("title")).thenReturn("torrijas");
        when(intent.getDoubleExtra("calories", 0.0)).thenReturn(150.0);
        when(intent.getStringExtra("webid")).thenReturn("torr!");
        when(intent.getStringExtra("imageurl")).thenReturn("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg");
        when(intent.getLongExtra("plantillaid", 0)).thenReturn(4L);
        when(intent.getStringExtra("period")).thenReturn("DINNER");
        RecipePlantillaItem.packageIntent(intent, item.getTitle(), item.getCalories(),
                item.getWebid(), item.getImageurl(), item.getPeriod(), item.getPlantillaid());
        RecipePlantillaItem recipePlantillaItem = new RecipePlantillaItem(intent);
        assertEquals("torrijas", recipePlantillaItem.getTitle());
        assertEquals(150.0, recipePlantillaItem.getCalories(), 0.0);
        assertEquals("torr!", recipePlantillaItem.getWebid());
        assertEquals("https://www.nutrifit.com/fotos/Cachopo-1-1.jpg", recipePlantillaItem.getImageurl());
        assertEquals(RecipePlantillaItem.Period.DINNER, recipePlantillaItem.getPeriod());
        assertEquals(4, recipePlantillaItem.getPlantillaid());

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
    public void getPeriodTest() {
        assertEquals(RecipePlantillaItem.Period.LUNCH, item.getPeriod());

    }

    @Test
    public void setPeriodTest() {
        assertEquals(RecipePlantillaItem.Period.LUNCH, item.getPeriod());
        item.setPeriod(RecipePlantillaItem.Period.DINNER);
        assertEquals(RecipePlantillaItem.Period.DINNER, item.getPeriod());

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
        assertEquals(3, item.getPlantillaid());

    }

    @Test
    public void setPlantillaidTest() {
        assertEquals(3, item.getPlantillaid());
        item.setPlantillaid(4);
        assertEquals(4, item.getPlantillaid());


    }

    @Test
    public void packageIntentTest() {
        when(intent.getStringExtra("title")).thenReturn(item.getTitle());
        when(intent.getDoubleExtra("calories", 0.0)).thenReturn(item.getCalories());
        when(intent.getStringExtra("webid")).thenReturn(item.getWebid());
        when(intent.getStringExtra("imageurl")).thenReturn(item.getImageurl());
        when(intent.getLongExtra("plantillaid", 0)).thenReturn(item.getPlantillaid());
        when(intent.getStringExtra("period")).thenReturn(String.valueOf(item.getPeriod()));
        RecipePlantillaItem.packageIntent(intent, item.getTitle(), item.getCalories(),
                item.getWebid(), item.getImageurl(), item.getPeriod(), item.getPlantillaid());
        RecipePlantillaItem recipePlantillaItem = new RecipePlantillaItem(intent);
        assertEquals(item.getTitle(), recipePlantillaItem.getTitle());
        assertEquals(item.getCalories(), recipePlantillaItem.getCalories());
        assertEquals(item.getWebid(), recipePlantillaItem.getWebid());
        assertEquals(item.getImageurl(), recipePlantillaItem.getImageurl());
        assertEquals(item.getPeriod(), recipePlantillaItem.getPeriod());
        assertEquals(item.getPlantillaid(), recipePlantillaItem.getPlantillaid());

    }

    @Test
    public void toStringTest() {
        assertEquals(item.getId() + System.getProperty("line.separator") + item.getPlantillaid() + System.getProperty("line.separator")
                + item.getTitle() + System.getProperty("line.separator") + item.getCalories() + System.getProperty("line.separator") +
                item.getWebid() + System.getProperty("line.separator") + item.getImageurl(), item.toString());
    }

    @Test
    public void toLogTest() {
        assertEquals("ID_ATTR: " + item.getId() + System.getProperty("line.separator") + "Plantillaid:" + item.getPlantillaid() + System.getProperty("line.separator")
                + "Title:" + item.getTitle() + System.getProperty("line.separator") + "Calories:" + item.getCalories() + System.getProperty("line.separator") +
                "Webid:" + item.getWebid() + System.getProperty("line.separator") + "Imageurl:" + item.getImageurl(), item.toLog());

    }

}
