package io.github.pitzzahh.payroll;

import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.payroll.util.Util;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import org.slf4j.LoggerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.time.Month;
import java.util.List;


public class Payroll extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Payroll.class);

    public static void main(String[] args) {
        launch(args);

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
        Parent parent = FXMLLoader.load(requireNonNull(Payroll.class.getResource("fxml/payroll.fxml"), "cannot find payroll.fxml file"));
        Scene scene = new Scene(parent);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.getIcons().add(new Image(requireNonNull(Payroll.class.getResourceAsStream("img/ico.png"), "Icon not found")));
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.toFront();
        primaryStage.setTitle("Payroll Application");
        primaryStage.show();

        ChoiceBox<Object> hoursWorkedChoiceBox = Util.getChoiceBox(parent, 1);
        hoursWorkedChoiceBox.getItems().addAll(FXCollections.observableArrayList(List.of(Month.values())));
        hoursWorkedChoiceBox.getSelectionModel().selectFirst();
        ChoiceBox<Object> absencesChoiceBox = Util.getChoiceBox(parent, 3);
        absencesChoiceBox.getItems().addAll(FXCollections.observableArrayList(List.of(Month.values())));
        absencesChoiceBox.getSelectionModel().selectFirst();
        LOGGER.info("Application Started");
    }

    /**
     * Gets the logger.
     * @return the {@code Logger} object.
     * @see Logger
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}
