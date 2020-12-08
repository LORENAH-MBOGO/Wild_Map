import org.sql2o.*;
import java.util.List;


public class Animal {
    public int id;
    public String name;
    public String type;

    //create animal- non endangered
    public static final String ANIMAL_TYPE = "Non-endangered";

    public Animal(String name){
        if (name.equals("")){
            //throw exception if no name entered
            throw new IllegalArgumentException("Please enter an animal name.");
        }
        this.name = name;

        type = ANIMAL_TYPE;
    }


    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    // set animal name
    public void setName(String name) {
        this.name = name;
    }

    // override Animal
    @Override
    public boolean equals(Object otherAnimal) {
        if (otherAnimal instanceof Animal) {
            Animal newAnimal = (Animal) otherAnimal;
            return (this.getName().equals(newAnimal.getName()));
        }

        return false;
    }

    //save  new animal by  name && type
    public void save() {
        String sql = "INSERT INTO animals (name, type) VALUES (:name, :type)";
        try(Connection con = DB.sql2o.open()) {
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", name)
                    .addParameter("type", type)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
        }
    }

    // delete  animal && sighting using id && throw  exception if  id  not mapped
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE from animals WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
            String sql2 = "DELETE from sightings WHERE animal_id = :id";
            con.createQuery(sql2)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }

    //List animals from animals table
    public static List<Animal> all() {
        String sql = "SELECT * FROM animals;";

        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);
        }
    }

    //find  animal with  id && throw  exception if  id  not mapped
    public static Animal find(int id) {
        String sql = "SELECT * FROM animals WHERE id = :id;";

        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animal.class);
        }
    }

    //update  animal using  id && throw  exception if  not mapped
    public void update() {
        String sql = "UPDATE animals SET name = :name WHERE id = :id";

        try(Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }
}
