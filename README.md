# Inventory and Warehouse Management System

## 1. Project Overview
The Inventory and Warehouse Management System is a Java desktop application developed to manage products, product categories, supplier details, and stock movements.

The application provides a graphical user interface (GUI) built using Java Swing and uses SQLite as its database through JDBC.

## 2. Technologies Used

- **Programming Language:** Java
- **GUI:** Java Swing
- **Database:** SQLite
- **Database Connectivity:** JDBC
- **Architecture:** Model-View-Controller (MVC)

## 3. Main Features

- Add and view products.
- Add categories and organize products by category.
- Add and view supplier details.
- Validate positive product quantities and 10-digit supplier phone numbers.
- Record stock inward and outward movements.
- View stock transaction history.
- Prevent issuing more stock than is available.
- Store data in an SQLite database.

## 4. MVC Architecture
The project follows the Model-View-Controller (MVC) design pattern.

- **Model:** Handles data and database operations. It contains classes such as `Product`, `Supplier`, `ProductDAO`, `SupplierDAO`, `CategoryDAO`, and `StockDAO`.
- **View:** Provides the graphical user interface using Java Swing. It contains `ProductView`, `SupplierView`, and `StockView`.
- **Controller:** Connects the views with the models and handles user actions. It contains `ProductController`, `SupplierController`, and `StockController`.

`Main.java` initializes the application and connects the components.

## 5. JDBC Integration
The application uses JDBC to connect Java with the SQLite database.

The SQLite JDBC driver is provided as `sqlite-jdbc-3.53.4.0.jar`.

The database file is `inventory.db`. It stores product information, categories, suppliers, and stock transaction records.

## 6. Project Structure

```
InventorySystem/
├── Main.java
├── Database.java
├── controller/
│   ├── ProductController.java
│   ├── SupplierController.java
│   └── StockController.java
├── model/
│   ├── CategoryDAO.java
│   ├── Product.java
│   ├── ProductDAO.java
│   ├── StockDAO.java
│   ├── StockTransaction.java
│   ├── Supplier.java
│   └── SupplierDAO.java
├── view/
│   ├── ProductView.java
│   ├── SupplierView.java
│   └── StockView.java
├── inventory.db
└── sqlite-jdbc-3.53.4.0.jar
```

## 7. Requirements

- Java Development Kit (JDK) installed.
- The SQLite JDBC JAR included with the project.
- Windows PowerShell or a compatible terminal.

Maven and IntelliJ IDEA are not required to compile and run the application.

## 8. How to Compile and Run
**Step 1: Open the terminal**

Extract the project ZIP and open PowerShell in the `InventorySystem` folder, where `Main.java`, `inventory.db`, and the SQLite JDBC JAR are located.

**Step 2: Check Java**

```
javac -version
java -version
```

**Step 3: Compile the source files**

```
javac -cp ".\sqlite-jdbc-3.53.4.0.jar" -d out (Get-ChildItem -Recurse -Filter *.java).FullName
```

**Step 4: Run the application**

```
java -cp "out;sqlite-jdbc-3.53.4.0.jar" InventorySystem.Main
```

The application window should open after successful compilation.

## 9. Important Notes

- Run the commands from the `InventorySystem` folder.
- Keep `inventory.db` and the SQLite JDBC JAR in that folder.
- The `out` folder contains compiled class files and is generated during compilation.
- Existing database records should be preserved when running the application.
