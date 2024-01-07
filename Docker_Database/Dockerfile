FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=mysecretpassword
ENV MYSQL_DATABASE=shopAPI

COPY Docker_Database/Database_init.sql /docker-entrypoint-initdb.d/
