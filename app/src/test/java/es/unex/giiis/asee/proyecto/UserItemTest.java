package es.unex.giiis.asee.proyecto;

import org.junit.*;
import org.mockito.*;
import android.content.Intent;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import es.unex.giiis.asee.proyecto.login_register.UserItem;

public class UserItemTest {
    private UserItem item = null;

    @Mock
    private Intent intent;

    @Before
    public void init () {
        item = new UserItem (
                0L,
                "default_username",
                "default_completename",
                "default_email",
                0L,
                0.0,
                0.0,
                "default_password"
        );
        MockitoAnnotations.initMocks (this);
    }

    @Test
    public void ConstructorTest1 () {
        UserItem local_item = new UserItem (
                "defaul_username",
                "default_completename",
                "default_email",
                0L,
                0.0,
                0.0,
                "default_password"
        );
        assertNotNull (local_item);
    }

    @Test
    public void ConstructorTest2 () {
        assertNotNull (item);
    }

    @Test
    public void ConstructorTest3 () {
        when (intent.getLongExtra ("ID_ATTR", 0L)).thenReturn (0L);
        when (intent.getStringExtra ("username")).thenReturn ("default_username");
        when (intent.getStringExtra ("completename")).thenReturn ("completename");
        when (intent.getStringExtra ("email")).thenReturn ("default_email");
        when (intent.getLongExtra ("age", 0L)).thenReturn (0L);
        when (intent.getLongExtra ("weight", 0L)).thenReturn (0L);
        when (intent.getLongExtra ("height", 0L)).thenReturn (0L);
        when (intent.getStringExtra ("password")).thenReturn ("default_password");

        UserItem.packageIntent (
                intent,
                item.getId (),
                item.getUsername (),
                item.getCompletename (),
                item.getEmail (),
                item.getAge (),
                item.getWeight (),
                item.getHeight (),
                item.getPassword ()
        );
        UserItem local_item = new UserItem (intent);

        assertNotNull (local_item);
    }

    @Test
    public void getIdTest () {
        assertEquals (0L, item.getId ());
        assertNotEquals (1L, item.getId ());
    }

    @Test
    public void setIdTest () {
        item.setId (1L);
        assertEquals (1L, item.getId ());
        assertNotEquals (0L, item.getId ());
    }

    @Test
    public void getUsernameTest () {
        assertEquals ("default_username", item.getUsername ());
        assertNotEquals ("new_username", item.getUsername ());
    }

    @Test
    public void setUsernameTest () {
        item.setUsername ("new_username");
        assertEquals ("new_username", item.getUsername ());
        assertNotEquals ("default_username", item.getUsername ());
    }

    @Test
    public void getCompletenameTest () {
        assertEquals ("default_completename", item.getCompletename ());
        assertNotEquals ("new_completename", item.getCompletename ());
    }

    @Test
    public void setCompletenameTest () {
        item.setCompletename ("new_completename");
        assertEquals ("new_completename", item.getCompletename ());
        assertNotEquals ("default_completename", item.getCompletename ());
    }

    @Test
    public void getEmailTest () {
        assertEquals ("default_email", item.getEmail ());
        assertNotEquals ("new_email", item.getEmail ());
    }

    @Test
    public void setEmailTest () {
        item.setEmail ("new_email");
        assertEquals ("new_email", item.getEmail ());
        assertNotEquals ("default_email", item.getEmail ());
    }

    @Test
    public void getAgeTest () {
        assertEquals (0L, item.getAge ());
        assertNotEquals (1L, item.getAge ());
    }

    @Test
    public void setAgeTest () {
        item.setAge (1L);
        assertEquals (1L, item.getAge ());
        assertNotEquals (0L, item.getAge ());
    }

    @Test
    public void getWeightTest () {
        assertEquals ((Double) 0.0, item.getWeight ());
        assertNotEquals ((Double) 1.0, item.getWeight ());
    }

    @Test
    public void setWeightTest () {
        item.setWeight (1.0);
        assertEquals ((Double) 1.0, item.getWeight ());
        assertNotEquals ((Double) 0.0, item.getWeight ());
    }

    @Test
    public void getHeightTest () {
        assertEquals ((Double) 0.0, item.getHeight ());
        assertNotEquals ((Double) 1.0, item.getHeight ());
    }

