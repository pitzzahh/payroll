package io.github.pitzzahh.payroll.util;

import static io.github.pitzzahh.payroll.Payroll.getLogger;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Node;

public interface Util {

    @SuppressWarnings("unchecked")
    static ChoiceBox<Object> getChoiceBox(Parent parent) {
        BorderPane borderPane = (BorderPane) parent.getChildrenUnmodifiable().get(0);
        BorderPane borderPaneCenterPane = (BorderPane) borderPane.getCenter();
        BorderPane centerPaneCenterPane = (BorderPane) borderPaneCenterPane.getCenter();
        VBox centerVBox = (VBox) centerPaneCenterPane.getCenter();
        Node lastHHox = centerVBox.getChildren().get(centerVBox.getChildren().size() - 1);
        getLogger().debug(lastHHox instanceof  HBox ? "Found checkBox" : "Not HBox");
        assert lastHHox instanceof HBox;
        return (ChoiceBox<Object>) ((HBox)lastHHox).getChildren().get(1);
    }
}
