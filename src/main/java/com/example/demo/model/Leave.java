package com.example.demo.model;

import java.time.LocalDate;

public class Leave {
    private int id;
    private int employeeId; // Référence à l'employé
    private String type; // Type de congé (ex : Maladie, Vacances)
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Ex : Approuvé, Rejeté, En attente

    public Leave(int id, int employeeId, String type, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
// Getters et setters
}
