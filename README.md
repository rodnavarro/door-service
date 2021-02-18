# Door-Service  API

Simple, sample API to manage an IoT Remote Door System.
There are some assumptions and further programming that needs to be done in a real production env. See below for these. 

## Getting Started

Start the app with the following command

```
./mvnw spring-boot:run -e

```

API is going to run in http://localhost:8080/

This application is also running an In-Memory H2 database that can be swapped for other real db in the future. 

The door-api-design.pdf in the root folder has an overview for this simple api. 

In a real system, users should use a smartcard to provide a security code along with the authorization. 

## Todo Improvements

- In real production: 
  - All communication must happen over SSL to make it harder to sniff any information over the network, such as credentials
  - Employees should authenticate with some sort of smart card system / PKI
  - Authenticate Door to get JWT token using a clock synced security code (instead of user/password) that's generated at the door and validated in the server to make it harder for 3rd parties to impersonate calls
  - For Doors, IOT Devices IPs should be private and whitelisted in firewall.
- Create a proper devops pipeline to streamline deployments. 
- Monitoring
  - Log Shipping must be implemented for debugging and alerts

## End-Points

### Authenticate 

Http Verb: POST

```
http://localhost:8080/authenticate
```

request body [json]:
```
{
    "userName" : "main_building",
    "password" : "pa%%2021!"
}
```

On Success, will return a JWT token in the response like this:

```
{
    "status": 200,
    "success": true,
    "message": "Authentication Succeeded",
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWluX2J1aWxkaW5nIiwiZXhwIjoxNjEzNjA0MzE0LCJpYXQiOjE2MTM1NjgzMTR9.gX08GK7dJIeqrTV_3HzMudrL3h5f3pOkFFchdqbZSoU"
}
```
On failure:

```
{
    "status": 401,
    "success": false,
    "message": "Authentication Failed",
    "jwt": null
}
```

## Persistence Layer

This project is using Spring Boot Data JPA with an H2 database. 

This framework defines repositories as an interface. Later on it can be swapped for another real database implementation. 

## Initial Data

Because this is a simple api sample, I've hardcoded some initial Door data in initData, inside the DoorsService class. 
In the future, we can have CRUD endpoints to interact with this repository. 

### Doors
```
List<Door> doors = Stream.of(
    new Door(101, "main_building", "s3cr3t-main_building"),
    new Door(102, "lab_a", "s3cr3t-lab_a"),
    new Door(103, "lab_b", "s3cr3t-lab_b")
).collect(Collectors.toList());
repository.saveAll(doors);
```

### Employees
```
List<Employee> employees = Stream.of(
    new Employee(5, "Rod N"),
    new Employee(6, "Charles A")
).collect(Collectors.toList());
repository.saveAll(employees);
```

### ACL (Access Control List)

This is to link where an specific employee has access. This data is used for Authorization.

```
List<ACL> acls = Stream.of(
    new ACL(5000, "main_building",5),
    new ACL(5001, "main_building",6),
    new ACL(5002, "lab_a",5),
    new ACL(5003, "lab_b",6)
).collect(Collectors.toList());
repository.saveAll(acls);
```

## Log Shipping

I've created a LogService class that is used in the api to post logs. In the future this could be connected with a service like logz.io or any other monitoring tool for alerts and observability
