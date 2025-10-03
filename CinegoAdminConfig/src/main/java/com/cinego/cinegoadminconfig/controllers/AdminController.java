package com.cinego.cinegoadminconfig.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminController {

public void voltarTelaInicial(ActionEvent event) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cinego/cinegoadminconfig/menu-view.fxml"));
    Scene scene = new Scene(loader.load());
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
}

    @FXML
    private TextField nomeUsuario;

    @FXML
    private TextField cpfUsuario;

    @FXML
    private TextField emailUsuario;

    @FXML
    private TextField senhaUsuario;

    @FXML
    private void cadastrarUsuarioAdmin(ActionEvent event) throws Exception {
        System.out.println("Nome: " + nomeUsuario.getText());
        System.out.println("CPF: " + cpfUsuario.getText());
        System.out.println("Email: " + emailUsuario.getText());
        System.out.println("Senha: " + senhaUsuario.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cinego/cinegoadminconfig/sucess-message-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);


    }


}
