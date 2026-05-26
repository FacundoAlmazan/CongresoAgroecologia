FROM maven as grupo7build

WORKDIR /home/ejemplo

COPY backend/pom.xml pom.xml
RUN mvn verify --fail-never

COPY backend/src src
RUN mvn package

FROM tomcat

COPY --from=grupo7build /home/ejemplo/target/*.war /usr/local/tomcat/webapps/ROOT.war