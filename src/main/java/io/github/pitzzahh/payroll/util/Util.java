package io.github.pitzzahh.payroll.util;

import static io.github.pitzzahh.payroll.Payroll.getLogger;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Node;

import java.time.Month;

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
}
