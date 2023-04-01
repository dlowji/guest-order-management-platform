# Soa midterm platform

## Name
Paimon - Guests Orders Management Platform

## Description
Allow guests, staffs and chefs to interact together in real time, enhance dining experience and reduce mistakes.
Guests can positively order and observe ordered dishes, as well as the grand total of the bill.
Staffs can write guest's orders on the order slip, transfer them to the kitchen using platform instead of manual.
Besides, chefs can control dishes were ordered by guests, especially ensure the correctness of note from guests.

## Installation
- Install Java JDK 17
- Install Intellij IDEA 2022
- Install Postman

## Usage

### Use these accounts below to login into the platform
+ HTTP Method: POST
+ URL: http://localhost:8080/accounts/auth/login
+ Body: raw -> JSON

Admin's account

```json
{
 "username":"admin1234",
 "password":"admin1234"
}
```

Staff's account

```json
{
 "username":"staff1234",
 "password":"staff1234"
}
```

Chef's account

```json
{
  "username":"chef1234",
  "password":"chef1234"
}
```

## Roadmap
- Enhance for final project
- Release module Loyalty customer rewards
- Release module Make payment by PayPal, credit card
- Release random wheel for promo code

## Contributing
- [@S1mpleOW](https://www.github.com/s1mpleow)

## Authors and acknowledgment
- [@Dlowji](https://github.com/dlowji)

## License
MIT

## Project status
- In developing - HOPELESS
