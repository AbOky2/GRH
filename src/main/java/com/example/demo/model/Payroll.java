package com.example.demo.model;

import java.time.LocalDate;

public class Payroll {
    private int id;
    private int employeeId;
    private double salaryPaid;
    private LocalDate paymentDate;

    public Payroll(int id, int employeeId, double salaryPaid, LocalDate paymentDate) {
        this.id = id;
        this.employeeId = employeeId;
        this.salaryPaid = salaryPaid;
        this.paymentDate = paymentDate;
    }

    // Getters et setters

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

    public double getSalaryPaid() {
        return salaryPaid;
    }

    public void setSalaryPaid(double salaryPaid) {
        this.salaryPaid = salaryPaid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
