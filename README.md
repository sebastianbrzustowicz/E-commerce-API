## E-commerce API

E-commerce API is a Java-based, server-side application created to establish communication with client and database.   
The application uses a MySQL database to store users and orders information.  

## Deploy

All in one deploy copy-paste commands for windows and linux systems.

Windows:
```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
.\run_docker_commands.ps1
```
Linux:
```console
chmod +x run_docker_commands.sh
./run_docker_commands.sh
```

## Docker

Follow these commands if u want to make containers manually and adjust them to your prorities.

### Database

The Dockerfile will provide containerisation and initialisation of the MySQL database.  
There is a set of commands to get to the same point when starting the application.  
Firstly make sure you are in main project directory.   
Now u can execute building process:   
```console
docker build -t mysqlshopapi:latest -f Docker_Database/Dockerfile .
```
I chose port 3307 because the standard 3306 port for MySQL is occupied by other container. It is up to you.    
You can then run the container with the default password or you can change it (don't forget to change it in the project properties too):
```console
docker run --name shopAPIDatabaseContainer -e MYSQL_ROOT_PASSWORD=sebastian -d -p 3307:3306 mysqlshopapi:latest
```
Now the MySQL container should run properly.

### Server

This Dockerfile will provide containerisation and initialisation of the Java application.  
There is a set of commands to get to the same point when starting the application.  
Firstly make sure you are in project directory.   
Now u can execute building process:   
```console
docker build -t javashopapi:latest -f Docker_Server/Dockerfile .
```
I chose port 8081 for Java container, it is up to you.    
You can then run the container:
```console
docker run --name shopAPIServerContainer -d -p 8081:8081 javashopapi:latest
```
Now the Java container should run properly.

## Initialize development stage

Once u are in main project directory this time.   
Firstly install all dependencies:
```console
mvn clean install
```
Then run application:
```console
mvn spring-boot:run
```
Now everything should be set up.

## Endpoints

Rest API embraces users and orders management issues.    
Some of the options implemented are creating account, changing user's data, orders registration.    
Rest endpoints for client only:    

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :green_circle: GET | /products | Retrieve a list of products | - | List&lt;Product&gt; |
| :green_circle: GET | /products/{id} | Get details for a specific product | - | Product |
| :green_circle: GET | /cart | View the current contents of the shopping cart | - | Cart |
| :yellow_circle: POST | /cart/add | Add a product to the shopping cart | Product | Cart |
| :yellow_circle: POST | /login | Authenticate a user | Credentials | String |
| :yellow_circle: POST | /register | Register a new user | User | String |
| :green_circle: GET | /orders | View a list of past orders | - | List&lt;Order&gt; |
| :green_circle: GET | /orders/{id} | Retrieve details of a specific order | - | Order |
| :green_circle: GET | /search | Search for products based on user input | SearchQuery | List&lt;Product&gt; |
| :green_circle: GET | /categories | Retrieve a list of product categories | - | List&lt;Category&gt; |
| :yellow_circle: POST | /profile | View or update user profile information | User | UserProfile |
| :green_circle: GET | /products/{id}/reviews | Get product reviews | - | List&lt;Review&gt; |
| :yellow_circle: POST | /products/{id}/reviews/add | Add a review for a product | Review | int |

Rest endpoints for admin only:    

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: POST | /admin/products/add | Add a new product to the catalog | Product | int |
| :red_circle: DELETE | /admin/products/delete/{productId} | Delete a product from the catalog | - | int |
| :purple_circle: PATCH | /admin/products/update/{productId} | Update product details | Product | int |
| :yellow_circle: POST | /admin/orders/fulfill/{orderId} | Mark an order as fulfilled | - | int |
| :yellow_circle: POST | /admin/users/update/{userId} | Update user information | User | int |
| :green_circle: GET | /admin/reports/sales | Retrieve sales report | DateRange | SalesReport |
| :green_circle: GET | /admin/reports/inventory | Retrieve inventory report | - | InventoryReport |

## Database

A MySQL database was used to store users and orders information.  
The entire database is containerised using Docker.  
The JDBC interface has been used to create a connection to the database.

### Table: shopAPI.users

| Column Name   | Data Type             | Constraints     |
|---------------|-----------------------|-----------------|
| userID        | VARCHAR(36)           | PRIMARY KEY     |
| name          | VARCHAR(255) NOT NULL |                 |
| email         | VARCHAR(255) NOT NULL |                 |
| password      | VARCHAR(255) NOT NULL |                 |
| phoneNum      | INT NOT NULL          |                 |
| role          | VARCHAR(50) NOT NULL  |                 |
| accCreated    | DATETIME NOT NULL      |                 |

### Table: shopAPI.products

| Column Name        | Data Type                 | Constraints            |
|--------------------|---------------------------|------------------------|
| productID          | VARCHAR(36)               | PRIMARY KEY            |
| productName        | VARCHAR(255) NOT NULL     |                        |
| price              | DECIMAL(10, 2) NOT NULL   |                        |
| description        | TEXT                      |                        |
| category           | VARCHAR(50)               |                        |
| availableQuantity  | INT NOT NULL              |                        |
| imagePath          | VARCHAR(255)              |                        |


### Table: shopAPI.orders

| Column Name        | Data Type                 | Constraints                        |
|--------------------|---------------------------|------------------------------------|
| userID             | VARCHAR(36) NULL          | FOREIGN KEY (userId) REFERENCES shopAPI.users(userID) |
| orderID            | VARCHAR(36) PRIMARY KEY   |                                    |
| orderType          | VARCHAR(50) NOT NULL      |                                    |
| orderProducts      | VARCHAR(255) NOT NULL     |                                    |
| additionalInfo     | VARCHAR(255)              |                                    |
| registrationTime   | DATETIME NULL             |                                    |


### Table: shopAPI.orderedProducts

| Column Name        | Data Type                 | Constraints                        |
|--------------------|---------------------------|------------------------------------|
| orderedProductID   | VARCHAR(36) PRIMARY KEY   |                                    |
| orderID            | VARCHAR(36)               | FOREIGN KEY (orderID) REFERENCES shopAPI.orders(orderID) |
| productName        | VARCHAR(255) NOT NULL     |                                    |
| price              | DECIMAL(10, 2) NOT NULL   |                                    |
| amount             | INT NOT NULL              |                                    |
| additionalInfo     | TEXT                      |                                    |

## Tests

Some simple JUnit tests have been implemented:
```java
testRegisterUser_SuccessfulRegistration()
```

## License

E-commerce API is released under the MIT license.

## Author

Sebastian Brzustowicz &lt;Se.Brzustowicz@gmail.com&gt;

