# Kuna Backend

This is a backend e-commerce application developed with Java and Spring Boot. It provides a foundation for building an e-commerce platform with features such as data storage, Rest API endpoints, database migrations, and integration with payment gateways such as Stripe.

## Setup

To set up and run the application, follow the instructions below:

### Prerequisites

- Java Development Kit (JDK) 17 
- Docker (if running MySQL in a Docker container)

### Steps

1. Clone the repository to your local machine:

   ```shell
   git clone https:https://github.com/fiorellamansilla/kuna_backend

2. Navigate to the project directory:

    ```shell
   cd kuna_backend
   
3. Build the project using Gradle:

    ```shell
    ./gradlew build

4. If you're running MySQL in a Docker container, create a docker-compose.yml file in the project directory with the following contents: 

   ```yaml
   version: '3.1'
   
   services:
    db:
       image: mysql
       ports:
         - "3306:3306"
       expose:
         - "3306"
       environment:
         - MYSQL_ROOT_HOST=%
         - MYSQL_DATABASE=kuna_db
         - MYSQL_ROOT_PASSWORD=your_password
         - MYSQL_ALLOW_EMPTY_PASSWORD=yes
       volumes:
         - ./docker/mysql_volume:/var/lib/mysql 
   ```
   
   This configuration sets up a MySQL container with the necessary environment variables and exposes port 3306 <br />


5. Start the MySQL container using Docker Compose:

   ```shell
   docker-compose up -d

6. Use the test data from 'data.sql' file for the application.<br />


7. Configure the database connection by modifying the flyway block in the build.gradle file:

   ```groovy
   flyway {
    url = 'jdbc:mysql://localhost:3306/kuna_db'
    user = 'root'
    password = 'your_password'
    schemas = ['kuna_database']
   }
   ```
   
   Replace your_database_name, your_username, and your_password with your actual database details.<br />


8. Migrate the database schema using Flyway:

   ```shell
   ./gradlew flywayMigrate

9. Start the application:

   ```shell
   ./gradlew bootRun
<br />
10. The application will start running on `http://localhost:8080`.

## Application Properties

The application requires the configuration specified in the application.properties file. Make sure to update the values of the properties to match your specific configuration.

## Dependencies

The application uses the following libraries and frameworks:

- Spring Boot: 3.0.0
- Spring Boot Starter Data JPA: 3.0.4
- Spring Boot Starter Web: 3.1.0
- Flyway: 9.11.0
- Flyway MySQL: 9.8.1
- Stripe Java: 22.13.0
- Jetbrains Annotations: 24.0.1
- Spring Security Crypto: 6.0.2
- JUnit: 4.11
- Mockito Core: 5.2.0
- MySQL Connector/J: 8.0.32

## Project Structure

The project follows a standard Maven project structure with the following main directories:

* src/main/java: Contains the Java source code of the application.
* src/main/resources: Contains configuration files and database files.
* src/test/java: Contains the unit tests for the application.

## Testing

To run the unit tests, use the following command:

   ```shell
   ./gradlew test
```

The tests are implemented using JUnit 4.11 and Mockito 5.2.

## License

This project is licensed under the MIT License.

```
You can save this content in a `README.md` file in your project directory.
```

