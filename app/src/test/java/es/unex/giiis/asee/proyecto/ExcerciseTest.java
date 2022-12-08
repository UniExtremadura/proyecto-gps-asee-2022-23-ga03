package es.unex.giiis.asee.proyecto;

import org.junit.*;

import static org.junit.Assert.*;

import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;

public class ExcerciseTest {

    private Excercise item;

    @Before
    public void init() {
        item = new Excercise("press de banca", "resistencia", "pechito", "barra", "muy dura", "mucha suerte bro, levanta ahí la barra como si fuera Españita");

    }

    @Test
    public void ExcerciseTest() {
        Excercise excercise = new Excercise("plancha lateral", "resistencia", "abdomen", "nada", "normal", "aguanta ahí como un jabato ostia");
        assertEquals("plancha lateral", excercise.getName());
        assertEquals("resistencia", excercise.getType());
        assertEquals("abdomen", excercise.getMuscle());
        assertEquals("nada", excercise.getEquipment());
        assertEquals("normal", excercise.getDifficulty());
        assertEquals("aguanta ahí como un jabato ostia", excercise.getInstructions());

    }


    @Test
    public void getNameTest() {
        assertEquals("press de banca", item.getName());

    }

    @Test
    public void setNameTest() {
        assertEquals("press de banca", item.getName());
        item.setName("banca de press");
        assertEquals("banca de press", item.getName());

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
        item.setEquipment("mancuerna");
        assertEquals("mancuerna", item.getEquipment());

    }

    @Test
    public void getDifficultyTest() {
        assertEquals("muy dura", item.getDifficulty());

    }

    @Test
    public void setDifficultyTest() {
        assertEquals("muy dura", item.getDifficulty());
        item.setDifficulty("durisima");
        assertEquals("durisima", item.getDifficulty());

    }

    @Test
    public void getInstructionsTest() {
        assertEquals("mucha suerte bro, levanta ahí la barra como si fuera Españita", item.getInstructions());

    }

    @Test
    public void setInstructionsTest() {
        assertEquals("mucha suerte bro, levanta ahí la barra como si fuera Españita", item.getInstructions());
        item.setInstructions("Mejor vete a por un Monster, anda");
        assertEquals("Mejor vete a por un Monster, anda", item.getInstructions());
    }


}
