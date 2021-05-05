FROM tomcat:latest
ADD target/runners.war /usr/local/tomcat/webapps/runners.war
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8080
CMD ["catalina.sh", "run"]
