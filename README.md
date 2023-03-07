# microservice-midterm

## Name
Paimon - Guests Orders Management Platform

## Description
Allow guests, staffs and chefs to interact together in real time, enhance dining experience and reduce mistakes.
Guests can positively order and observe ordered dishes, as well as the grand total of the bill.
Staffs can write guest's orders on the order slip, transfer them to the kitchen using platform instead of manual.
Besides, chefs can control dishes were ordered by guests, especially ensure the correctness of note from guests.

## Badges

## Visuals

## Installation
- Install Java JDK 17
- Install Intellij IDEA 2022
- Install Postman

## Usage
## Add new role
- To add new role, use the following details in Postman tool:
+ HTTP Method: POST
+ URL: http://localhost:8080/accounts/register/roles
+ Body: raw -> JSON
```json
{
    "roleId": "R01",
    "roleName": "Admin",
    "roleDescription": "Super power role, the role can control everyone such as @Simple0W" 
}
```
## Register account
- To register account, ensure that role data has exist in db, then use the following details in Postman tool:
+ HTTP Method: POST
+ URL: http://localhost:8080/accounts/auth/register
+ Body: raw -> JSON
```json
{
    "username": "annnaan1234",
    "password": "cuibap",
    "fullName": "Tom Riddle",
    "email": "dev.loivo2k2@gmail.com",
    "gender": 1,
    "salary": 1200,
    "dob": "2002-01-24",
    "address": "Inside My Le's heart",
    "roleId": "R01",
    "phone": "0937732655"
}
```
## Login with JWT Auth in Spring Cloud Gateway
- In order to login for getting the JWT token, use the following details in Postman tool:

+ HTTP Method: POST
+ URL: http://localhost:8080/accounts/auth/login
+ Body: raw -> JSON
```json
{
 "username":"annnaan1234",
 "password":"cuibap"
}
```
## To use these below API, you have to log in and assign the return token value to Authorization header (don't add Bearer because I forget in developing process :<<)

## Create new dish
- To add a new dish, use the following details in Postman tool:
+ HTTP Method: POST
+ URL: http://localhost:8080/kitchens/dishes/create
+ Body: raw -> JSON
```json
{
    "title":"MARGARITA",
    "image":"https://hips.hearstapps.com/hmg-prod/images/margarita-1592951298.jpg?crop=1xw:1xh;center,top&resize=980:*",
    "price":300000,
    "summary":"Cloyingly sweet margarita mixes have given this drink a bad name. A well-made version is a fresh mix of lime juice and tequila, with a hint of sweetener",
    "categoryId":"C01"
}
```

## Get dishes
- To get menu of dishes use the following details in Postman tool:
+ HTTP Method: GET
+ URL: http://localhost:8080/kitchens/menu

## Create new table
- To add a new severed table use the following details in Postman tool:
+ HTTP Method: POST
+ URL: http://localhost:8080/tables
+ Body: raw -> JSON
```json
{
    "code": "Alpha hawkeye",
    "capacity": 10
}
```

## Get list of tables
- To get list of tables use the following details in Postman tool:
+ HTTP Method: GET
+ URL: http://localhost:8080/tables

## Get table detail by id
- To get table detail by id use the following details in Postman tool:
+ HTTP Method: GET
+ URL: http://localhost:8080/tables/{id}

## Create new order
- To create a new order use the following details in Postman tool:
+ HTTP Method: POST
+ URL: http://localhost:8080/orders
+ Body: raw -> JSON
```json
{
    "userId": "909fda08-41cc-4163-b06f-bce3e5f69c82",
    "tableId": "909fda08-41cc-4163-b06f-bce3e5f69c81"
}
```

## Place an order
- To place an order use the following details in Postman tool:
+ HTTP Method: POST
+ URL: http://localhost:8080/orders/placed
+ Body: raw -> JSON
```json
{
    "orderId": "909fda08-41cc-4163-b06f-bce3e5f69c83",
    "orderLineItemRequestList": [
        {
            "dishId": "909fda08-41cc-4163-b06f-bce3e5f69ccc",
            "quantity": 3,
            "price": 1200
        },
        {
            "dishId": "909fda08-41cc-4163-b06f-bce3e5f69cec",
            "quantity": 5,
            "price": 2300
        }
    ]
}
```

## Get list of orders
- To get list of orders use the following details in Postman tool:
+ HTTP Method: GET
+ URL: http://localhost:8080/orders

## Get table detail by id
- To get order detail by id use the following details in Postman tool:
+ HTTP Method: GET
+ URL: http://localhost:8080/orders/{id}

## Roadmap
- Enhance for final project
- Release module Loyalty customer rewards
- Release module Make payment by PayPal
- Release ...

## Contributing
- [@S1mpleOW](https://www.github.com/s1mpleow)

## Authors and acknowledgment
- [@Dlowji](https://github.com/dlowji)

## License
MIT

## Project status
- In developing - HOPELESS
