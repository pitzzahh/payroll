package io.github.pitzzahh.payroll.controllers;


import static io.github.pitzzahh.payroll.util.Util.*;
import io.github.pitzzahh.payroll.Payroll;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

public class PayrollController {

    @FXML
    public void onMouseEnteredHoursWorkedInput(MouseEvent mouseEvent) {
        Object selectedItem = getSelectionModel(mouseEvent, false)
                .getSelectedItem();
        TextField textField = getTextField(mouseEvent, false);
        Payroll.getLogger().debug("SELECTED ITEM: " + selectedItem);
        setTextFieldText(selectedItem, textField, false);
        putCurrentHoursWorkedOfMonthIfPresent(
                getSelectionModel(
                        mouseEvent,
                        false
                ).getSelectedItem(),
                textField
        );
    }

    /**
     * Save the hours worked.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onSaveHoursWorked(MouseEvent mouseEvent) {
        Object selectedItem = getSelectionModel(mouseEvent, true).getSelectedItem();
        TextField textField = getTextField(mouseEvent, true);
        String hoursWorked = textField.getText().trim();
        addHoursWorked(selectedItem, hoursWorked);
        System.out.println(getHoursWorkedPerMonth());
        textField.setText("");
        getSelectionModel(mouseEvent, true)
                .select(getSelectionModel(mouseEvent, true).getSelectedIndex() + 1);
    }
}
