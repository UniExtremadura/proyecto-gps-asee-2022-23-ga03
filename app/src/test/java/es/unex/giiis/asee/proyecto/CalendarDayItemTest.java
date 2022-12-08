package es.unex.giiis.asee.proyecto;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem.FORMAT;

import org.mockito.*;
import android.content.Intent;

import java.util.Date;

import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

public class CalendarDayItemTest {
    private CalendarDayItem item = null;

    @Mock
    private Intent intent;

    /**
     * Método 'Before' que inicializa el objeto general de la clase.
     */

    @Before
    public void beforeMethod () {
        item = new CalendarDayItem (
                0L,
                "default_title",
                "default_webid",
                0L,
                CalendarDayItem.Status.NOTDONE,
                "01/01/3000",
                new Date (10),
                "default_type"
        );
        MockitoAnnotations.initMocks (this);
    }


    /**
     * Prueba el constructor parametrizado de la clase que no incluye su identificador.
     */

    @Test
    public void ConstructorTest1 () {
        CalendarDayItem local_item = new CalendarDayItem (
                "default_title",
                "default_webid",
                CalendarDayItem.Status.NOTDONE,
                "01/01/3000",
                new Date (10),
                0L,
                "default_type"
        );
        assertNotEquals (local_item, null);
    }

    /**
     * Prueba el constructor parametrizado de la clase que incluye su identificador.
     */

    @Test
    public void ConstructorTest2 () {
        CalendarDayItem local_item = new CalendarDayItem (
                0L,
                "default_title",
                "default_webid",
                0L,
                CalendarDayItem.Status.NOTDONE,
                "01/01/3000",
                new Date (10),
                "default_type"
        );
        assertNotEquals (local_item, null);
    }

    /**
     * Prueba el constructor parametrizado de la clase pasando los parámetros como String.
     */

    @Test
    public void ConstructorTest3 () {
        CalendarDayItem local_item = new CalendarDayItem (
                0L,
                "default_title",
                "default_webid",
                "NOTDONE",
                "01/01/3000",
                "00:00:00",
                0L,
                "default_type"
        );
        assertNotEquals (local_item, null);
    }

    /**
     * Prueba el constructor de la clase con un Intent como parámetro de entrada.
     */

    @Test
    public void ConstructorTest4 () {
        when (intent.getLongExtra ("ID", 0L)).thenReturn (0L);
        when (intent.getStringExtra ("title")).thenReturn ("default_title");
        when (intent.getStringExtra ("status")).thenReturn ("NOTDONE");
        when (intent.getStringExtra ("date")).thenReturn ("01/01/3000");
        when (intent.getStringExtra ("time")).thenReturn ("00:00:00");
        when (intent.getLongExtra ("webid", 0L)).thenReturn (0L);
        when (intent.getLongExtra ("userid", 0L)).thenReturn (0L);
        when (intent.getStringExtra ("type")).thenReturn ("default_type");

        CalendarDayItem.packageIntent (
                intent,
                item.getId (),
                item.getTitle (),
                item.getWebid (),
                item.getStatus (),
                item.getDate (),
                item.getTime (),
                item.getUserid (),
                item.getType ()
        );
        CalendarDayItem local_item = new CalendarDayItem (intent);

        assertNotEquals (local_item, null);
    }


    @Test
    public void getIdTest () {
        assertEquals (item.getId (), 0L);
        assertNotEquals (item.getId (), 1L);
    }

    @Test
    public void setIdTest () {
        item.setId (1L);
        assertEquals (item.getId (), 1L);
        assertNotEquals (item.getId(), 0L);
    }

    @Test
    public void getTitleTest () {
        assertEquals (item.getTitle (), "default_title");
        assertNotEquals (item.getTitle (), "new_title");
    }

    @Test
    public void setTitleTest () {
        item.setTitle ("new_title");
        assertEquals (item.getTitle (), "new_title");
        assertNotEquals (item.getTitle (), "default_title");
    }

    @Test
    public void getWebidTest () {
        assertEquals (item.getWebid (), "default_webid");
        assertNotEquals (item.getWebid (), "new_webid");
    }

    @Test
    public void setWebidTest () {
        item.setWebid ("new_webid");
        assertEquals (item.getWebid (), "new_webid");
        assertNotEquals (item.getWebid (), "default_webid");
    }

    @Test
    public void getUseridTest () {
        assertEquals (item.getUserid (), 0L);
        assertNotEquals (item.getUserid (), 1L);
    }

    @Test
    public void setUseridTest () {
        item.setUserid (1L);
        assertEquals (item.getUserid (), 1L);
        assertNotEquals (item.getUserid (), 0L);
    }

    @Test
    public void getStatusTest () {
        assertEquals (item.getStatus (), CalendarDayItem.Status.NOTDONE);
        assertNotEquals (item.getStatus (), CalendarDayItem.Status.DONE);
    }

    @Test
    public void setStatusTest () {
        item.setStatus (CalendarDayItem.Status.DONE);
        assertEquals (item.getStatus (), CalendarDayItem.Status.DONE);
        assertNotEquals (item.getStatus (), CalendarDayItem.Status.NOTDONE);
    }

