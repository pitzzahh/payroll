module payroll {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens io.github.pitzzahh.payroll.controllers to javafx.fxml;
    exports io.github.pitzzahh.payroll;
    exports io.github.pitzzahh.payroll.application;
}