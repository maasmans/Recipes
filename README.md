# Objective
Create a standalone java application which allows users to manage their favourite recipes. It should
allow adding, updating, removing and fetching recipes. Additionally users should be able to filter
available recipes based on one or more of the following criteria:
1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.
   For example, the API should be able to handle the following search requests:
   • All vegetarian recipes
   • Recipes that can serve 4 persons and have “potatoes” as an ingredient
   • Recipes without “salmon” as an ingredient that has “oven” in the instructions.
   Requirements
   Please ensure that we have some documentation about the architectural choices and also how to
   run the application. The project is expected to be delivered as a GitHub (or any other public git
   hosting) repository URL.
## All these requirements needs to be satisfied:
1. It must be a REST application implemented using Java (use a framework of your choice)
2. Your code should be production-ready.
3. REST API must be documented
4. Data must be persisted in a database
5. Unit tests must be present
6. Integration tests must be present

## Notes about the application
This application uses the Spring Boot framework for quick and safe environment configuration with minimal boilerplate code. 
The database is Postgres, a popular open-source relational database. 
JPA with Hibernate implementation is used for persistence implement standard CRUD operations as seen in the repository classes. 
Flyway manages database migrations for easy updating between versions within a Java application. 
The database is containerized for portability across environments.

### How to run the application
Environment requirements for running the application: Maven, docker, java 17 installed. 
1. Run docker-compose up -d in main directory of the project, this is where the docker-compose.yml is located. For this you will need to have docker installed locally.
2. Open a terminal in the root of the application and execute 'mvn spring-boot:run'
For testing the application you can use the command mvn verify or mvn clean verify to remove all the generated files in the target directory and recreate them.