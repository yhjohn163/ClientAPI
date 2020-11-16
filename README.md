

*  [OpenJDK](https://openjdk.java.net/)
*  [Spring Boot](https://spring.io/projects/spring-boot)
*  [MySQL](https://www.mysql.com/downloads/)
*  [Gradle](https://gradle.org/)
*  [Swagger](https://swagger.io/)



##  Prerequisites 

* Any Java IDE
> **Recommended:** [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/) (Community Edition works fine).
* Any SQL-DB Client
> **Recommended:** [phpMyAdmin](www.phpmyadmin.net) , [MySQL Workbench](www.mysql.com/products/workbench) , [DBeaver](https://dbeaver.io/).
* [Git](https://git-scm.com/downloads)
* [Gradle 6.6.1+](https://gradle.org/install/)
> **Optional:** This project is configured to use [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) so there is no need to install a local instance of Gradle.
* [JDK 13+](https://openjdk.java.net/install/)
* [Lombok plugin](https://projectlombok.org/)  
* [MySQL 8.0+](https://www.mysql.com/downloads/)
> **Optional:** Try a container solution, like Docker, instead of installing a local instance of MySQL Server. More details [here](https://hub.docker.com/_/mysql).


##  Getting Started 

> :exclamation: **Attention:** If you are using **IntelliJ IDEA**, you may follow [this tutorial](https://www.jetbrains.com/help/idea/getting-started-with-gradle.html) for more detailed instructions and ignore the explanation from this section.

### Installing 

1. Clone project

```
    $ cd <PROJECT_ROOT_FOLDER>
    $ git clone https://mahjadan@bitbucket.org/mahjadan/client-api.git
```

2. Enter to the project 

```
    $ cd client-api
```
### Checking 
1. you need java 13, check the version `java -version`
2. make sure you are running under bash shell.

### Building 
1. Build the project
```
    $ make build
```
this will run gradlew to build the project and generate the Api jar file required to run the project

### Running 

1. Running MySQL Database
    * Local: we can run a local instance and create a new database with name 'clientDB' and user, but this will need to edit the project properties file to point to the local instance with the relavent username and password
    * Docker: to start a container with MySQL 8 you can run 
   ```
    $ make deploy-mysql
    ``` 
this might take a while the first time as its going to pull the docker image.
you can check the container logs to confirm its has already started by running:
    ```
    $ docker container logs mysql-container
    ``` 
2. Running the Application
    * Using IDE: you can run the application using your preffered IDE
    * Using Docker: first we need to build docker image with our application jar and to do that you can run:
    ```
    $ make docker-build
    ``` 
    After creating the image you can run the docker image using:
    ```
    $ make deploy-app
    ``` 

> **Note:** Application will start by default on **port 8080**

3. Access the Application:
    * Using Postman: you can import the client collection from [Here](https://bitbucket.org/mahjadan/client-api/src/master/Clients.postman_collection.json) and access the API on **http://localhost:8080/client**
    * Using Swagger UI: by accessing this link [docs](http://localhost:8080/swagger-ui/index.html) you can try each end-point.

### Testing 

Go to project root folder and run : `make test` or `./gradlew test`





