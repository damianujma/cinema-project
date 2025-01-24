# Introduction
We are going to implement a cinema reservation system (the `com.acme.cinema.ReservationServiceImpl` class).
Every client can reserve a seat in a cinema hall which has a fixed number of seats.
The reservation is successful if the seat is not already reserved. 

## Technical details
It's a maven based project, so it should be easy to import it in your favourite IDE.
There are some Junit 5 test cases. They can be executed from the IDE or from the command line by running:
```shell
mvn test
```
We use Java 17 version. For your convenience we don't use any Java 17 specific features, so you could change it if needed (Java 11 is the baseline here).
Feel free to use any Java version (11 or above) which works for you. You can change the java version in the [pom.xml](./pom.xml) file by editing following line:
```xml
<java.version>17</java.version>
```

# Tasks
There are 3 tasks. They depend on each other so start with [Task 1](#Task-1) then continue with [Task 2](#Task-2) and finish the whole assignment with the [Task-3](#Task-3).

## Task 1
In this task you are asked to implement 4 methods in the `com.acme.cinema.ReservationServiceImpl` class. The class has the following methods:

- `reserveSeats` - Reserves seats for a given client. In the response, the method should return all reserved seats by the client - including previous reservations. If the client has not managed to reserve any seat and has not had any previous reservations, the method should return an empty set.
- `percentageOfFreeSeats` - Returns the integer percentage of free seats in the cinema hall. The percentage should be rounded to the nearest integer. 
- `getReservedSeatsByClient`- Returns the set of reserved seats for a given client.
- `getAllClients` - Returns the set of all clients who have reserved seats.

The assumption is that client names and numbers of seats are unique. 
Initial set of free seats needs to be injected in the constructor of the `ReservationServiceImpl` class. 

The `com.acme.cinema.Seat` class represents a seat in the cinema hall. It has a number and a status (FREE, RESERVED).

To focus on the code, a test case for this scenario is provided in the `com.acme.cinema.ReservationScenario1Test` class.
Prepared tests uses the `com.acme.cinema.SeatGenerator.generateFreeSeats` method to generate a set of initial free seats.

As a result of this task the test should pass successfully. Feel free to add any additional tests you like.
If your IDE doesn't allow you to run the test directly you can run it from a command line by executing:
```shell
mvn test "-Dtest=ReservationScenario1Test"
```


## Task 2
Our community is growing we have more and more requests. Let's make our `ReservationService` thread safe (**Eventually Consistent** solution is fine).

The same approach is used. Focus on the code. Use the `com.acme.cinema.ReservationScenario2Test` to verify your solution.

## Task 3
Some clients request an option to cancel their reservation.

Please add a `ReservationService.cancelReservations` method accepting the name of the client and a list of seat numbers to cancel.

Please base your solution on the result of a [Task 2](#Task-2).
