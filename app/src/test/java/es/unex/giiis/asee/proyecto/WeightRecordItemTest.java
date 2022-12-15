package es.unex.giiis.asee.proyecto;

import android.content.Intent;


import android.content.Intent;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;

@RunWith(MockitoJUnitRunner.class)
public class WeightRecordItemTest {

    private WeightRecordItem item;
    @Mock
    private Intent intent;

    @Before
    public void init() {
        item = new WeightRecordItem(10, 1, 64.5, new Date(10));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void WeightRecordItemTest1() {
        WeightRecordItem item1 = new WeightRecordItem(15, 22.5, new Date(10));
        assertEquals(15, item1.getUserid());
        assertEquals(22.5, item1.getWeight(), 0.0);
        Date fecha = new Date(10);
        assertEquals(fecha, item1.getDate());
    }
    @Test
    public void WeightRecordItemTest2() {
        WeightRecordItem item2 = new WeightRecordItem(1,16, 20.5, new Date(11));
        assertEquals(1, item2.getId());
        assertEquals(16, item2.getUserid());
        assertEquals(20.5, item2.getWeight(), 0.0);
        Date fecha = new Date(11);
        assertEquals(fecha, item2.getDate());
    }

    @Test
    public void WeightRecordItemTest3() {
        WeightRecordItem item3 = new WeightRecordItem(9,69, 26.3,"10/10/2010" );
        assertEquals(9, item3.getId());
        assertEquals(69, item3.getUserid());
        assertEquals(26.3, item3.getWeight(), 0.0);
        String fecha = "10/10/2010";
        Date date = new Date();
        try {
            date = WeightRecordItem.FORMAT.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(date, item3.getDate());

    }

    @Test
    public void WeightRecordItemTest4() {

        when(intent. getLongExtra("ID_ATTR",0)).thenReturn(0L);
        when(intent. getLongExtra("userid",0)).thenReturn(1L);
        when(intent.getDoubleExtra("weight",0.0)).thenReturn(64.5D);
        when(intent.getStringExtra("date")).thenReturn("10/10/2010");

        WeightRecordItem.packageIntent(intent, item.getId(), item.getUserid(),
                item.getWeight(), WeightRecordItem.FORMAT.format(item.getDate()));
        WeightRecordItem weightRecordItem = new WeightRecordItem(intent);
        assertEquals(0, weightRecordItem.getId());
        assertEquals(1, weightRecordItem.getUserid());
        assertEquals(64.5, weightRecordItem.getWeight(), 0.0);
        String fecha = "10/10/2010";
        Date date = new Date(10);
        try {
            date = WeightRecordItem.FORMAT.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(date, item.getDate());


    }
    @Test
    public void  getIdTest() {
        assertEquals(10, item.getId());
    }
    @Test
    public void  setIdTest() {
        assertEquals(10, item.getId());
        item.setId(15);
        assertEquals(15, item.getId());
    }
    @Test
    public void getUseridTest() {
        assertEquals(1, item.getUserid());
    }
    @Test
    public void setUseridTest() {
        assertEquals(1, item.getUserid());
        item.setUserid(12);
        assertEquals(12, item.getUserid());
    }
    @Test
    public void getWeightTest() {
        assertEquals(64.5 , item.getWeight(), 0.0);

    }
    @Test
    public void setWeightTest( ) {
        assertEquals(64.5 , item.getWeight(), 0.0);
        item.setWeight(12.5);
        assertEquals(12.5 , item.getWeight(), 0.0);
    }
    @Test
    public void getDateTest() {
        Date fecha = new Date(10);
        assertEquals(fecha, item.getDate());

    }
    @Test
    public void setDateTest() {
        Date fecha = new Date(10);
        assertEquals(fecha, item.getDate());
        Date fechaAct = new Date(12);
        item.setDate(fechaAct);
        assertEquals(fechaAct, item.getDate());
    }


    @Test
    public void packageIntentTest() {
        when(intent. getLongExtra("ID_ATTR",0)).thenReturn(item.getId());
        when(intent. getLongExtra("userid",0)).thenReturn(item.getUserid());
        when(intent.getDoubleExtra("weight",0.0)).thenReturn(item.getWeight());
        when(intent.getStringExtra("date")).thenReturn(WeightRecordItem.FORMAT.format(item.getDate()));


        WeightRecordItem.packageIntent(intent, item.getId(), item.getUserid(),
                item.getWeight(), WeightRecordItem.FORMAT.format(item.getDate()));
        WeightRecordItem weightRecordItem = new WeightRecordItem(intent);
        assertEquals(item.getId(), weightRecordItem.getId());
        assertEquals(item.getUserid(), weightRecordItem.getUserid());
        assertEquals(item.getWeight(), weightRecordItem.getWeight(), 0.0);
        String fecha = "10/10/2010";
        Date date = new Date(10);
        try {
            date = WeightRecordItem.FORMAT.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(date, item.getDate());


    }
    @Test
    public void toStringTest() {
        assertEquals( item.getId()+ System.getProperty("line.separator") + item.getUserid()+ System.getProperty("line.separator")+ item.getWeight()+ System.getProperty("line.separator")+ new SimpleDateFormat(
                "dd-MM-yyyy", Locale.US).format(item.getDate()) ,item.toString());



    }
    @Test
    public void toLogTest() {
        assertEquals("ID_ATTR: " + item.getId()+ System.getProperty("line.separator")+ "Userid:" + item.getUserid()+ System.getProperty("line.separator")+ "Weight:" + item.getWeight()+ System.getProperty("line.separator")+ "Date:" + new SimpleDateFormat(
                "dd-MM-yyyy", Locale.US).format(item.getDate()) ,item.toLog());

    }
}