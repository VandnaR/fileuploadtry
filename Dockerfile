FROM openjdk:8
ADD target/fileupload-docker.jar fileupload-docker.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "fileupload-docker.jar"]