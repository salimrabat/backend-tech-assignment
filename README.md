# Listings Rest Service

This is a REST service for managing listings for online advertising service.

## Prerequisites

- Java 17
- Maven 3.6.3

## Running the Application locally
1. Clone the repository to your local machine:
```
git clone https://github.com/salimrabat/backend-tech-assignment.git
```
2. Navigate to the project directory:
```
cd backend-tech-assignment
```
3. Create your own `.env` file in the project directory based on the `.env.sample` file and fill in the values:
```
DB_URL=jdbc:mysql://localhost:3306/listing_db
DB_USER=dbusername
DB_PASSWORD=dbpassword
```
4. Build the application using Maven:
```
mvn clean package
```
5. Run the application using the following command:
```
java -jar target/backend-0.0.1-SNAPSHOT.jar
```
6. Once the application is running, you can access it in your web browser by navigating to:
```
http://localhost:8080
```
## API Documentation
The API documentation is available in Swagger UI. Once the application is running, you can access it in your web browser by navigating to:
```
http://localhost:8080/swagger-ui/index.html
```
## Running Tests
You can run the unit tests for the application using the following command:
```
mvn test
```
