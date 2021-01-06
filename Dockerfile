FROM openjdk:11
MAINTAINER Akash <akash.ratogi17@gmail.com>

EXPOSE 8080

# Setup java application
COPY target/*.jar /opt/service.jar
COPY startup.sh /

CMD ["/bin/bash","startup.sh"]
