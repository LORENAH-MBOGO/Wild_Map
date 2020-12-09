import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {
    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    @Test
    public void animalInstantiatesCorrectly() {
        Animal animal = new Animal("Lion");
         assertTrue(animal instanceof Animal);

    }

    @Test
    public void getNameReturnsCorrectName_Lion() {
        Animal animal = new Animal ("Lion");
        assertEquals("Lion", animal.getName());
    }

    @Test
    public void equalsFunctionsCorrectly_True() {
        Animal animal = new Animal ("Lion");
        Animal secondAnimal = new Animal ("Lion");
        assertTrue(animal.equals(secondAnimal));
    }

    @Test
    public void findByIdReturnsCorrectAnimal_True() {
        Animal animal = new Animal ("Lion");
        animal.save();
        assertTrue(animal.equals(Animal.find(animal.getId())));
    }
}