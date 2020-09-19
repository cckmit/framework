FROM java:8

ADD target/job-*.jar job.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Duser.timezone=GMT+08","-jar","job.jar"]