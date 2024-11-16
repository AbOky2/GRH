package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public void save(Employee employe) {
        String sql = "INSERT INTO Employe (nom_complet, poste, departement, email, salaire, date_embauche, date_fin_contrat, type_contrat) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employe.getNomComplet());
            stmt.setString(2, employe.getPoste());
            stmt.setString(3, employe.getDepartement());
            stmt.setString(4, employe.getEmail());
            stmt.setDouble(5, employe.getSalaire());
            stmt.setDate(6, Date.valueOf(employe.getDateEmbauche()));
            stmt.setDate(7, employe.getDateFinContrat() != null ? Date.valueOf(employe.getDateFinContrat()) : null);
            stmt.setString(8, employe.getTypeContrat());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de l'employé a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employe.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création de l'employé a échoué, aucun ID obtenu.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employee> findAll() {
        List<Employee> employes = new ArrayList<>();
        String sql = "SELECT * FROM Employe";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee employe = new Employee(
                        rs.getInt("id"),
                        rs.getString("nom_complet"),
                        rs.getString("poste"),
                        rs.getString("departement"),
                        rs.getString("email"),
                        rs.getDouble("salaire"),
                        rs.getDate("date_embauche").toLocalDate(),
                        rs.getDate("date_fin_contrat") != null ? rs.getDate("date_fin_contrat").toLocalDate() : null,
                        rs.getString("type_contrat")
                );
                employes.add(employe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employes;
    }


    // Méthodes update()
    public void update(Employee employe) {
        String sql = "UPDATE Employe SET nom_complet = ?, poste = ?, departement = ?, email = ?, salaire = ?, date_embauche = ?, date_fin_contrat = ?, type_contrat = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employe.getNomComplet());
            stmt.setString(2, employe.getPoste());
            stmt.setString(3, employe.getDepartement());
            stmt.setString(4, employe.getEmail());
            stmt.setDouble(5, employe.getSalaire());
            stmt.setDate(6, Date.valueOf(employe.getDateEmbauche()));
            stmt.setDate(7, employe.getDateFinContrat() != null ? Date.valueOf(employe.getDateFinContrat()) : null);
            stmt.setString(8, employe.getTypeContrat());
            stmt.setInt(9, employe.getId());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Méthode delete
    public void delete(int id) {
        String sql = "DELETE FROM Employe WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}