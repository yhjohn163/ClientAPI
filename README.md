<div align="center">
<a><img src="https://www.flaticon.com/svg/static/icons/svg/1379/1379505.svg" align="center" width="150" alt="Client API" /></a>

### Client API

*API responsible for maintaining client information: data records*

___


<img src="https://img.shields.io/badge/majorVersion-1-blue">
<img src="https://img.shields.io/badge/java-13-green?logo=java">
<img src="https://img.shields.io/badge/gradle-6.6.1-green?logo=gradle">
<img src="https://img.shields.io/badge/mysql-8.0-green?logo=mysql">
<img src="https://img.shields.io/badge/spring--boot-2.3.5-green?logo=spring">

</div>

___

<div align="center">
<a href="#technologies"><b>Technologies</b></a> •
<a href="#prerequisites"><b>Prerequisites</b></a> • 
<a href="#getting-started"><b>Getting Started</b></a> • 
</div>

___

<!--

-------------- Replace this section for [TOC] marker when available (https://docs.gitlab.com/ee/user/markdown.html#table-of-contents)

## :bookmark_tabs: Table of Contents

1. [Technologies](#Technologies)
2. [Prerequisites](#prerequisites)
3. [Developing](#developing)
    * [Installing](#installing)
    * [Running](#running)
    * [Testing](#testing)
4. [Load Testing](#load-testing)
5. [Other Links](#other-links)
6. [Authors](#authors)
!-->

##  Technologies <a name="technologies"></a>

*  [OpenJDK](https://openjdk.java.net/)
*  [Spring Boot](https://spring.io/projects/spring-boot)
*  [MySQL](https://www.mysql.com/downloads/)
*  [Gradle](https://gradle.org/)
*  [Swagger](https://swagger.io/)



##  Prerequisites <a name="prerequisites"></a>

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


##  Getting Started <a name="getting-started"></a>

> :exclamation: **Attention:** If you are using **IntelliJ IDEA**, you may follow [this tutorial](https://www.jetbrains.com/help/idea/getting-started-with-gradle.html) for more detailed instructions and ignore the explanation from this section.

### Installing <a name="installing"></a>

1. Clone project

```bash
    $ cd <PROJECT_ROOT_FOLDER>
    $ git clone https://mahjadan@bitbucket.org/mahjadan/client-api.git
```

2. Enter to the project 

```bash
    $ cd client-api
```
### Checking <a name="running"></a>
1. you need java 13, check the version `java -version`
2. make sure you are running under bash shell.

### Building <a name="building"></a>
1. Build the project
```bash
    $ make build
```
this will run gradlew to build the project and generate the Api jar file required to run the project

### Running <a name="running"></a>

1. Running MySQL Database
    * Local: we can run a local instance and create a new database with name 'clientDB' and user, but this will need to edit the project properties file to point to the local instance with the relavent username and password
    * Docker: to start a container with MySQL 8 you can run 
   ```bash
    $ make deploy-mysql
    ``` 
this might take a while the first time as its going to pull the docker image.
you can check the container logs to confirm its has already started by running:
    ```bash
    $ docker container logs mysql-container
    ``` 
2. Running the Application
    * Using IDE: you can run the application using your preffered IDE
    * Using Docker: first we need to build docker image with our application jar and to do that you can run:
    ```bash
    $ make docker-build
    ``` 
    After creating the image you can run the docker image using:
    ```bash
    $ make deploy-app
    ``` 

> **Note:** Application will start by default on **port 8080**

3. Access the Application:
    * Using Postman: you can import the client collection from [Here](https://bitbucket.org/mahjadan/client-api/src/master/Clients.postman_collection.json) and access the API on **http://localhost:8080/client**
    * Using Swagger UI: by accessing this link [docs](http://localhost:8080/swagger-ui/index.html) you can try each end-point.

### Testing <a name="testing"></a>

Go to project root folder and run : `make test` or `./gradlew test`





