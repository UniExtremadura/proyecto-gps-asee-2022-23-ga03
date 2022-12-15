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

import es.unex.giiis.asee.proyecto.ui.ejercicios.FavoriteExcerciseItem;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteExcerciseItemTest {

    private FavoriteExcerciseItem item;

    @Mock
    private Intent intent;

    @Before
    public void init() {
        item = new FavoriteExcerciseItem(1, "press de banca", "resistencia", "pechito", "barra", "mu dura mu dura", "Tienes que ser masoca para meter este ejercicio en favoritos", 7);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void FavoriteExcerciseItem1() {
        FavoriteExcerciseItem favoriteExcerciseItem = new FavoriteExcerciseItem("pesas", "fuerza", "biceps", "mancuerna", "easy remix", "se puede se puede", 17);
        assertEquals("pesas", favoriteExcerciseItem.getTittle());
        assertEquals("fuerza", favoriteExcerciseItem.getType());
        assertEquals("biceps", favoriteExcerciseItem.getMuscle());
        assertEquals("mancuerna", favoriteExcerciseItem.getEquipment());
        assertEquals("easy remix", favoriteExcerciseItem.getDifficulty());
        assertEquals("se puede se puede", favoriteExcerciseItem.getInstructions());
        assertEquals(17, favoriteExcerciseItem.getUserid());

    }

    @Test
    public void FavoriteExcerciseItem2() {
        FavoriteExcerciseItem favoriteExcerciseItem = new FavoriteExcerciseItem(5, "pesas", "fuerza", "biceps", "mancuerna", "easy remix", "se puede se puede", 17);
        assertEquals("pesas", favoriteExcerciseItem.getTittle());
        assertEquals("fuerza", favoriteExcerciseItem.getType());
        assertEquals("biceps", favoriteExcerciseItem.getMuscle());
        assertEquals("mancuerna", favoriteExcerciseItem.getEquipment());
        assertEquals("easy remix", favoriteExcerciseItem.getDifficulty());
        assertEquals("se puede se puede", favoriteExcerciseItem.getInstructions());
        assertEquals(17, favoriteExcerciseItem.getUserid());
        assertEquals(5, favoriteExcerciseItem.getId());


    }

    @Test
    public void FavoriteExcerciseItem3() {
        when(intent.getLongExtra("ID_ATTR", 0)).thenReturn(12L);
        when(intent.getStringExtra("title")).thenReturn("prensa");
        when(intent.getStringExtra("type")).thenReturn("resistencia");
        when(intent.getStringExtra("muscle")).thenReturn("pierna");
        when(intent.getStringExtra("equipment")).thenReturn("maquina de prensa");
        when(intent.getStringExtra("difficulty")).thenReturn("dificil");
        when(intent.getStringExtra("instructions")).thenReturn("hasta que te tiemblen las piernas");
        when(intent.getLongExtra("userid", 0)).thenReturn(4L);

        intent.putExtra("ID_ATTR",12);
        intent.putExtra("title","prensa");
        intent.putExtra("type","resistencia");
        intent.putExtra("muscle","pierna");
        intent.putExtra("equipment","maquina de prensa");
        intent.putExtra("difficulty","dificil");
        intent.putExtra("instructions", "hasta que te tiemblen las piernas");
        intent.putExtra("userid", 4);

        FavoriteExcerciseItem favoriteExcerciseItem = new FavoriteExcerciseItem(intent);
        assertEquals(12, favoriteExcerciseItem.getId());
        assertEquals("prensa", favoriteExcerciseItem.getTittle());
        assertEquals("resistencia", favoriteExcerciseItem.getType());
        assertEquals("pierna", favoriteExcerciseItem.getMuscle());
        assertEquals("maquina de prensa", favoriteExcerciseItem.getEquipment());
        assertEquals("dificil", favoriteExcerciseItem.getDifficulty());
        assertEquals("hasta que te tiemblen las piernas", favoriteExcerciseItem.getInstructions());
        assertEquals(4, favoriteExcerciseItem.getUserid());



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
    public void getTittleTest() {
        assertEquals("press de banca", item.getTittle());
    }

    @Test
    public void setTittleTest() {
        assertEquals("press de banca", item.getTittle());
        item.setTittle("banca de press");
        assertEquals("banca de press", item.getTittle());
    }

    @Test
    public void getTypeTest() {
        assertEquals("resistencia", item.getType());
    }

    @Test
    public void setTypeTest() {
        assertEquals("resistencia", item.getType());
        item.setType("fuerza");
        assertEquals("fuerza", item.getType());
    }

    @Test
    public void getMuscleTest() {
        assertEquals("pechito", item.getMuscle());
    }

    @Test
    public void setMuscleTest() {
        assertEquals("pechito", item.getMuscle());
        item.setMuscle("pecho");
        assertEquals("pecho", item.getMuscle());
    }

    @Test
    public void getEquipmentTest() {
        assertEquals("barra", item.getEquipment());
    }

    @Test
    public void setEquipmentTest() {
        assertEquals("barra", item.getEquipment());
        item.setEquipment("nada");
        assertEquals("nada", item.getEquipment());
    }

    @Test
    public void getDifficultyTest() {
        assertEquals("mu dura mu dura", item.getDifficulty());
    }

    @Test
    public void setDifficultyTest() {
        assertEquals("mu dura mu dura", item.getDifficulty());
        item.setDifficulty("nahh");
        assertEquals("nahh", item.getDifficulty());
    }

    @Test
    public void getInstructionsTest() {
        assertEquals("Tienes que ser masoca para meter este ejercicio en favoritos", item.getInstructions());
    }

    @Test
    public void setInstructionsTest() {
        assertEquals("Tienes que ser masoca para meter este ejercicio en favoritos", item.getInstructions());
        item.setInstructions("levanta la barra y asi hasta 100");
        assertEquals("levanta la barra y asi hasta 100", item.getInstructions());
    }

    @Test
    public void getUseridTest() {
        assertEquals(7, item.getUserid());
    }

    @Test
    public void setUseridTest() {
        assertEquals(7, item.getUserid());
        item.setUserid(9);
        assertEquals(9, item.getUserid());
    }


}
