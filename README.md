# WILDMAP

## Author
Lorenah M.

## Description
This application was inspired by Douglas Fir for a case study on whether the forest authority should allow deforestation or not. It allows rangers to track wildlife sightings in the area.


## Behavior Driven Development

 | Input | Description| Output |
 |:---    | ---: | ---: |
 | Add animal (endangered & common)| click on the add animal button | animal added to list. |   
 | Add sighting | click on add sighting button  | new sighting is added  |
 | View animals/ sightings | click on link on the navigation bar | all recorded animals and sightings are seen |
  | Edit/Delete animal/sighting | click on edit/delete  button  | new animal/sighting is added/deleted  |


## Pre-requisites
1.You need to have java installed. You can use [sdkman](https://sdkman.io/).
Follow the instructions on the _sdkman_ installation process. Then install java:
 ```bash
sdk install java
 ```

2.Gradle
This is a build tool for the java app, you can install it through sdkman:
```bash
sdk install gradle
```
## Setup
1. You will need Internet connection.

2. Get to this WildMap repo on github.

Link:-> ```https://github.com/LORENAH-MBOGO/Wild_Map```

3. From there you can access WildMap.

4. **Clone** the project.

5. **get into project folder** (cd into project).

6. If you have all the **Pre-requisites** you can run the application.
 ``` bash
gradle run
 ```
navigate to localhost:4567 to view the website.


## SQL
7. Install postgres.[ Create User with password.]
```bash
1.Launch postgres
2.Type in psql
Run these commands
3. CREATE DATABASE wildmap;
4. \c wildmap;
5. CREATE TABLE animals (id serial PRIMARY KEY, name varchar, health varchar, age varchar, type varchar);
6. CREATE TABLE sightings (id serial PRIMARY KEY, animal_id int, location varchar, ranger_name varchar, timestamp timestamp);
7. CREATE DATABASE wildmap_test WITH TEMPLATE wildmap;
8. Make sure to edit the DB.java file with the credentials created in order to connect to your database.
```

## Technologies used
- Java , intellij IDEA
- **[Maven] (https://maven.apache.org/)**
- Heroku CLI
- Bootstrap.
- Cascading Style Sheets.
- Handlebars.

## Contributions and support.
In case of any issues, bugs, or you would like to contribute or support, contact me via: 
- Email :- lorayne911@gmail.com.
- Github-Username :- LORENAH-MBOGO

## License
- This project is licensed under the MIT Open Source license Copyright (c) 2020. [LICENCE]