module com.libreria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.libreria            to javafx.fxml;
    opens com.libreria.controller to javafx.fxml;
    opens com.libreria.model      to javafx.base;

    exports com.libreria;
    exports com.libreria.controller;
    exports com.libreria.model;
    exports com.libreria.dao;
    exports com.libreria.db;
}
