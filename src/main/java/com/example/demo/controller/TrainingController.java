package com.example.demo.controller;

import com.example.demo.model.Training;
import com.example.demo.repository.TrainingRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TrainingController {
    @FXML
    private TableView<Training> trainingTable;

    @FXML
    private TableColumn<Training, String> titleColumn;
    @FXML
    private TableColumn<Training, String> descriptionColumn;
    @FXML
    private TableColumn<Training, String> durationColumn;

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField durationField;

    private final TrainingRepository repository = new TrainingRepository();
    private final ObservableList<Training> trainings = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialisation des colonnes
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        descriptionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        durationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getDuration())));

        trainingTable.setItems(trainings);
        handleRefresh();
    }

    @FXML
    public void handleAddTraining() {
        try {
            String title = titleField.getText();
            String description = descriptionField.getText();
            int duration = Integer.parseInt(durationField.getText());

            Training training = new Training(0, title, description, duration);
            repository.save(training);
            handleRefresh();

            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter la formation", e.getMessage());
        }
    }

    @FXML
    public void handleUpdateTraining() {
        Training selectedTraining = trainingTable.getSelectionModel().getSelectedItem();
        if (selectedTraining == null) {
            showAlert("Erreur", "Aucune formation sélectionnée", "Veuillez sélectionner une formation pour la modifier.");
            return;
        }

        try {
            // Pré-remplir les champs
            titleField.setText(selectedTraining.getTitle());
            descriptionField.setText(selectedTraining.getDescription());
            durationField.setText(String.valueOf(selectedTraining.getDuration()));

            // Sauvegarder les modifications
            selectedTraining.setTitle(titleField.getText());
            selectedTraining.setDescription(descriptionField.getText());
            selectedTraining.setDuration(Integer.parseInt(durationField.getText()));

            repository.update(selectedTraining);
            handleRefresh();
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de modifier la formation", e.getMessage());
        }
    }

    @FXML
    public void handleDeleteTraining() {
        Training selectedTraining = trainingTable.getSelectionModel().getSelectedItem();
        if (selectedTraining == null) {
            showAlert("Erreur", "Aucune formation sélectionnée", "Veuillez sélectionner une formation pour la supprimer.");
            return;
        }

        repository.delete(selectedTraining.getId());
        handleRefresh();
    }


    @FXML
    public void handleRefresh() {
        trainings.setAll(repository.findAll());
    }

    private void clearFields() {
        titleField.clear();
        descriptionField.clear();
        durationField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
