import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;

public class DB {

    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildmap", "moringa", "skylar");
//    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-3-210-255-177.compute-1.amazonaws.com:5432/d3ro70t2uhnta4",
//            "onzeqcwxqxllki", "e40995f1734f2c60615abf48f0d08a23707bc172ee2976bebc403e041301649e");


}