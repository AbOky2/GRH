package com.example.demo.controller;

import com.example.demo.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EmployeeFormController {
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
    private TextField typeContratField;

    private Employee employee; // Pour retourner l'employé ajouté/modifié
    private boolean valid = false; // Pour indiquer si le formulaire est valide

    @FXML
    public void handleSave() {
        try {
            // Créez un nouvel employé à partir des données du formulaire
            employee = new Employee(
                    0,
                    nomCompletField.getText(),
                    posteField.getText(),
                    departementField.getText(),
                    emailField.getText(),
                    Double.parseDouble(salaireField.getText()),
                    dateEmbaucheField.getValue(),
                    dateFinContratField.getValue(),
                    typeContratField.getText()
            );
            valid = true; // Marque le formulaire comme valide
            closeForm();
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les erreurs de validation si nécessaire
        }
    }

    @FXML
    public void handleCancel() {
        valid = false; // Annuler la saisie
        closeForm();
    }

    private void closeForm() {
        Stage stage = (Stage) nomCompletField.getScene().getWindow();
        stage.close();
    }

    public Employee getEmployee() {
        return valid ? employee : null;
    }
}
