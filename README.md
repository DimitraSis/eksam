# Eksamen 3. semester efterår 2023 - Dimitra Siskou

## Task 1

| HTTP method | REST Ressource                 | Exceptions and status(es)         |
|-------------|--------------------------------|-----------------------------------|
| GET         | `/api/doctors`                 | 200                               |
| GET         | `/api/doctors/{id}`            | 200                               |
| GET         | `/api/doctors/speciality`      | 200  |
| POST        | `/api/doctors`                 | 201                               |
| GET         | `/api/doctors/birthdate/range` | 404                               |
| PUT         | `/api/doctors/{id}`            | 200                               |
| DELETE      | `/api/doctors/{id}`            | 204                               |

```
 {
    "id": 1,
    "name": "Dr Alice Smith",
    "dateOfBirth": "1975-04-12",
    "yearOfGraduation": 2000,
    "nameOfClinic": "City Health Clinic",
    "speciality": "FAMILY_MEDICINE"
  },
  {
    "id": 2,
    "name": "Dr Bob Johnson",
    "dateOfBirth": "1980-08-05",
    "yearOfGraduation": 2005,
    "nameOfClinic": "Downtown Medical",
    "speciality": "SURGERY"
  },
  {
    "id": 3,
    "name": "Dr Clara Lee",
    "dateOfBirth": "1983-07-22",
    "yearOfGraduation": 2008,
    "nameOfClinic": "Green Valley",
    "speciality": "PEDIATRICS"
  }
]
```

## Task 2

Error handling.
GET http://localhost:7071/api/doctor
if correct url: 200 and a list of names, if not correct url: 404 javelin.NotFoundResponse
GET http://localhost:7071/api/doctor/1
if correct url 200 and the doc with this id, if not correct url: 404 and my message
POST http://localhost:7071/api/doctor/
if correct url 201 and the doc we just created, if not correct data: a 500 javelin.InternalServerErrorResponse
PUT http://localhost:7071/api/doctor/1
if correct url 200 and the doc we just updated, if not correct data: 404 file not found 
DELETE http://localhost:7071/api/doctor/1
if correct url 204 and the doc we just deleted, if not correct data: 404 file not found
GET http://localhost:7071/api/doctor/speciality/SURGERY
if the type of specialty correct then a list

## Task 3
jeg har Generics med

## Task 4
min database er i gang med data fra populate, min endpoints er alle som det skal med 200,201,204

## Task 5 
jeg har testet min dao og alt er som det skal
### Task 6
jeg har lavet test for Get,Put,Post,Create and Delete



## Task 6

### example of a table if needed:

|HTTP method | REST Ressource | Exceptions and status(es) |
|---|---|---|
|GET| `/api/plants`| |
|GET|`/api/plants/{id}`| |
|GET|`/api/plants/type/{type}` | |
|POST| `/api/plants` | |

## code snippet example:

### java

```java
 public void javaCodeSnippet() {
        System.out.println("Hello World");
    }

```
### JSON example

```
{
   json example
   
    "id":8,
    "type": "Rose",
    "name": "Another Rose",
    "maxHeight": 350,
    "price": 299.00

}
```