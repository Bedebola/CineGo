package com.cinego.cinegoadminconfig.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminController {

public void voltarTelaInicial(ActionEvent event) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cinego/cinegoadminconfig/views/menu-view.fxml"));
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
        try {
        var urlNovoUsuario = "http://localhost:8080/usuario/cadastrarUsuario";
        URL url = new URL(urlNovoUsuario);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String json = String.format(
                    "{\"nome\":\"%s\", \"cpf\":\"%s\", \"email\":\"%s\", \"senha\":\"%s\", \"role\":\"ADMIN\"}",
                    nomeUsuario.getText(),
                    cpfUsuario.getText(),
                    emailUsuario.getText(),
                    senhaUsuario.getText()
            );

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = connection.getResponseCode();

            if (status == 200 || status == 203){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Usuário " +  nomeUsuario.getText() + " cadastrado com sucesso!");
                alert.showAndWait();

                nomeUsuario.clear();
                cpfUsuario.clear();
                emailUsuario.clear();
                senhaUsuario.clear();
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Ocorreu um erro: " + e.getMessage());
            alert.showAndWait();
        }
    }
}


