module start.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens start.src to javafx.fxml;
    exports start.src;
    exports start.src.controller;
    opens start.src.controller to javafx.fxml;
}