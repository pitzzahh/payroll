package io.github.pitzzahh.payroll.controllers;


import static io.github.pitzzahh.payroll.util.Util.setTextFieldText;
import javafx.scene.control.SelectionModel;
import io.github.pitzzahh.payroll.Payroll;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;

public class PayrollController {

    @FXML
    public void onMouseEnteredAbsencesChoiceBox(MouseEvent mouseEvent) {
        Object selectedItem = getSelectionModel(mouseEvent).getSelectedItem();
        HBox hBox = (HBox) getChoiceBox(mouseEvent).getParent();
        TextField textField = (TextField) hBox.getChildrenUnmodifiable().get(2);
        setTextFieldText(selectedItem, textField, true);
    }

    @SuppressWarnings("unchecked")
    private static ChoiceBox<Object> getChoiceBox(MouseEvent mouseEvent) {
        return (ChoiceBox<Object>) mouseEvent.getSource();
    }

    private static SelectionModel<Object> getSelectionModel(MouseEvent mouseEvent) {
        return getChoiceBox(mouseEvent).getSelectionModel();
    }

    @FXML
    public void onSelection(MouseEvent mouseEvent) {
//        Object selectedItem = getSelectionModel(mouseEvent).getSelectedItem();
//        Payroll.getLogger().debug("SELECTED: " + selectedItem);
    }

    @FXML
    public void onMouseReleaseHoursWorked(MouseEvent mouseEvent) {
        fixIt(mouseEvent, "SELECTED 2: ");
    }

    @FXML
    public void onMousePressedHoursWorked(MouseEvent mouseEvent) {
        fixIt(mouseEvent, "SELECTED 1: ");
    }

    @FXML
    public void onMouseExitedHoursWorked(MouseEvent mouseEvent) {
        fixIt(mouseEvent, "SELECTED 3: ");
    }

    private static void fixIt(MouseEvent mouseEvent, String x) {
        Object selectedItem = getSelectionModel(mouseEvent).getSelectedItem();
        HBox hBox = (HBox) getChoiceBox(mouseEvent).getParent();
        TextField textField = (TextField) hBox.getChildrenUnmodifiable().get(2);
        Payroll.getLogger().debug(x + selectedItem);
        setTextFieldText(selectedItem, textField, false);
    }

    @FXML
    public void onMouseEnteredHoursWorked(MouseEvent mouseEvent) {
        fixIt(mouseEvent, "SELECTED LAST: ");
    }
}
