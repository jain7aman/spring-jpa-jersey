# Spring 4 + Hibernate 4 + MySQL/H2+ Maven Integration
This is the solution to the problem posted at https://github.com/cinqtechnologies/spring-jpa-jersey by ` CINQ Technologies ` 

The solution uses:

- Java (preferably Java 8 and Functional programming as much as possible)
- RESTFull service
- Data manipulation layer
- Spring 4 and Spring-boot
- Maven
- MySQL and H2 (in memory) database 
- Testng for Testing

# Description
This implementation provides a GET/POST REST service to retrieve the list of cities in the database, and return them as a JSON object. 
The service is `idempotent` in inserting new entries, that is, if a country (or city-country combination) is inserted again, the database will maintain only a single copy of the data.

# REST ENDPOINTS

 1. *GET* list all the cities

 	http://localhost:8080/rest/cities[?country=name]

 
 *Returns* list of cities present in the database. 
 *Optional* [country]: If present, filters the city list on the bases of specified country name. It searches (case-insensitive search) on part of country name as well.

 2. *GET* load sample data present at `src/java/resources/SampleData.csv`

 	http://localhost:8080/rest/loadSample

 *Returns* the list of cities uploaded from the file.

 JSON Output format [for both GET calls]:


    [
        {
            "id":86,
            "name":"New York",
            "country":{
                "id":2,
                "name":"United States"
            }
        },
        {
            "id":87,
            "name":"Los Angeles",
            "country":{
                "id":2,
                "name":"United States"
            }
        },
        {
            "id":88,
            "name":"Atlanta",
            "country":{
                "id":2,
                "name":"United States"
            }
        }
    ]

3. *POST* add user provided cities (and countries) into the database
 
 	http://localhost:8080/rest/load
 
 ## Request Parameters:
 Can use any REST client like `POSTMAN` to send POST request

 ###### Headers: 

 	Content-Type : application/json

 ###### Sample Body:

     [
        {
            "name":"Mumbai",
            "country":{
                "name":"India"
            }
        },
        {
            "name":"Bangalore",
            "country":{
                "name":"India"
            }
        }
    ]

 *Returns* Status of the operation `HTTP 200 OK`


# Instructions to deploy
1. Open terminal and `cd` to the directory where you to clone the project.
2. Clone the project using:
	`git clone https://github.com/jain7aman/spring-jpa-jersey.git`
3. `cd spring-jpa-jersey` 
4. Run:
	`mvn clean install`
5. `cd target` and copy the `rest.war` file.
6. Paste the war into `webapps` directory of Apache Tomcat
7. Start the Tomcat server by going into the bin directory and execute startup.bat [for Windows] or startup.sh [for Linux] file.

Note: Can also run `mvn verify` to run the test cases.

# Configuration
- The solution supports MySQL as well as H2 in-memory database. To change the database configurations, edit:

`application.properties` under `spring-jpa-jersey/src/java/resources` for main project
`application.properties` under `spring-jpa-jersey/src/test/resources` for test cases

- Database schema and tables will automatically be created and the data persists between service invocations. To start with the empty tables every time you deply the service, change value of `hibernate.hbm2ddl.auto` parameter (in `application.properties` file for main project) to `create-drop` or `create`. 


