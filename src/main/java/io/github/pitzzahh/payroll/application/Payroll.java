package io.github.pitzzahh.payroll.application;

import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.payroll.Launcher;
import io.github.pitzzahh.payroll.util.Util;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;


public class Payroll extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent parent = FXMLLoader.load(requireNonNull(Launcher.class.getResource("fxml/payroll.fxml"), "cannot find payroll.fxml file"));
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNIFIED);
        stage.getIcons().add(new Image(requireNonNull(Launcher.class.getResourceAsStream("img/ico.png"), "Icon not found")));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.toFront();
        stage.setTitle("Payroll Application");
        stage.show();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) getStage().setFullScreen(!getStage().isFullScreen());
        });
        ChoiceBox<Object> hoursWorkedChoiceBox = Util.getChoiceBox(parent, 1);

        List<DayOfWeek> weekdays = Arrays.stream(DayOfWeek.values())
                .filter(e -> e.ordinal() <= 4)
                .collect(Collectors.toList());

        hoursWorkedChoiceBox.getItems().addAll(FXCollections.observableArrayList(weekdays));
        ChoiceBox<Object> absencesChoiceBox = Util.getChoiceBox(parent, 3);
        absencesChoiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.asList(Month.values())));
    }

}
