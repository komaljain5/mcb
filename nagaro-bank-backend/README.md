## Run following insert statements into H2 database once application start

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');


## Application details:

Application port = 9081



## Database details:

DB link : localhost:9081/h2/console

spring.datasource.username=sa
spring.datasource.password=password

## Angular application
localhost:4200


NOTE: Run the backend application before launching the Angular application