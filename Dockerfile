#FROM openjdk:11
#ADD target/runners-mysql.jar runners-mysql.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","runners-mysql.jar"]

FROM tomcat:latest
ADD target/runners-mysql.war /usr/local/tomcat/webapps/ROOT.war
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8080
CMD ["catalina.sh", "run"]
