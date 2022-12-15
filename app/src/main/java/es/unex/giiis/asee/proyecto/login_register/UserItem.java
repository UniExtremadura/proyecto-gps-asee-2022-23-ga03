package es.unex.giiis.asee.proyecto.login_register;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Define a un usario registrado en la aplicación.
 * Toda su información queda almacenada en la Base de Datos.
 */

@Entity(tableName = "usuarios")
public class UserItem {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");
    @Ignore
    public static final String ID_ATTR = "ID_ATTR";
    @Ignore
    public static final String USERNAMESTRING = "username";
    @Ignore
    public static final String COMP_NAME = "completename";
    @Ignore
    public static final String EMAIL_ADDRESS = "email";
    @Ignore
    public static final String AGE_NUMBER = "age";
    @Ignore
    public static final String PASSWORD_STRING = "password";
    @Ignore
    public static final String PERSON_WEIGHT = "weight";
    @Ignore
    public static final String PERSON_HEIGHT = "height";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String username; //Nombre de usuario
    private String password; //Contraseña
    private String completename; //Nombre completo de usuario (con nombre y apellidos)
    private String email; //Correo electrónico
    private long age; //Edad
    private Double weight; //Peso
    private Double height; //Altura

    @Ignore
    public UserItem(String username, String completename, String email, long age, Double weight, Double height, String password) {
        this.username = username;
        this.completename = completename;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.password = password;
    }

    public UserItem(long id, String username, String completename, String email, long age, Double weight, Double height, String password) {
        this.id = id;
        this.username = username;
        this.completename = completename;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.password = password;
    }

    @Ignore
    public UserItem(Intent intent) {
        this.id = intent.getLongExtra(UserItem.ID_ATTR,0);
        this.username = intent.getStringExtra(UserItem.USERNAMESTRING);
        this.completename = intent.getStringExtra(UserItem.COMP_NAME);
        this.email = intent.getStringExtra(UserItem.EMAIL_ADDRESS);
        this.age = intent.getLongExtra(UserItem.AGE_NUMBER, 0);
        this.weight = intent.getDoubleExtra(UserItem.PERSON_WEIGHT,0.0);
        this.height = intent.getDoubleExtra(UserItem.PERSON_HEIGHT,0.0);
        this.password = intent.getStringExtra(UserItem.PASSWORD_STRING);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getCompletename() { return completename; }

    public void setCompletename(String completename) { this.completename = completename; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public long getAge() { return age; }

    public void setAge(long age) { this.age = age; }

    public Double getWeight() { return weight; }

    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }

    public void setHeight(Double height) { this.height = height; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }


    public static void packageIntent(Intent intent, long id, String username, String completename,
                                     String email, long age, Double weight, Double height, String password) {

        intent.putExtra(UserItem.ID_ATTR, id);
        intent.putExtra(UserItem.USERNAMESTRING, username);
        intent.putExtra(UserItem.COMP_NAME, completename);
        intent.putExtra(UserItem.EMAIL_ADDRESS, email);
        intent.putExtra(UserItem.AGE_NUMBER, age);
        intent.putExtra(UserItem.PERSON_WEIGHT, weight);
        intent.putExtra(UserItem.PERSON_HEIGHT, height);
        intent.putExtra(UserItem.PASSWORD_STRING, password);
    }

    public String toString() {
        return id + ITEM_SEP + username + ITEM_SEP + completename + ITEM_SEP + email + ITEM_SEP + age + ITEM_SEP + weight + ITEM_SEP + height;
    }

    public String toLog() {
        return "ID_ATTR: " + id + ITEM_SEP + "Username:" + username + ITEM_SEP +
                "Completename:" + completename + ITEM_SEP + "Email:" + email + ITEM_SEP +
                "Age:" + age + ITEM_SEP + "Weight:" + weight + ITEM_SEP +
                "Height:" + height;
    }
}
