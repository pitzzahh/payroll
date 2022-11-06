package io.github.pitzzahh.payroll.controllers;


import io.github.pitzzahh.payroll.Payroll;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PayrollController {

    @FXML
    public ChoiceBox<Object> choiceBox;


    public void onMouseClicked(MouseEvent mouseEvent) {
        Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();

        Payroll.getLogger().debug("CLICKED: " + selectedItem);
    }
}
