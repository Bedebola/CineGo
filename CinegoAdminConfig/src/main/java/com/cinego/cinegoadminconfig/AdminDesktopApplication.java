package com.cinego.cinegoadminconfig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDesktopApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminDesktopApplication.class.getResource("/com/cinego/cinegoadminconfig/views/menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 250, 350);
        stage.setTitle("Cinego-Portal de Configuração!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}