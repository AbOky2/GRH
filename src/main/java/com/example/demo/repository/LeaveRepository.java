package com.example.demo.repository;

import com.example.demo.model.Leave;
import com.example.demo.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveRepository {
    public void save(Leave leave) {
        String sql = "INSERT INTO Leave (employee_id, type, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, leave.getEmployeeId());
            stmt.setString(2, leave.getType());
            stmt.setDate(3, Date.valueOf(leave.getStartDate()));
            stmt.setDate(4, Date.valueOf(leave.getEndDate()));
            stmt.setString(5, leave.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Leave> findAll() {
        List<Leave> leaves = new ArrayList<>();
        String sql = "SELECT * FROM Leave";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                leaves.add(new Leave(
                        rs.getInt("id"),
                        rs.getInt("employee_id"),
                        rs.getString("type"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

    public void update(Leave leave) {
        String sql = "UPDATE Leave SET type = ?, start_date = ?, end_date = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, leave.getType());
            stmt.setDate(2, Date.valueOf(leave.getStartDate()));
            stmt.setDate(3, Date.valueOf(leave.getEndDate()));
            stmt.setString(4, leave.getStatus());
            stmt.setInt(5, leave.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Leave WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
