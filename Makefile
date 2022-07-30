build:
	./gradlew build

docker-build:
	cp Dockerfile build/libs ; cd build/libs ; docker build -t client-app .

deploy-mysql:
	docker run -p 3306:3306 --name mysql-container \
	-e MYSQL_ROOT_PASSWORD=root\
	 -e MYSQL_DATABASE=clientDB\
	  -e MYSQL_USER=user\
	   -e MYSQL_PASSWORD=password\
	    -d mysql:8

deploy-app:
	docker run -p 8080:8080 --name client-app\
 	--link mysql-container:mysql client-app

test:
	./gradlew test
