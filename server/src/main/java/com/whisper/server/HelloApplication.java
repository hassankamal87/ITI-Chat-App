package com.whisper.server;

import com.whisper.server.business.services.ServerService;
import com.whisper.server.presentation.services.SceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        stage.setOnCloseRequest(event -> {

          //  ServerService.getInstance().stopServer();
            Platform.exit();
            System.exit(0);
        });
        SceneManager.getInstance().initStage(stage);
        SceneManager.getInstance().loadView("homeServerView");
        stage.setTitle("Hello!");
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}