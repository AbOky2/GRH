package com.example.demo.utils;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init() {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Table Employé (existant)
            String employeTable = "CREATE TABLE IF NOT EXISTS Employe (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nom_complet VARCHAR(100)," +
                    "poste VARCHAR(50)," +
                    "departement VARCHAR(50)," +
                    "email VARCHAR(100) UNIQUE," +
                    "salaire DOUBLE," +
                    "date_embauche DATE," +
                    "date_fin_contrat DATE," +
                    "type_contrat VARCHAR(20))";
            stmt.execute(employeTable);

            // Table des congés et absences
            String leaveTable = "CREATE TABLE IF NOT EXISTS Leave (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "employee_id INT NOT NULL," +
                    "type VARCHAR(50) NOT NULL," +
                    "start_date DATE NOT NULL," +
                    "end_date DATE NOT NULL," +
                    "status VARCHAR(20) NOT NULL," +
                    "FOREIGN KEY (employee_id) REFERENCES Employe(id) ON DELETE CASCADE)";
            stmt.execute(leaveTable);

            // Table du suivi de la paie
            String payrollTable = "CREATE TABLE IF NOT EXISTS Payroll (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "employee_id INT NOT NULL," +
                    "salary_paid DOUBLE NOT NULL," +
                    "payment_date DATE NOT NULL," +
                    "FOREIGN KEY (employee_id) REFERENCES Employe(id) ON DELETE CASCADE)";
            stmt.execute(payrollTable);

            // Table des formations
            String trainingTable = "CREATE TABLE IF NOT EXISTS Training (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(100) NOT NULL," +
                    "description TEXT," +
                    "duration INT NOT NULL)";
            stmt.execute(trainingTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
