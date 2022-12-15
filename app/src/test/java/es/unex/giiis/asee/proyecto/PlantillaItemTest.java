package es.unex.giiis.asee.proyecto;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import android.content.Intent;

import androidx.room.Ignore;

import es.unex.giiis.asee.proyecto.ui.horario.PlantillaItem;

@RunWith(MockitoJUnitRunner.class)
public class PlantillaItemTest {

    private PlantillaItem item;

    @Mock
    private Intent intent;

    @Before
    public void init() {
        item = new PlantillaItem(1, "plantilla", "HIGH", "THURSDAY", 7);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void PlantillaItem1Test() {
        PlantillaItem plantillaItem = new PlantillaItem( "plantilla2", PlantillaItem.Priority.LOW, PlantillaItem.Day.MONDAY, 9);
        assertEquals("plantilla2", plantillaItem.getTitle());
        assertEquals(PlantillaItem.Priority.LOW, plantillaItem.getPriority());
        assertEquals(PlantillaItem.Day.MONDAY, plantillaItem.getDay());
        assertEquals(9, plantillaItem.getUserid());

    }

    @Test
    public void PlantillaItem2Test() {
        PlantillaItem plantillaItem = new PlantillaItem(3, "plantilla3", PlantillaItem.Priority.LOW, PlantillaItem.Day.MONDAY, 9);
        assertEquals(3, plantillaItem.getId());
        assertEquals("plantilla3", plantillaItem.getTitle());
        assertEquals(PlantillaItem.Priority.LOW, plantillaItem.getPriority());
        assertEquals(PlantillaItem.Day.MONDAY, plantillaItem.getDay());
        assertEquals(9, plantillaItem.getUserid());


    }

    @Test
    public void PlantillaItem3Test() {
        PlantillaItem plantillaItem = new PlantillaItem(3, "plantilla3", "LOW", "MONDAY", 9);
        assertEquals(3, plantillaItem.getId());
        assertEquals("plantilla3", plantillaItem.getTitle());
        assertEquals("LOW", String.valueOf(plantillaItem.getPriority()));
        assertEquals("MONDAY", String.valueOf(plantillaItem.getDay()));
        assertEquals(9, plantillaItem.getUserid());

    }

    @Test
    public void PlantillaItem4Test() {
        when(intent.getLongExtra("ID_ATTR", 0)).thenReturn(10L);
        when(intent.getStringExtra("title")).thenReturn("plantilla4");
        when(intent.getStringExtra("priority")).thenReturn("HIGH");
        when(intent.getStringExtra("day")).thenReturn("MONDAY");
        when(intent.getLongExtra("userid", 0)).thenReturn(12L);
        PlantillaItem.packageIntent(intent, item.getId(), item.getTitle(), item.getPriority(), item.getDay(), item.getUserid());
        PlantillaItem plantillaItem = new PlantillaItem(intent);
        assertEquals(10, plantillaItem.getId());
        assertEquals("plantilla4", plantillaItem.getTitle());
        assertEquals("HIGH", String.valueOf(plantillaItem.getPriority()));
        assertEquals("MONDAY", String.valueOf(plantillaItem.getDay()));
        assertEquals(12, plantillaItem.getUserid());

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
        assertEquals("plantilla", item.getTitle());
    }

    @Test
    public void setTitleTest() {
        assertEquals("plantilla", item.getTitle());
        item.setTitle("plantilla2");
        assertEquals("plantilla2", item.getTitle());

    }

    @Test
    public void getPriorityTest() {
        assertEquals("HIGH", String.valueOf(item.getPriority()));

    }

    @Test
    public void setPriorityTest() {
        assertEquals("HIGH", String.valueOf(item.getPriority()));
        item.setPriority(PlantillaItem.Priority.LOW);
        assertEquals("LOW", String.valueOf(item.getPriority()));

    }

    @Test
    public void getDayTest() {
        assertEquals("THURSDAY", String.valueOf(item.getDay()));

    }

    @Test
    public void setStatusTest() {
        assertEquals("THURSDAY", String.valueOf(item.getDay()));
        item.setStatus(PlantillaItem.Day.TUESDAY);
        assertEquals("TUESDAY", String.valueOf(item.getDay()));


    }

    @Test
    public void getUseridTest() {
        assertEquals(7, item.getUserid());
    }

    @Test
    public void setUseridTest() {
        assertEquals(7, item.getUserid());
        item.setUserid(8);
        assertEquals(8, item.getUserid());

    }

    @Test
    public void packageIntentTest() {
        when(intent.getLongExtra("ID_ATTR", 0)).thenReturn(item.getId());
        when(intent.getStringExtra("title")).thenReturn(item.getTitle());
        when(intent.getStringExtra("priority")).thenReturn(String.valueOf(item.getPriority()));
        when(intent.getStringExtra("day")).thenReturn(String.valueOf(item.getDay()));
        when(intent.getLongExtra("userid", 0)).thenReturn(item.getUserid());
        PlantillaItem.packageIntent(intent, item.getId(), item.getTitle(), item.getPriority(), item.getDay(), item.getUserid());
        PlantillaItem plantillaItem = new PlantillaItem(intent);
        assertEquals(item.getId(), plantillaItem.getId());
        assertEquals(item.getTitle(), plantillaItem.getTitle());
        assertEquals(String.valueOf(item.getPriority()), String.valueOf(plantillaItem.getPriority()));
        assertEquals(String.valueOf(item.getDay()), String.valueOf(plantillaItem.getDay()));
        assertEquals(item.getUserid(), plantillaItem.getUserid());


    }

    @Test
    public void toStringTest() {
        assertEquals(item.getId() + System.getProperty("line.separator") + item.getUserid() + System.getProperty("line.separator")
                + item.getTitle() + System.getProperty("line.separator") + item.getPriority() + System.getProperty("line.separator") +
                item.getDay(), item.toString());

    }

    @Test
    public void toLogTest() {
        assertEquals("ID_ATTR: " + item.getId() + System.getProperty("line.separator") + "Userid:" + item.getUserid() + System.getProperty("line.separator")
                + "Title:" + item.getTitle() + System.getProperty("line.separator") + "Priority:" + item.getPriority() + System.getProperty("line.separator") +
                "Day:" + item.getDay(), item.toLog());

    }
}
