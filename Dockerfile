FROM node:20 AS grupo7front

WORKDIR /home/ejemplo/frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend/ ./
RUN npm run build -- --configuration production


FROM maven AS grupo7build

WORKDIR /home/ejemplo

COPY backend/pom.xml pom.xml
RUN mvn verify --fail-never

COPY backend/src src

COPY --from=grupo7front /home/ejemplo/frontend/dist/browser/ src/main/webapp/

RUN mvn package


FROM tomcat

COPY --from=grupo7build /home/ejemplo/target/*.war /usr/local/tomcat/webapps/ROOT.war