    @Test
    public void setHeightTest () {
        item.setHeight (1.0);
        assertEquals ((Double) 1.0, item.getHeight ());
        assertNotEquals ((Double) 0.0, item.getHeight ());
    }

    @Test
    public void getPasswordTest () {
        assertEquals ("default_password", item.getPassword ());
        assertNotEquals ("new_password", item.getPassword ());
    }

    @Test
    public void setPasswordTest () {
        item.setPassword ("new_password");
        assertEquals ("new_password", item.getPassword ());
        assertNotEquals ("default_password", item.getPassword ());
    }

    @Test
    public void packageIntentTest () {
        when (intent.getLongExtra ("ID_ATTR", 0L)).thenReturn (item.getId ());
        when (intent.getStringExtra ("username")).thenReturn (item.getUsername ());
        when (intent.getStringExtra ("completename")).thenReturn (item.getCompletename ());
        when (intent.getStringExtra ("email")).thenReturn (item.getEmail ());
        when (intent.getLongExtra ("age", 0L)).thenReturn (item.getAge ());
        when (intent.getDoubleExtra ("weight", 0.0)).thenReturn (item.getWeight ());
        when (intent.getDoubleExtra ("height", 0.0)).thenReturn (item.getHeight ());
        when (intent.getStringExtra ("password")).thenReturn (item.getPassword ());

        UserItem.packageIntent (
                intent,
                item.getId (),
                item.getUsername (),
                item.getCompletename (),
                item.getEmail (),
                item.getAge (),
                item.getWeight (),
                item.getHeight (),
                item.getPassword ()
        );

        UserItem local_item = new UserItem (intent);
        assertNotNull (local_item);
    }

    @Test
    public void toStringTest () {
        assertEquals (
                item.getId () +
                        System.getProperty ("line.separator") +
                        item.getUsername () +
                        System.getProperty ("line.separator") +
                        item.getCompletename () +
                        System.getProperty ("line.separator") +
                        item.getEmail () +
                        System.getProperty ("line.separator") +
                        item.getAge () +
                        System.getProperty ("line.separator") +
                        item.getWeight () +
                        System.getProperty ("line.separator") +
                        item.getHeight (),
                item.toString ()
        );
        assertNotEquals (
                "ID_ATTR: " +
                        item.getId () +
                        System.getProperty ("line.separator") +
                        "Username:" +
                        item.getUsername () +
                        System.getProperty ("line.separator") +
                        "Completename:" +
                        item.getCompletename () +
                        System.getProperty ("line.separator") +
                        "Email:" +
                        item.getEmail () +
                        System.getProperty ("line.separator") +
                        "Age:" +
                        item.getAge () +
                        System.getProperty ("line.separator") +
                        "Weight:" +
                        item.getWeight () +
                        System.getProperty ("line.separator") +
                        "Height:" +
                        item.getHeight (),
                item.toString ()
        );
    }

    @Test
    public void toLogTest () {
        assertEquals (
                "ID_ATTR: " +
                        item.getId () +
                        System.getProperty ("line.separator") +
                        "Username:" +
                        item.getUsername () +
                        System.getProperty ("line.separator") +
                        "Completename:" +
                        item.getCompletename () +
                        System.getProperty ("line.separator") +
                        "Email:" +
                        item.getEmail () +
                        System.getProperty ("line.separator") +
                        "Age:" +
                        item.getAge () +
                        System.getProperty ("line.separator") +
                        "Weight:" +
                        item.getWeight () +
                        System.getProperty ("line.separator") +
                        "Height:" +
                        item.getHeight (),
                item.toLog ()
        );
        assertNotEquals (
                item.getId () +
                        System.getProperty ("line.separator") +
                        item.getUsername () +
                        System.getProperty ("line.separator") +
                        item.getCompletename () +
                        System.getProperty ("line.separator") +
                        item.getEmail () +
                        System.getProperty ("line.separator") +
                        item.getAge () +
                        System.getProperty ("line.separator") +
                        item.getWeight () +
                        System.getProperty ("line.separator") +
                        item.getHeight (),
                item.toLog ()
        );
    }

    @After
    public void finish () {
        item = null;
    }
}
