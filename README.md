
[![Known Vulnerabilities](https://app.snyk.io/test/github/Nrapendra786/springboot-salesforce-account-demo/badge.svg)](https://snyk.io/test/github/Nrapendra786/springboot-salesforce-account-demo)

Book Link : https://www.amazon.de/-/en/Tobias-Weissmann-ebook/dp/B0F1Z4LDDH(This book taught me a lot about github action)


# Demo Project developed using Java, SpringBoot, Apache Kafka and Salesforce Cloud

Prerequisites: Docker must be installed in the system.

Before starting to use this project, please create a Salesforce account. After that, create a new connected app and save the following parameters from Salesforce.com in environment variables:
1) Username
2) Password
3) Client ID
4) Client Secret
5) Security Token
6) Login URL

I created following services in this demo project.
## 1) sf-account-service :
   This service enable user to perform Create, Read, Update and Delete Operations on Salesforce Cloud
   ## How to use sf-account-service:
     After setting the environment variables, the user must start the application. Once the application is running, go to the following URL:
     [your host name]:[your port number]/swagger-ui/index.html. The user will then be prompted to enter a username and password.
     The USERNAME and PASSWORD are both "test". After entering the correct credentials, the user will be redirected to the Swagger UI page, where they can perform CRUD 
     operations on Salesforce.
## 2) sf-pub-sub-java-client
     This service notifies user upon any change in Account provided change event is correctly configured on Salesforce Cloud.
  ## How to use sf-pub-sub-java-client
     Follow the guidelines on /sf-pub-sub-java-client/README.md 
## 3) sf-account.management-service
  This service notifies read data from kafka-topic, write data to local database and allow use to see data stored on database on swagger-ui.
   ## How to use sf-account.management-service
    Follow the guidelines on /sf-account.management-service/README.md 

## Start Application
   cd [path to rootfolder] <br/>
   docker-compose up -d
## Stop Application
  cd [path to rootfolder]  <br/>
   docker-compose down --rmi all
## Contact Author
  trivajay259@gmail.com
