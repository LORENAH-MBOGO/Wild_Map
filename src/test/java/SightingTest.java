import org.sql2o.*;
import org.junit.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import static org.junit.Assert.*;

public class SightingTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    private Sighting testSighting;
    private Animal testAnimal;

    @Before
    public void setUp() {


        testSighting = new Sighting(1, "Zone A", "Kinda");
    }


    @Test
    public void Sighting_instantiatesCorrectly() {
        assertTrue(testSighting instanceof Sighting);
    }

    @Test
    public void getId_sightingInstantiatesWithId() {
        testSighting.save();
        assertTrue(testSighting.getId() > 0);
    }

    @Test
    public void getAnimalId_sightingInstantiatesWithAnimalId() {
        testSighting.save();
        assertEquals(1, testSighting.getAnimalId());
    }

    @Test
    public void getLocation_sightingInstantiatesWithLocation() {
        testSighting.save();
        assertEquals("Zone A", testSighting.getLocation());
    }

    @Test
    public void getRangerName_sightingInstantiatesWithRangerName() {
        testSighting.save();
        assertEquals("Kinda", testSighting.getRangerName());
    }

    @Test
    public void equals_returnsTrueIfAllPropertiesAreTheSame() {
        Sighting anotherSighting = new Sighting(1, "Zone A", "Kinda");
        assertEquals(true, testSighting.equals(anotherSighting));
    }

    @Test
    public void save_assignsIdToObject() {
        testSighting.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(testSighting.getId(), savedSighting.getId());
    }

    @Test
    public void save_insertsObjectIntoDatabase() {
        testSighting.save();
        assertTrue(Sighting.all().get(0).equals(testSighting));
    }

//
    @Test
    public void find_returnsSightingWithSameId_secondSighting() {
        testSighting.save();
        Sighting otherSighting = new Sighting(1, "Zone B", "Zumba");
        otherSighting.save();
        assertEquals(Sighting.find(otherSighting.getId()), otherSighting);
    }
}


