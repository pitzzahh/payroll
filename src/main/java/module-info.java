module io.github.pitzzahh.payroll {
    requires javafx.controls;
    requires javafx.fxml;
    requires util.classes;
    requires ch.qos.logback.core;
    requires org.slf4j;

    opens io.github.pitzzahh.payroll.controllers to javafx.fxml;
    exports io.github.pitzzahh.payroll;
}