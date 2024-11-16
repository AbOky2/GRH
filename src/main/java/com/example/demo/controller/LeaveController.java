package com.example.demo.controller;

import com.example.demo.model.Leave;
import com.example.demo.repository.LeaveRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class LeaveController {
    @FXML
    private TableView<Leave> leaveTable;

    @FXML
    private TableColumn<Leave, String> typeColumn;
    @FXML
    private TableColumn<Leave, String> startDateColumn;
    @FXML
    private TableColumn<Leave, String> endDateColumn;
    @FXML
    private TableColumn<Leave, String> statusColumn;

    @FXML
    private ComboBox<String> leaveTypeField;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private ComboBox<String> statusField;

    private final LeaveRepository repository = new LeaveRepository();
    private final ObservableList<Leave> leaves = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialisation des colonnes
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType()));
        startDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        endDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEndDate().toString()));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        leaveTable.setItems(leaves);

        // Initialisation des options
        leaveTypeField.setItems(FXCollections.observableArrayList("Vacances", "Maladie", "Congé sans solde"));
        statusField.setItems(FXCollections.observableArrayList("En attente", "Approuvé", "Rejeté"));

        handleRefresh();
    }

    @FXML
    public void handleAddLeave() {
        try {
            String type = leaveTypeField.getValue();
            LocalDate startDate = startDateField.getValue();
            LocalDate endDate = endDateField.getValue();
            String status = statusField.getValue();

            Leave leave = new Leave(0, 1, type, startDate, endDate, status); // ID employé par défaut : 1
            repository.save(leave);
            handleRefresh();

            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter le congé", e.getMessage());
        }
    }

    @FXML
    public void handleUpdateLeave() {
        Leave selectedLeave = leaveTable.getSelectionModel().getSelectedItem();
        if (selectedLeave == null) {
            showAlert("Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé pour le modifier.");
            return;
        }

        try {
            // Pré-remplir les champs avec les données existantes
            leaveTypeField.setValue(selectedLeave.getType());
            startDateField.setValue(selectedLeave.getStartDate());
            endDateField.setValue(selectedLeave.getEndDate());
            statusField.setValue(selectedLeave.getStatus());

            // Sauvegarder les modifications
            selectedLeave.setType(leaveTypeField.getValue());
            selectedLeave.setStartDate(startDateField.getValue());
            selectedLeave.setEndDate(endDateField.getValue());
            selectedLeave.setStatus(statusField.getValue());

            repository.update(selectedLeave);
            handleRefresh();
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de modifier le congé", e.getMessage());
        }
    }


    @FXML
    public void handleDeleteLeave() {
        Leave selectedLeave = leaveTable.getSelectionModel().getSelectedItem();
        if (selectedLeave == null) {
            showAlert("Erreur", "Aucun congé sélectionné", "Veuillez sélectionner un congé dans la table pour le supprimer.");
            return;
        }

        repository.delete(selectedLeave.getId());
        handleRefresh();
    }

    @FXML
    public void handleRefresh() {
        leaves.setAll(repository.findAll());
    }

    private void clearFields() {
        leaveTypeField.setValue(null);
        startDateField.setValue(null);
        endDateField.setValue(null);
        statusField.setValue(null);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
