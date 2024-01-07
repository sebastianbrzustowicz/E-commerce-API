## Shop API

Shop API is a Java-based, server-side application created to establish communication with client and database.   
The application uses a MySQL database to store users and orders information.  

## Docker MySQL

The Dockerfile will provide containerisation and initialisation of the MySQL database.  
There is a set of commands to get to the same point when starting the application.  
Firstly make sure you are in `Docker` directory.   
Now u can execute building process:   
```
docker build -t mysql:latest .
```
I chose port 3307 because the standard 3306 port for MySQL is occupied by other container. It is up to you.    
You can then run the container with the default password or you can change it (don't forget to change it in the project properties too):
```
docker run --name shopAPIContainer -e MYSQL_ROOT_PASSWORD=sebastian -d -p 3307:3306 mysql:latest
```
Now the MySQL container should run properly.

## Docker Java

Main Dockerfile will provide containerisation and initialisation of the Java application database.  
There is a set of commands to get to the same point when starting the application.  
Firstly make sure you are in project directory.   
Now u can execute building process:   
```
docker build -t javashopapi:latest .
```
I chose port 8081 for Java container, it is up to you.    
You can then run the container:
```
docker run -d -p 8081:8081 javashopapi:latest
```
Now the Java container should run properly.

## Initialize app

Once u are in main project directory this time.   
Firstly install all dependencies:
```
mvn clean install
```
Then run application:
```
mvn spring-boot:run
```
Now everything should be set up.

## Endpoints

Rest API embraces users and orders management issues.    
Some of the options implemented are creating account, changing user's data, orders registration.
Rest endpoints for client only:    

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: POST | /rest/user/register | register new user | User | int |
| :red_circle: DELETE | /rest/user/delete/{userId} | delete user | String | int |
| :yellow_circle: POST | /rest/user/login | login user | &lt;String, String&gt; | String |
| :purple_circle: PATCH | /rest/user/changedata | change user's data | User | String |
| :large_blue_circle: PUT | /rest/admin/changedata | user data management | User | String |
| :yellow_circle: POST | /rest/vehicle/register | register user's vehicle | &lt;String, String&gt; | String |
| :yellow_circle: POST | /rest/vehicle/custom/register | register custom vehicle | Vehicle | String |
| :green_circle: GET | /rest/vehicle/information | information about user's vehicles | String | List&lt;Vehicle&gt; |
| :yellow_circle: POST | /rest/vehicle/delete | deregistration user's vehicle | String | String |

Rest endpoints for admin only:    

| STOMP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: POST | /rest/user/register | register new user | User | int |
| :red_circle: DELETE | /rest/user/delete/{userId} | delete user | String | int |
| :yellow_circle: POST | /rest/user/login | login user | &lt;String, String&gt; | String |
| :purple_circle: PATCH | /rest/user/changedata | change user's data | User | String |
| :large_blue_circle: PUT | /rest/admin/changedata | user data management | User | String |
| :yellow_circle: POST | /rest/vehicle/register | register user's vehicle | &lt;String, String&gt; | String |
| :yellow_circle: POST | /rest/vehicle/custom/register | register custom vehicle | Vehicle | String |
| :green_circle: GET | /rest/vehicle/information | information about user's vehicles | String | List&lt;Vehicle&gt; |
| :yellow_circle: POST | /rest/vehicle/delete | deregistration user's vehicle | String | String |

## Database

A MySQL database was used to store users and orders information.  
The entire database is containerised using Docker.  
The JDBC interface has been used to create a connection to the database.

Users table:
| userId | login | password | email | phoneNum | role | accCreated |
| -------------- | -------------- | -------------- | -------------- | -------------- | -------------- | -------------- |
| VARCHAR(36) | VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | INT | VARCHAR(50) | DATETIME |
| randomUUID()  | "myLogin" | "myPassword" | "example@email.com" | 666777888 | "user" | "10.10.2023 19:23" |

Orders table:
| userId | vehicleId | vehicleName | vehicleType | registrationTime |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| VARCHAR(36)  | VARCHAR(36) | VARCHAR(255) | VARCHAR(50) | DATETIME |
| randomUUID()  | randomUUID() | "myDrone" | "Quadcopter" | "10.10.2023 19:23" |

## Tests

Some simple JUnit tests have been implemented:
```java
testRegisterUser_SuccessfulRegistration()
```

## License

Robot Tasker API is released under the MIT license.

## Author

Sebastian Brzustowicz &lt;Se.Brzustowicz@gmail.com&gt;

