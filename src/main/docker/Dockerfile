FROM java:8
VOLUME /tmp
ADD KingForum.jar /kingforum.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kingforum.jar"]
