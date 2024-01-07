## Robot Tasker API

Robot Tasker API is a Java-based, server-side application created to establish fast and real-time communication with robots 'over the internet'.   
It is simple connector between the client and the vehicle with no redundant restrictions on the data transmitted.    
Sending desired control values or (less likely) tasks to robot is crucial in terms of connection speed.   
Therefore 'over the internet' solutions are not so popular, as opposed to short-range methods.       
In this case, the WebSocket method was used, which provides high-speed data transfer. 
Moreover, this solution adds a teleoperational aspect to the robot control.  
A multi-channel approach was used to implement the WebSocket, allowing server to handle multiple connections.  
The application uses a MySQL database to store user and vehicle information.  
### Disclaimer
Complete version which guarantee best performance is not available publicly.   
Only alpha version of API with license restrictions is available.   
Please contact me if you are interested in cooperation.   
I am willing to help.  

## Docker

The Dockerfile will provide containerisation and initialisation of the MySQL database.  
There is a set of commands to get to the same point when starting the application.  
Firstly make sure you are in `Docker` directory.   
Now u can execute building process:   
```
docker build -t mysql:latest .
```
You can then run the container with the default password or you can change it (don't forget to change it in the project properties too):
```
docker run --name robotTaskerApiContainer -e MYSQL_ROOT_PASSWORD=sebastian -d -p 3306:3306 mysql:latest
```
Now the MySQL container should run properly.

## Initialize app

Now it is time to compile and run API created in Java.
Make sure u are in main project directory this time.   
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

Rest API part embraces user and vehicle management issues.    
Some of the options implemented are creating account, changing user's data, vehicle registration.
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

A websocket approach was implemented for fast, real-time data transmission between the client and the vehicle.   
Websocket is implemented as multi-channel server-side application with STOMP approach.    
An example implementation of the publisher and subscriber can be found in the `WebSocket_manual_test` folder.    
One assumption made is that the vehicle should have its permanent vehicleId, which is similar to the MAC.    
The vehicleId should be registered for recognition and that is all.
WebSocket endpoints for client (publisher) and vehicle (subscriber):      

| STOMP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: CONNECT | /app/vehicle/connect | create new channel | String | null |
| :red_circle: DISCONNECT  | /app/vehicle/disconnect | delete channel | String | null |
| :yellow_circle: CONNECT | /websocket-endpoint | connect vehicle to ws | null | null |
| :red_circle: DISCONNECT  | not implemented | disconnect vehicle from ws | null | null |
| :green_circle: SUBSCRIBE  | /topic/vehicle-status/{vehicleId} | connection status | null | String |
| :green_circle: SUBSCRIBE  | /topic/vehicle-data/{vehicleId} | get actual data | null | String |
| :large_blue_circle: SEND  | /app/vehicle/connect | connect to specific vehicle | String | null |
| :large_blue_circle: SEND  | /app/vehicle/disconnect | disconnect from specific vehicle | String | null |
| :large_blue_circle: SEND  | /app/vehicle/data | send actual data to vehicle | String | null |

## Tables

A MySQL database was used to store user and vehicle information.  
The entire database is containerised using Docker.  
The JDBC interface has been used to create a connection to the database.

Users table:
| userId | login | password | email | phoneNum | role | accCreated |
| -------------- | -------------- | -------------- | -------------- | -------------- | -------------- | -------------- |
| VARCHAR(36) | VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | INT | VARCHAR(50) | DATETIME |
| randomUUID()  | "myLogin" | "myPassword" | "example@email.com" | 666777888 | "user" | "10.10.2023 19:23" |

Vehicles table:
| userId | vehicleId | vehicleName | vehicleType | registrationTime |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| VARCHAR(36)  | VARCHAR(36) | VARCHAR(255) | VARCHAR(50) | DATETIME |
| randomUUID()  | randomUUID() | "myDrone" | "Quadcopter" | "10.10.2023 19:23" |

## Data transmitted to vehicle

Data is sent in stringified JSON format.    
There are no restrictions imposed on the data to be transmitted in terms of quantity or naming.    
Keywords and types may vary depending on vehicle type used.   
Basically the data content depends on the client and the vehicle implementation, because this app is just a connector.    
Current state of object is fitting quadcopter data.   
Example values:  

```json
{
  "vehicleId": "e218e18c-9e1c-11ee-8c90-0242ac120002",
  "mode": 2,
  "vtol": 0,
  "x": 1,
  "y": 4,
  "alt": 50,
  "yaw": 0,
  "camTrig": 0,
  "camTog": 0,
  "camPitch": 0,
  "clamp": 0
}
```

Data interpretation depends on vehicle used and its needs.

Example encoding:
mode: 1 - Mild, 2 - Normal, 3 - Sport.   
vtol: 0 - no action, 1 - take off, 2 - landing.

## Tests

Some simple JUnit tests have been implemented:
```java
testRegisterUser_SuccessfulRegistration()
testRegisterUser_FailedRegistration()
testLoginUser_SuccessfulLogin()
testLoginUser_FailedLogin()
testRegisterVehicle_SuccessfulRegistration()
testRegisterVehicle_FailedRegistration()
testDeleteVehicle_SuccessfulDeletion()
testDeleteVehicle_FailedDeletion()
testChangeUserData_SuccessfulChange()
testChangeUserData_FailedChange()
testChangeUserDataByAdmin_SuccessfulChange()
testChangeUserDataByAdmin_FailedChange()
testRegisterCustomVehicle_SuccessfulRegister()
testRegisterCustomVehicle_FailedRegister()
```

## License

Robot Tasker API is released under the CC BY-NC-ND 4.0 license.

## Author

Sebastian Brzustowicz &lt;Se.Brzustowicz@gmail.com&gt;

