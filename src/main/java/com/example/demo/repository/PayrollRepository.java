package com.example.demo.repository;

import com.example.demo.model.Payroll;
import com.example.demo.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollRepository {
    public void save(Payroll payroll) {
        String sql = "INSERT INTO Payroll (employee_id, salary_paid, payment_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payroll.getEmployeeId());
            stmt.setDouble(2, payroll.getSalaryPaid());
            stmt.setDate(3, Date.valueOf(payroll.getPaymentDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payroll> findAll() {
        List<Payroll> payrolls = new ArrayList<>();
        String sql = "SELECT * FROM Payroll";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                payrolls.add(new Payroll(
                        rs.getInt("id"),
                        rs.getInt("employee_id"),
                        rs.getDouble("salary_paid"),
                        rs.getDate("payment_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payrolls;
    }

    public void update(Payroll payroll) {
        String sql = "UPDATE Payroll SET salary_paid = ?, payment_date = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, payroll.getSalaryPaid());
            stmt.setDate(2, Date.valueOf(payroll.getPaymentDate()));
            stmt.setInt(3, payroll.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        String sql = "DELETE FROM Payroll WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
