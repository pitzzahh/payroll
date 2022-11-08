package io.github.pitzzahh.payroll.controllers;


import static io.github.pitzzahh.payroll.Payroll.getLogger;
import static io.github.pitzzahh.payroll.util.Util.*;
import static java.lang.Double.parseDouble;
import static java.lang.String.format;
import static java.lang.String.valueOf;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

import java.util.Currency;

public class PayrollController {

    @FXML
    public TextField employeeNameInput;

    @FXML
    public TextField hourlyRate;

    @FXML
    public Label salary;

    @FXML
    public Label employeeName;

    @FXML
    public Label absences;

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
        getLogger().debug("SELECTED ITEM: " + selectedItem);
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
        boolean isValidHours = addHours(selectedItem, hoursWorked, false);
        System.out.println(getHoursWorkedPerMonth());
        textField.setText("");
        if (isValidHours) getSelectionModel(mouseEvent, true)
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
        boolean isValidHours = addHours(selectedItem, absentHours, true);
        System.out.println(getHoursAbsencesPerMonth());
        textField.setText("");
        if (isValidHours) getSelectionModel(mouseEvent, true)
                .select(getSelectionModel(mouseEvent, true).getSelectedIndex() + 1);
    }

    public void onCalculate(MouseEvent mouseEvent) {
        getLogger().debug("NAME: " + employeeName.getText());
        int sum = getHoursWorkedPerMonth().values()
                .stream()
                .filter(e -> !e.isEmpty())
                .mapToInt(Integer::parseInt)
                .sum();
        employeeName.setText(employeeNameInput.getText().trim());
        String peso = Currency.getInstance("PHP").getSymbol();
        salary.setText(peso + format("%.2f", parseDouble(hourlyRate.getText().trim()) * sum));
        absences.setText(valueOf(getHoursAbsencesPerMonth().values()
                .stream()
                .filter(e -> !e.isEmpty())
                .mapToInt(Integer::parseInt)
                .sum()));
        getLogger().debug("HOURLY RATE: " + hourlyRate.getText());
        getLogger().debug("SUM: " + sum);
    }
}
