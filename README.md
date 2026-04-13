# Employee Management System (Java)

## 📌 Overview

The Employee Management System is a console-based Java application designed to manage employee records efficiently. It supports full CRUD operations, file persistence, and reporting features using core Java concepts.

---

## 🚀 Features

### 👤 Employee Management

* Add new employee records
* View all employees in formatted table
* Update employee details
* Delete employee records

### 🔍 Search Functionality

* Search employee by ID
* Search by name (partial match supported)
* Search by department

### 💾 File Persistence

* Save employee data to file (`employees.dat`)
* Load employee data from file
* Uses Java Serialization

### 📊 Reports

* Total number of employees
* Average salary
* Highest and lowest salary
* Department-wise employee count and average salary

### ⚠️ Exception Handling

* Handles invalid inputs (non-numeric values, empty fields)
* Prevents duplicate employee IDs
* Ensures salary is non-negative

---

## 🛠️ Technologies Used

* Java (Core Java)
* ArrayList (for storing employees)
* HashMap (for fast lookup by ID)
* File Handling (ObjectInputStream / ObjectOutputStream)
* OOP Concepts (Encapsulation, Classes, Methods)

---

## 📂 Project Structure

EmployeeManagementSystem/
│
├── MainApp.java
├── employees.dat (generated after saving data)
└── README.md

---

## ▶️ How to Run

1. Compile the program:

```
javac MainApp.java
```

2. Run the program:

```
java MainApp
```

---

## 📋 Menu Options

```
=== EMPLOYEE MANAGEMENT SYSTEM ===
1. Add New Employee
2. View All Employees
3. Search Employee
4. Update Employee
5. Delete Employee
6. Generate Reports
7. Save to File
8. Load from File
9. Exit
```

---

## 🧠 Key Concepts Demonstrated

* Object-Oriented Programming (OOP)
* Data Structures (ArrayList, HashMap)
* File Handling & Serialization
* Input Validation & Exception Handling
* Modular and Maintainable Code Design

---

## 📈 Future Enhancements

* GUI using Java Swing or JavaFX
* Database integration (MySQL)
* Export reports to CSV/Excel
* Role-based login system

---

## 👨‍💻 Author

Chandni Singh
---
