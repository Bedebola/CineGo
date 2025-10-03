module com.cinego.cinegoadminconfig {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cinego.cinegoadminconfig to javafx.fxml;
    exports com.cinego.cinegoadminconfig;
    exports com.cinego.cinegoadminconfig.controllers;
    opens com.cinego.cinegoadminconfig.controllers to javafx.fxml;
}