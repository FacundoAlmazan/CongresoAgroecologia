#FROM una imagen NODE que tenga NPM instalado y la renombran con as grupo7front

# WORKDIR /home/ejemplo
# COPY frontend/package.json package.json
# RUN npm install

# COPY frontend/src src
# COPY ng build --prod

FROM maven as grupo7build

WORKDIR /home/ejemplo

COPY backend/pom.xml pom.xml
RUN mvn verify --fail-never

COPY backend/src src
# COPY --from=grupo7front /home/ejemplo/dist/browser/* src/main/webapp
RUN mvn package

FROM tomcat

COPY --from=grupo7build /home/ejemplo/target/*.war /usr/local/tomcat/webapps/ROOT.war