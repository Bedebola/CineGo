package com.cinego.cinegoadminconfig.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AdminController {

    @FXML
    private TextField nomeUsuario;

    @FXML
    private TextField cpfUsuario;

    @FXML
    private TextField emailUsuario;

    @FXML
    private PasswordField senhaUsuario;

    @FXML
    private void cadastrarUsuarioAdmin(ActionEvent event) {
        new Thread(() -> {
            try {
                String urlNovoUsuario = "http://localhost:8080/usuario/criarAdminInicial";
                URL url = new URL(urlNovoUsuario);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                String json = String.format(
                        "{\"nome\":\"%s\", \"cpf\":\"%s\", \"email\":\"%s\", \"senha\":\"%s\"}",
                        nomeUsuario.getText(),
                        cpfUsuario.getText(),
                        emailUsuario.getText(),
                        senhaUsuario.getText()
                );

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = json.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int status = connection.getResponseCode();
                StringBuilder response = new StringBuilder();

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                status < 400 ? connection.getInputStream() : connection.getErrorStream(),
                                StandardCharsets.UTF_8
                        )
                )) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                }

                connection.disconnect();

                Platform.runLater(() -> {
                    if (status == 200 || status == 201 || status == 203) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso");
                        alert.setHeaderText(null);
                        alert.setContentText("Usuário " + nomeUsuario.getText() + " cadastrado com sucesso!");
                        alert.showAndWait();

                        nomeUsuario.clear();
                        cpfUsuario.clear();
                        emailUsuario.clear();
                        senhaUsuario.clear();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText(null);
                        alert.setContentText("Erro ao cadastrar usuário: " + response);
                        alert.showAndWait();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Ocorreu um erro: " + e.getMessage());
                    alert.showAndWait();
                });
            }
        }).start();
    }

    public void voltarTelaInicial(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cinego/cinegoadminconfig/views/menu-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
