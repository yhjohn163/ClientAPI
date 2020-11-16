
FROM adoptopenjdk/openjdk13:alpine-jre

WORKDIR /opt/springbootapp
ADD client-0.0.1-SNAPSHOT.jar /opt/springbootapp
CMD java $JAVA_OPTS -jar /opt/springbootapp/client-0.0.1-SNAPSHOT.jar