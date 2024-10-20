## Demo Project using Salesforce, Java, SpringBoot and Apache Kafka

Before start using this project, please create a salesforce account and after that create new connectedApp and take following parameters from salesforce.com
1) username
2) password
3) client_id
4) client_secret
5) security_token
6) login_url
save these parameters in environment variable

I created following service in this demo project.
# 1) sf-account-service :
   This service enable user to perform Create,Read,Update and Delete Operations on Salesforce Cloud
   # How to use sf-account-service:
       After setting environment variable, user must start application , after starting application go to following URL
       [your host name]:[your port number]/swagger-ui/index.html , after that user is shown a window to enter username and password
       Here USERNAME and PASSWORD are test and test.After entering correct username, user is forwarded to a swagger-ui page, allowing user to perform CRUD operations on
       salesforce. 
# 2) sf-pub-sub-java-client
     This service notifies user upon any change in Account provided change event is correctly configured on Salesforce Cloud.
  # How to use sf-pub-sub-java-client
     Follow the guidelines on /sf-pub-sub-java-client/README.md 
# 3) sf-account.management-service
  This service notifies read data from kafka-topic, write data to local database and allow use to see data stored on database on swagger-ui.
   # How to use sf-account.management-service
    Follow the guidelines on /sf-account.management-service/README.md 

# Start Application
   cd [path to rootfolder] 
   docker-compose up -d
# Stop Application
  cd [path to rootfolder] 
   docker-compose down --rmi all
# Contact Author
  trivajay259@gmail.com
