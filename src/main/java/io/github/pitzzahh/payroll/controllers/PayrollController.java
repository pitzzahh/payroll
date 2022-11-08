package io.github.pitzzahh.payroll.controllers;


import static io.github.pitzzahh.payroll.Payroll.getLogger;
import static io.github.pitzzahh.payroll.util.Util.*;
import static java.lang.Double.parseDouble;
import io.github.pitzzahh.payroll.Payroll;
import static java.lang.String.format;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.regex.Pattern;
import javafx.scene.control.*;
import javafx.fxml.FXML;

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
    public Label tardiness;

    @FXML
    public Label grossSalary;

    @FXML
    public Label netPay;

    @FXML
    public Button resetButton;

    @FXML
    public TextField hoursWorkedTextField;

    @FXML
    public TextField hoursAbsencesTextField;

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
        setTextFieldText(selectedItem, textField, isAbsences);
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

    @FXML
    public void onCalculate(MouseEvent mouseEvent) {
        mouseEvent.consume();
        employeeName.setText(employeeNameInput.getText().trim());

        final int hoursWorked = getHoursWorkedPerMonth().values()
                .stream()
                .filter(e -> !e.isEmpty())
                .mapToInt(Integer::parseInt)
                .sum();

        final String totalHourlyRate = hourlyRate.getText().trim();

        final boolean isValidHourlyRate = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$")
                .matcher(totalHourlyRate)
                .matches();

        if (isValidHourlyRate) {
            final double hourlyRate = parseDouble(totalHourlyRate);

            double totalSalary = hourlyRate * hoursWorked;

            salary.setText(getPesoSign() + format("%s", formatting().format(totalSalary)));

            final int totalAbsences = getHoursAbsencesPerMonth().values()
                    .stream()
                    .filter(e -> !e.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .sum();

            absences.setText(format("%d hours", totalAbsences));

            double totalTardiness = totalHourlyRate.isEmpty() ? 0 : (hourlyRate * (totalAbsences / 60F));

            tardiness.setText(getPesoSign() + format("%s", formatting().format(totalTardiness)));

            final double overTimePay = getOverTimePay().apply(totalSalary, getOverTimeHours());

            getLogger().debug("OVERTIME PAY: " + overTimePay);

            final double grossPay = (totalSalary + overTimePay) - totalTardiness;

            grossSalary.setText(getPesoSign() + format("%s", formatting().format(grossPay)));

            double totalNetPay = grossPay - getDeductions();

            netPay.setText(getPesoSign() + format("%s", formatting().format(totalNetPay)));

        } else {
            Tooltip tooltip = initToolTip("Cannot calculate because of invalid hourly rate.");
            tooltip.setAutoHide(true);
            tooltip.setStyle("-fx-background-color: #003049; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: Jetbrains Mono;" +
                    "-fx-font-size: 15px;");
            tooltip.show(Payroll.getStage().getScene().getWindow());
        }
    }

    /**
     * Resets everything.
     * @param event the button event.
     */
    @FXML
    public void onReset(MouseEvent event) {
        event.consume();
        getHoursWorkedPerMonth().clear();
        getHoursAbsencesPerMonth().clear();

        employeeNameInput.clear();
        hoursWorkedTextField.clear();
        hourlyRate.clear();
        hoursAbsencesTextField.clear();

        employeeName.setText("");
        salary.setText("");
        absences.setText("");
        tardiness.setText("");
        grossSalary.setText("");
        netPay.setText("");

        ChoiceBox<Object> hoursWorkedChoiceBox = getChoiceBox(Payroll.getStage().getScene().getRoot(), 1);
        ChoiceBox<Object> absencesChoiceBox = getChoiceBox(Payroll.getStage().getScene().getRoot(), 3);
        hoursWorkedChoiceBox.getSelectionModel().selectFirst();
        absencesChoiceBox.getSelectionModel().selectFirst();
    }
}
