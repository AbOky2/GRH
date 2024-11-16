package com.example.demo.repository;

import com.example.demo.model.Training;
import com.example.demo.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingRepository {
    public void save(Training training) {
        String sql = "INSERT INTO Training (title, description, duration) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, training.getTitle());
            stmt.setString(2, training.getDescription());
            stmt.setInt(3, training.getDuration());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Training> findAll() {
        List<Training> trainings = new ArrayList<>();
        String sql = "SELECT * FROM Training";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trainings.add(new Training(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("duration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainings;
    }

    public void update(Training training) {
        String sql = "UPDATE Training SET title = ?, description = ?, duration = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, training.getTitle());
            stmt.setString(2, training.getDescription());
            stmt.setInt(3, training.getDuration());
            stmt.setInt(4, training.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Training WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
