# IMAGES_STORAGE_APP
***
It is a simple fullstack web application which allows upload images, store them in AWS S3, analyze them using AWS Rekognition, and view the results through a web interface

Project structure
-----------
Application is designed according to SOLID, REST principles with next layers:
1. controllers layer;
2. services layer;
3. repositories layer;

Features
-----------
1. Image upload via REST API;
2. File storage in AWS S3;
3. Image analysis using AWS Rekognition;
4. Persistence of metadata and analysis results in a database;
5. REST API for listing images and viewing details;
6. Angular frontend for displaying results;

Technologies
-----------
* Java 17
* Spring Boot
* Spring WEB
* Spring Data JPA
* Apache Maven
* H2 db (local)
* Swagger OpenApi
* Angular
* AWS S3
* AWS Rekognition


Usage
-----------
1. Clone this project from GitHub and make sure that an absolute path doesn't include any white spaces and/or non-Latin
   symbols;
2. Run application locally:
BACKEND:
   * ./mvnw spring-boot:run
   * http://localhost:8080/swagger-ui.html
   * http://localhost:8080/h2-console
FRONTEND:
   * cd image-app-frontend
   * npm install
   * ng serve
   * http://localhost:4200
3. AWS setup:
   * AWS account (Free Tier) is required;
   * S3 bucket is required. eu-west-1 region is highly recommended for correct AWS Rekognition work;
   * IAM user with access to S3 and Rekognition;
   * setup S3 region in the application and make sure that region is the same as in s3 bucket setup;

