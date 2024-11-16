package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmployeeController {
    @FXML
    private TableView<Employee> employeTable;

    @FXML
    private TableColumn<Employee, String> nomColumn;
    @FXML
    private TableColumn<Employee, String> posteColumn;
    @FXML
    private TableColumn<Employee, String> departementColumn;
    @FXML
    private TableColumn<Employee, String> emailColumn;
    @FXML
    private TableColumn<Employee, String> salaireColumn;
    @FXML
    private TableColumn<Employee, String> dateEmbaucheColumn;
    @FXML
    private TableColumn<Employee, String> dateFinContratColumn;
    @FXML
    private TableColumn<Employee, String> typeContratColumn;

    private final EmployeeRepository repository = new EmployeeRepository();
    private final ObservableList<Employee> employes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialisation de la colonne
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomComplet()));
        posteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPoste()));
        departementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartement()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        salaireColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSalaire())));
        dateEmbaucheColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateEmbauche().toString()));
        dateFinContratColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateFinContrat() != null ? cellData.getValue().getDateFinContrat().toString() : ""));
        typeContratColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeContrat()));


        // Charger les données
        employeTable.setItems(employes);
        handleRefresh(); // Charger les données au démarrage
    }


    @FXML
    private TextField nomCompletField;
    @FXML
    private TextField posteField;
    @FXML
    private TextField departementField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField salaireField;
    @FXML
    private DatePicker dateEmbaucheField;
    @FXML
    private DatePicker dateFinContratField;
    @FXML
    private TextField typeContratField ;

    @FXML
    public void handleAddEmployee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/demo/view/EmployeeForm.fxml"));
            Parent form = fxmlLoader.load();

            EmployeeFormController formController = fxmlLoader.getController();

            Stage formStage = new Stage();
            formStage.setTitle("Ajouter un Employé");
            formStage.setScene(new Scene(form));
            formStage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
            formStage.showAndWait();

            // Récupérez l'employé créé après fermeture du formulaire
            Employee newEmployee = formController.getEmployee();
            if (newEmployee != null) {
                repository.save(newEmployee);
                handleRefresh(); // Rafraîchir la table
            }
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter l'employé", e.getMessage());
        }
    }

    //Update
    @FXML
    public void handleUpdateEmployee() {
        Employee selectedEmployee = employeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert("Erreur", "Aucun employé sélectionné", "Veuillez sélectionner un employé dans la table pour le modifier.");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/demo/view/EmployeeForm.fxml"));
            Parent form = fxmlLoader.load();

            // Pré-remplir les champs avec les données de l'employé sélectionné
            TextField nomCompletField = (TextField) form.lookup("#nomCompletField");
            TextField posteField = (TextField) form.lookup("#posteField");
            TextField departementField = (TextField) form.lookup("#departementField");
            TextField emailField = (TextField) form.lookup("#emailField");
            TextField salaireField = (TextField) form.lookup("#salaireField");
            DatePicker dateEmbaucheField = (DatePicker) form.lookup("#dateEmbaucheField");
            DatePicker dateFinContratField = (DatePicker) form.lookup("#dateFinContratField");
            TextField typeContratField = (TextField) form.lookup("#typeContratField");

            nomCompletField.setText(selectedEmployee.getNomComplet());
            posteField.setText(selectedEmployee.getPoste());
            departementField.setText(selectedEmployee.getDepartement());
            emailField.setText(selectedEmployee.getEmail());
            salaireField.setText(String.valueOf(selectedEmployee.getSalaire()));
            dateEmbaucheField.setValue(selectedEmployee.getDateEmbauche());
            dateFinContratField.setValue(selectedEmployee.getDateFinContrat());
            typeContratField.setText(selectedEmployee.getTypeContrat());

            Stage formStage = new Stage();
            formStage.setTitle("Modifier Employé");
            formStage.setScene(new Scene(form));
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.showAndWait();

            // Récupérer les modifications
            selectedEmployee.setNomComplet(nomCompletField.getText());
            selectedEmployee.setPoste(posteField.getText());
            selectedEmployee.setDepartement(departementField.getText());
            selectedEmployee.setEmail(emailField.getText());
            selectedEmployee.setSalaire(Double.parseDouble(salaireField.getText()));
            selectedEmployee.setDateEmbauche(dateEmbaucheField.getValue());
            selectedEmployee.setDateFinContrat(dateFinContratField.getValue());
            selectedEmployee.setTypeContrat(typeContratField.getText());

            repository.update(selectedEmployee);
            handleRefresh();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de modifier l'employé", e.getMessage());
        }
    }

    //Delete
    @FXML
    public void handleDeleteEmployee() {
        Employee selectedEmployee = employeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert("Erreur", "Aucun employé sélectionné", "Veuillez sélectionner un employé dans la table pour le supprimer.");
            return;
        }

        // Confirmation de la suppression
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer l'employé");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'employé " + selectedEmployee.getNomComplet() + " ?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            repository.delete(selectedEmployee.getId());
            handleRefresh();
        }
    }

    @FXML
    public void handleRefresh() {
        List<Employee> listeEmployes = repository.findAll();
        employes.setAll(listeEmployes);
    }
    private void clearFormFields() {
        nomCompletField.clear();
        posteField.clear();
        departementField.clear();
        emailField.clear();
        salaireField.clear();
        dateEmbaucheField.setValue(null);
        dateFinContratField.setValue(null);
        typeContratField.clear();
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}