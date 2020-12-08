import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";


        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);
        // retrieving index page
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //CREATING OBJECTS

        //retrieving new animal form
        get("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/animalForm.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        // posting animals form details
        post("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            try {
                Animal animal = new Animal(name);
                animal.save();
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter an animal name.");
            }
            response.redirect("/animals");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //retrieving all animals
        get("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animals", Animal.all());
            model.put("template", "templates/animals.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //retrieving  endangered animal form
        get("/endangered/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/endangeredAnimalForm.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //posting endangered animal form details
        post("/endangered/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String health = request.queryParams("health");
            String age = request.queryParams("age");
            try {
                Endangered endangered = new Endangered(name, health, age);
                endangered.save();
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter all input fields.");
            }
            response.redirect("/animals");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //retrieving sighting form
        get("/sightings/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animals", Animal.all());
            model.put("template", "templates/SightingForm.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //posting a sighting form details
        post("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int animal_id = Integer.parseInt(request.queryParams("animal"));
            String location = request.queryParams("location");
            String ranger_name = request.queryParams("rangerName");


            try {
                Sighting sighting = new Sighting(animal_id, location, ranger_name);
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter Ranger name.");
            }
            response.redirect("/sightings");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //retrieving all sightings
        get("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sightings", Sighting.all());
            model.put("Animal", Animal.class);
            model.put("template", "templates/sightings.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //retrieving animals by id
        get("/animals/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animal", Animal.find(Integer.parseInt(request.params(":id"))));
            model.put("endangered", Endangered.find(Integer.parseInt(request.params(":id"))));
            model.put("Sighting", Sighting.class);
            model.put("template", "templates/animalForm.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //UPDATING OBJECTS
        //retrieving animals edit form
        get("/animals/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("animal", Animal.find(Integer.parseInt(request.params(":id"))));
            model.put("endangered", Endangered.find(Integer.parseInt(request.params(":id"))));
            model.put("template", "templates/animalEditForm.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //posting animal edit form details
        post("/animals/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params(":id"));
            String name = request.queryParams("name");
            String health = request.queryParams("health");
            String age = request.queryParams("age");
            Animal animal = Animal.find(id);
            animal.setName(name);
            animal.update();
            if(animal.getType().equals("Endangered")) {
                Endangered endangered = Endangered.find(id);
                endangered.setHealth(health);
                endangered.setAge(age);
                endangered.update();
            }
            response.redirect("/animals/" + id);
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        //retrieving edit  sightings form
        get("/sightings/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("sighting", Sighting.find(Integer.parseInt(request.params(":id"))));
            model.put("Animal", Animal.class);
            model.put("template", "templates/SightingForm.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //updating a sighting instance
        post("/sightings/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params(":id"));
            String location = request.queryParams("location");
            String rangerName = request.queryParams("rangerName");
            Sighting sighting = Sighting.find(id);
            sighting.setLocation(location);
            sighting.setRangerName(rangerName);
            sighting.update();
            response.redirect("/sightings");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //deleting animal object
        get("/animals/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Animal.find(Integer.parseInt(request.params(":id"))).delete();
            response.redirect("/animals");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        //deleting a sighting
        get("/sightings/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Sighting.find(Integer.parseInt(request.params(":id"))).delete();
            response.redirect("/sightings");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
    }
}