    @Test
    public void getDateTest () {
        assertEquals (item.getDate (), "01/01/3000");
        assertNotEquals (item.getDate (), "05/06/2001");
    }

    @Test
    public void setDateTest () {
        item.setDate ("05/06/2001");
        assertEquals (item.getDate (), "05/06/2001");
        assertNotEquals (item.getDate (), "01/01/3000");
    }

    @Test
    public void getTimeTest () {
        assertEquals (item.getTime (), new Date (10));
        assertNotEquals (item.getTime (), new Date (11));
    }

    @Test
    public void setTimeTest () {
        item.setTime (new Date (11));
        assertEquals (item.getTime (), new Date (11));
        assertNotEquals (item.getTime (), new Date (10));
    }

    @Test
    public void getTypeTest () {
        assertEquals (item.getType (), "default_type");
        assertNotEquals (item.getType (), "new_type");
    }

    @Test
    public void setTypeTest () {
        item.setType ("new_type");
        assertEquals (item.getType (), "new_type");
        assertNotEquals (item.getType (), "default_type");
    }

    @Test
    public void packageIntentTest () {
        when (intent.getLongExtra ("ID", 0L)).thenReturn (item.getId ());
        when (intent.getStringExtra ("title")).thenReturn (item.getTitle ());
        when (intent.getStringExtra ("webid")).thenReturn (item.getWebid ());
        when (intent.getStringExtra ("status")).thenReturn (String.valueOf (item.getStatus ()));
        when (intent.getStringExtra ("date")).thenReturn (item.getDate ());
        when (intent.getStringExtra ("time")).thenReturn (String.valueOf (item.getTime ()));
        when (intent.getLongExtra ("userid", 0L)).thenReturn (item.getUserid ());
        when (intent.getStringExtra ("type")).thenReturn (item.getType ());

        CalendarDayItem.packageIntent (
                intent,
                item.getId (),
                item.getTitle (),
                item.getWebid (),
                item.getStatus (),
                item.getDate (),
                item.getTime (),
                item.getUserid (),
                item.getType ()
        );
        CalendarDayItem local_item = new CalendarDayItem (intent);
        assertNotEquals (local_item, null);
    }

    @Test
    public void toStringTest () {
        assertEquals (
                item.getId () +
                System.getProperty ("line.separator") +
                item.getTitle () +
                System.getProperty ("line.separator") +
                item.getWebid () +
                System.getProperty ("line.separator") +
                item.getStatus () +
                System.getProperty ("line.separator") +
                item.getDate () +
                System.getProperty ("line.separator") +
                FORMAT.format (item.getTime ()) +
                System.getProperty ("line.separator") +
                item.getUserid () +
                System.getProperty ("line.separator") +
                item.getType (),
                item.toString ()
        );
        assertNotEquals (
                "ID: " +
                        item.getId () +
                        System.getProperty ("line.separator") +
                        "Title:" +
                        item.getTitle () +
                        System.getProperty ("line.separator") +
                        "Webid:" +
                        item.getWebid () +
                        System.getProperty ("line.separator") +
                        "Status:" +
                        item.getStatus () +
                        System.getProperty ("line.separator") +
                        "Date:" +
                        item.getDate () +
                        System.getProperty ("line.separator") +
                        "Time:" +
                        FORMAT.format (item.getTime ()) +
                        System.getProperty ("line.separator") +
                        "Userid:" +
                        item.getUserid () +
                        System.getProperty ("line.separator") +
                        "Type:" +
                        item.getType (),
                item.toString ()
        );
    }

    @Test
    public void toLogTest () {
        assertEquals (
                "ID: " +
                item.getId () +
                System.getProperty ("line.separator") +
                "Title:" +
                item.getTitle () +
                System.getProperty ("line.separator") +
                "Webid:" +
                item.getWebid () +
                System.getProperty ("line.separator") +
                "Status:" +
                item.getStatus () +
                System.getProperty ("line.separator") +
                "Date:" +
                item.getDate () +
                System.getProperty ("line.separator") +
                "Time:" +
                FORMAT.format (item.getTime ()) +
                System.getProperty ("line.separator") +
                "Userid:" +
                item.getUserid () +
                System.getProperty ("line.separator") +
                "Type:" +
                item.getType (),
                item.toLog ()
        );
        assertNotEquals (
                item.getId () +
                System.getProperty ("line.separator") +
                item.getTitle () +
                System.getProperty ("line.separator") +
                item.getWebid () +
                System.getProperty ("line.separator") +
                item.getStatus () +
                System.getProperty ("line.separator") +
                item.getDate () +
                System.getProperty ("line.separator") +
                FORMAT.format (item.getTime ()) +
                System.getProperty ("line.separator") +
                item.getUserid () +
                System.getProperty ("line.separator") +
                item.getType (),
                item.toLog ()
        );
    }

    @After
    public void afterMethod () {
        item = null;
    }
}
