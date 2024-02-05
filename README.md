# Table Reservation System

## Description
This is an application designed to manage table reservations in a restaurant. It allows to reserve a table for a customer based on the amount of seats required, handling the logic for both standard and group reservations.

### Table of Contents

- [Project Name](#table-reservation-system)
    - [Description](#description)
    - [Table of Contents](#table-of-contents)
    - [Features](#features) 
    - [Getting Started](#getting-started)
    - [Project Structure](#project-structure)
    - [ERD](#erd)
    - [Credits](#credits)

## Features

- **Customer Management**
  - `Add a Customer`: Register new customers into the system.
  - `Delete a Customer`: Remove customer profiles when no longer needed.
  - `Show Customers`: List all customers to keep track of the customer base.
- **Reservation Handling**
  - `Add a Reservation`: Create reservations with options tailored for individual or group bookings.
  - `Delete a Reservation`: Cancel reservations with ease.
- **Reservation Overview**
  - `Show Reservations`: View all reservations, with the ability to apply various filters for customized displays.
- **Table Administration**
  - `Show Tables`: Display all tables to overview the restaurant's seating arrangement.
  - `Add a Table`: Introduce new tables to the system as the restaurant layout evolves.
  - `Delete a Table`: Remove tables from the system to reflect changes in the seating plan.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Java JDK 8 or higher
- PostgreSQL database

### Installing

A step-by-step series of examples that tell you how to get a development environment running:

1. Clone the repository to your local machine:

```bash
git clone https://github.com/yourusername/tablereservationsystem.git
```

2. Navigate to the directory where you cloned the repository:
   
```bash
cd tablereservationsystem
```
3. Compile and run the application (the command may vary depending on your environment):
```bash
javac Main.java
java Main
```

### Setting up the Database
Ensure you have PostgreSQL installed and running on your system. Then set up the database connection:

1. Open the DatabaseConnection.java file.
2. Replace the DATABASE_URL, USER, and PASSWORD with your PostgreSQL database URL, user name, and password:
```java
private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/yourdbname";
private static final String USER = "yourusername";
private static final String PASSWORD = "yourpassword";
```
3. Run the application, and it will automatically connect to the database using the provided credentials.
Running the Application
Once you have set up your database connection, you can run the application using the following command:

```bash
java Main
```

Remember to replace `yourusername/tablereservationsystem.git`, `yourdbname`, `yourusername`, and `yourpassword` with your actual GitHub repository URL and your PostgreSQL credentials.

## Project Structure

The Table Reservation System is organized into several packages, each serving a distinct role in the application:

### `controller` Package
Contains classes that handle user input and application logic:
- `CustomerController`: Manages customer-related operations.
- `ReservationController`: Handles reservation creation and management.
- `TableController`: Oversees table-related functionalities.
- `ReservationValidator`: Validates reservation data for correctness.

### `model` Package
Includes the core business logic and data models:
- `builder` Subpackage: Contains builder classes like `GroupReservationBuilder` and `StandardReservationBuilder` for constructing reservation objects.
- `CustomerService`, `ReservationService`, `TableService`: Service classes that provide a higher-level interface for interacting with the DAO layer.

### `dao` Package
Encapsulates all database access:
- `CustomerDAO`, `ReservationDAO`, `TableDAO`: Data access objects for CRUD operations on `Customer`, `Reservation`, and `Table` entities, respectively.
- `DAO`: An interface defining standard data access operations.
- `entities` Subpackage: Contains entity classes that represent database tables.

### `singleton` Package
Manages database connection:
- `DatabaseConnection`: Singleton class that handles the creation and management of the database connection.

### `strategy` Package
Implements Strategy pattern for various application behaviours:
- `reservation_display` Subpackage: Contains strategies for displaying reservations, such as `CurrentReservationDisplayStrategy` and `CustomerReservationDisplayStrategy`.
- `table_allocation` Subpackage: Contains strategies for table allocation, like `GroupAllocationStrategy` and `StandardAllocationStrategy`.

### `view` Package
Provides the user interface for the application:
- `CustomerView`, `ReservationView`, `TableView`: Classes responsible for displaying information and interacting with the user.
- `CLI`: Command-Line Interface that provides the main application menu and interaction loop.
- `Main`: The entry point of the application, bootstrapping the entire system.

Each package is designed to be as independent as possible, following the principle of separation of concerns to facilitate maintainability and scalability.


## ERD

![table-reservation-erd](https://github.com/juliakam588/Table-Reservation-System/assets/77432872/fbdcf5b2-53d8-476f-a7ab-ec44c45448f9)

## Credits
Created by Julia Kamuda. Contact: julka.kamuda@gmail.com
