docker build -t mysqlshopapi:latest -f Docker_Database/Dockerfile .

docker run --name shopAPIDatabaseContainer -e MYSQL_ROOT_PASSWORD=sebastian -d -p 3307:3306 mysqlshopapi:latest

docker build -t javashopapi:latest -f Docker_Server/Dockerfile .

docker run --name shopAPIServerContainer -d -p 8081:8081 javashopapi:latest