package com.cinego.cinegoadminconfig.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    public void sair(){
        System.exit(0);
    }

    public void abrirMenuTesteApiBanco (ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cinego/cinegoadminconfig/testeapibanco-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void abrirMenuAdmin() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cinego/cinegoadminconfig/admin-view.fxml"));
        Scene scene = new Scene(loader.load());
    }







}
