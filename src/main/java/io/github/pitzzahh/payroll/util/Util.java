package io.github.pitzzahh.payroll.util;

import io.github.pitzzahh.util.utilities.validation.Validator;
import static io.github.pitzzahh.payroll.Payroll.getLogger;
import io.github.pitzzahh.payroll.Payroll;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import static java.lang.String.format;
import java.util.function.Supplier;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Node;
import java.time.Month;
import java.util.*;

/**
 * Utility class
 */
public interface Util {

    /**
     * Get the choice box
     * @param parent the parent node
     * @param index the index of the HBox which parent is VBox
     * @return the {@code ChoiceBox<Object>}.
     */
    @SuppressWarnings("unchecked")
    static ChoiceBox<Object> getChoiceBox(Parent parent, int index) {
        BorderPane borderPane = (BorderPane) parent.getChildrenUnmodifiable().get(0);
        BorderPane borderPaneCenterPane = (BorderPane) borderPane.getCenter();
        BorderPane centerPaneCenterPane = (BorderPane) borderPaneCenterPane.getCenter();
        VBox centerVBox = (VBox) centerPaneCenterPane.getCenter(); // center
        Node node = centerVBox.getChildren().get(index);
        getLogger().debug(node instanceof  HBox ? "Found CheckBox" : "Node is NOT HBox");
        assert node instanceof HBox;
        return (ChoiceBox<Object>) ((HBox)node).getChildren().get(1);
    }

    @SuppressWarnings("unchecked")
    static ChoiceBox<Object> getChoiceBox(MouseEvent mouseEvent, boolean isFromSaveButton) {
        HBox parent;
        if (isFromSaveButton) parent = (HBox) ((Button) mouseEvent.getSource()).getParent();
        else parent = (HBox) ((TextField) mouseEvent.getSource()).getParent();
        return (ChoiceBox<Object>) parent.getChildren().get(1);
    }

    static SelectionModel<Object> getSelectionModel(MouseEvent mouseEvent, boolean isFromButton) {
        return getChoiceBox(mouseEvent, isFromButton).getSelectionModel();
    }

    static TextField getTextField(MouseEvent mouseEvent, boolean isFromSaveButton) {
        HBox parent;
        if (isFromSaveButton) parent = (HBox) ((Button) mouseEvent.getSource()).getParent();
        else parent = (HBox) ((TextField) mouseEvent.getSource()).getParent();
        return (TextField) parent.getChildren().get(2);
    }


    static void setTextFieldText(Object selectedItem, TextField textField, boolean isAbsences) {
        String prompt = isAbsences ? "Enter Number of Absent Hours On " + selectedItem : "Enter Hours Worked On " + selectedItem;
        if (selectedItem.equals(Month.JANUARY)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.FEBRUARY)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.MARCH)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.APRIL)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.MAY)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.JUNE)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.JULY)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.AUGUST)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.SEPTEMBER)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.OCTOBER)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.NOVEMBER)) textField.setPromptText(prompt);
        else if (selectedItem.equals(Month.DECEMBER)) textField.setPromptText(prompt);
    }

    static boolean addHours(Object month, String hours, boolean isAbsences) {
        boolean isNumber = Validator.isWholeNumber().or(Validator.isDecimalNumber()).test(hours.trim());
        if (isNumber || hours.trim().isEmpty()) {
            if (isAbsences) Fields.hoursAbsences.put(month, hours);
            else Fields.hoursWorkedPerMonth.put(month, hours);
            return true;
        }
        else {
            Tooltip tooltip = initToolTip("Please Enter a Number");
            tooltip.setAutoHide(true);
            tooltip.setStyle("-fx-background-color: #003049; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: Jetbrains Mono;" +
                    "-fx-font-size: 15px;");
            tooltip.show(Payroll.getStage().getScene().getWindow());
            getLogger().error(format("HOURS ON MONTH %s is %s IS NOT A NUMBER", month, hours));
            return false;
        }
    }

    /**
     * Sets the tooltip for the specified control.
     * @param tip the tooltip.
     * @return the tooltip.
     * @see Tooltip
     */
    static Tooltip initToolTip(String tip) {
        return new Tooltip(tip);
    }

    static Map<Object, String> getHoursWorkedPerMonth() {
        return Fields.hoursWorkedPerMonth;
    }

    static Map<Object, String> getHoursAbsencesPerMonth() {
        return Fields.hoursAbsences;
    }

    static void putCurrentHoursWorkedOfMonthIfPresent(Object selectedItem, TextField textField, boolean isAbsences) {
        Optional<String> hours;
        if (isAbsences) hours = Fields.hoursAbsences.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(selectedItem))
                .map(Map.Entry::getValue)
                .findAny();
        else hours = Fields.hoursWorkedPerMonth.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(selectedItem))
                .map(Map.Entry::getValue)
                .findAny();
        getLogger().debug("HOURS PRESENT: " + hours.isPresent());
        hours.ifPresentOrElse(textField::setText, () -> textField.setText(""));
    }
}
class Fields {
    static Map<Object, String> hoursWorkedPerMonth = new Hashtable<>();
    static Map<Object, String> hoursAbsences = new Hashtable<>();

    static final Supplier<Double> DEDUCTIONS = () -> 600.0;
}
