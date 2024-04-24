# Spring Boot Application

This is a sample Spring Boot application that requires Java and Maven.


### Java

Ensure you have Java installed. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

### Maven

Ensure you have Maven installed. You can download it from [here](https://maven.apache.org/download.cgi).

# Run the following mvn commands in the root folder of the project

### TEST
```mvn test```

### RUN
```mvn spring-boot:run```


### Documentation
basic auth is implemented, following are the credentials
username : admin
password : admin 

sample curl requests are provided.



### Sample curl requests
## create user
``` 
curl --location 'localhost:8080/users/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Cookie: JSESSIONID=35057888B2902E9E392FE06CD67F7C57' \
--data '{
    "username":"geralt",
    "password":"whataboutiT12$1",
    "firstName": "Hitman"
}'
```

## get user
``` 
curl --location 'localhost:8080/users/geralt' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Cookie: JSESSIONID=35057888B2902E9E392FE06CD67F7C57'
```

## delete user
``` 
curl --location --request DELETE 'localhost:8080/users/geralt' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Cookie: JSESSIONID=35057888B2902E9E392FE06CD67F7C57'
```


## update user
``` 
curl --location 'localhost:8080/users/update' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Cookie: JSESSIONID=35057888B2902E9E392FE06CD67F7C57' \
--data '{
    "username":"geralt",
    "password":"whataboutiT12$1",
    "firstName": "Hitman",
    "lastName": "Bro"
}'
```