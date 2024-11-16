package com.example.demo.controller;

import com.example.demo.model.Payroll;
import com.example.demo.repository.PayrollRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class PayrollController {
    @FXML
    private TableView<Payroll> payrollTable;

    @FXML
    private TableColumn<Payroll, String> employeeIdColumn;
    @FXML
    private TableColumn<Payroll, String> salaryPaidColumn;
    @FXML
    private TableColumn<Payroll, String> paymentDateColumn;

    @FXML
    private TextField employeeIdField;
    @FXML
    private TextField salaryField;
    @FXML
    private DatePicker paymentDateField;

    private final PayrollRepository repository = new PayrollRepository();
    private final ObservableList<Payroll> payrolls = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialisation des colonnes
        employeeIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getEmployeeId())));
        salaryPaidColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getSalaryPaid())));
        paymentDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPaymentDate().toString()));

        payrollTable.setItems(payrolls);
        handleRefresh();
    }

    @FXML
    public void handleAddPayroll() {
        try {
            int employeeId = Integer.parseInt(employeeIdField.getText());
            double salary = Double.parseDouble(salaryField.getText());
            LocalDate paymentDate = paymentDateField.getValue();

            Payroll payroll = new Payroll(0, employeeId, salary, paymentDate);
            repository.save(payroll);
            handleRefresh();

            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter la paie", e.getMessage());
        }
    }

    @FXML
    public void handleUpdatePayroll() {
        Payroll selectedPayroll = payrollTable.getSelectionModel().getSelectedItem();
        if (selectedPayroll == null) {
            showAlert("Erreur", "Aucune paie sélectionnée", "Veuillez sélectionner une paie pour la modifier.");
            return;
        }

        try {
            // Pré-remplir les champs
            employeeIdField.setText(String.valueOf(selectedPayroll.getEmployeeId()));
            salaryField.setText(String.valueOf(selectedPayroll.getSalaryPaid()));
            paymentDateField.setValue(selectedPayroll.getPaymentDate());

            // Sauvegarder les modifications
            selectedPayroll.setSalaryPaid(Double.parseDouble(salaryField.getText()));
            selectedPayroll.setPaymentDate(paymentDateField.getValue());

            repository.update(selectedPayroll);
            handleRefresh();
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de modifier la paie", e.getMessage());
        }
    }

    @FXML
    public void handleDeletePayroll() {
        Payroll selectedPayroll = payrollTable.getSelectionModel().getSelectedItem();
        if (selectedPayroll == null) {
            showAlert("Erreur", "Aucune paie sélectionnée", "Veuillez sélectionner une paie pour la supprimer.");
            return;
        }

        repository.delete(selectedPayroll.getId());
        handleRefresh();
    }


    @FXML
    public void handleRefresh() {
        payrolls.setAll(repository.findAll());
    }

    private void clearFields() {
        employeeIdField.clear();
        salaryField.clear();
        paymentDateField.setValue(null);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
