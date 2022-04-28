# Meeting application API

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/#build-image)

### Run application in docker

```
docker-compose build
```

```
docker-compose run
```

## API calls
```
Currenty mapped to http://localhost:8080
```
## User
### Create a user resource
```
POST /users/add
Accept: application/json
Content-Type: application/json

{
"name" : "Adam Smith",
"email" : "adam.smith@gmail.com",
"positionId" : "1" (optional)
}

RESPONSE: HTTP 201 (Created) "Saved"
Location header: http://localhost:8080/users/add?name=Adam Smith&email=adam.smith@gmail.com&positionId=1
```

### Retrieve a json of all users

```
GET http://localhost:8080/users/all

Response: HTTP 200
Content:  json file 
```

### Retrieve user with given id

```
GET http://localhost:8080/users/findById?id=25

Response: HTTP 200
Content:  json file 
```

### Update a user resource

```
PUT http://localhost:8080/users/update?id=28&name=Adam Smith&email=adam.smith@gmail.com&positionId=1
Accept: application/json
Content-Type: application/json

{
"id" : "28",
"name" : "Adam Smith", (optional)
"email" : "adam.smith@gmail.com", (optional)
"positionId" : "1" (optional)
}

RESPONSE: HTTP 204 (No Content) "Updated"
```

### Delete user with given id

```
GET http://localhost:8080/users/delete?id=28

Response: HTTP 200
Content: "Deleted" 
```

For other entities look into `src/main/java/com/erstedigital/meetingappbackend/rest/controller` files.