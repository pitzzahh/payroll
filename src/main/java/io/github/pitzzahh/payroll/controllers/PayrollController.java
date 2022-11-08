package io.github.pitzzahh.payroll.controllers;


import static io.github.pitzzahh.payroll.util.Util.*;
import io.github.pitzzahh.payroll.Payroll;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

public class PayrollController {

    @FXML
    public void onMouseEnteredHoursWorkedInput(MouseEvent mouseEvent) {
        logic(mouseEvent, false);
    }

    /**
     * Contains the logic.
     * @param mouseEvent the mouse event.
     * @param isAbsences if choice box is from absences.
     */
    private static void logic(MouseEvent mouseEvent, boolean isAbsences) {
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
                textField,
                isAbsences
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
        addHours(selectedItem, hoursWorked, false);
        System.out.println(getHoursWorkedPerMonth());
        textField.setText("");
        getSelectionModel(mouseEvent, true)
                .select(getSelectionModel(mouseEvent, true).getSelectedIndex() + 1);
    }

    @FXML
    public void onMouseEnteredAbsencesPerMonth(MouseEvent mouseEvent) {
        logic(mouseEvent, true);
    }

    @FXML
    public void onSaveAbsences(MouseEvent mouseEvent) {
        Object selectedItem = getSelectionModel(mouseEvent, true).getSelectedItem();
        TextField textField = getTextField(mouseEvent, true);
        String absentHours = textField.getText().trim();
        addHours(selectedItem, absentHours, true);
        System.out.println(getHoursAbsencesPerMonth());
        textField.setText("");
        getSelectionModel(mouseEvent, true)
                .select(getSelectionModel(mouseEvent, true).getSelectedIndex() + 1);
    }
}
