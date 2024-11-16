package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController {

    public ImageView employeeIcon;
    public ImageView leaveIcon;
    public ImageView payrollIcon;
    public ImageView trainingIcon;


    @FXML
    public void initialize() {
        // Charger le logo

        employeeIcon.setImage(new Image(getClass().getResource("/images/employe.png").toExternalForm()));
        leaveIcon.setImage(new Image(getClass().getResource("/images/congé.png").toExternalForm()));
        payrollIcon.setImage(new Image(getClass().getResource("/images/suivi.png").toExternalForm()));
        trainingIcon.setImage(new Image(getClass().getResource("/images/formation.png").toExternalForm()));
    }
    @FXML
    public void openEmployeeView() {
        openView("/com/example/demo/EmployeeView.fxml", "Gestion des Employés");
    }

    @FXML
    public void openLeaveView() {
        openView("/com/example/demo/view/LeaveView.fxml", "Gestion des Congés");
    }

    @FXML
    public void openPayrollView() {
        openView("/com/example/demo/view/PayrollView.fxml", "Suivi des Paies");
    }

    @FXML
    public void openTrainingView() {
        openView("/com/example/demo/view/TrainingView.fxml", "Gestion des Formations");
    }

    private void openView(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
