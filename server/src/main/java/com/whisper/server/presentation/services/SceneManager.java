package com.whisper.server.presentation.services;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SceneManager implements Initializable{
    private static final SceneManager instance = new SceneManager();

    private Stage primaryStage;

    private final Map<String, Scene> scenes = new HashMap<>();

    private final Map <String, Parent> panes = new HashMap<>();
    private final List<String> mustBeLoaded = new ArrayList<>();

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mustBeLoaded.add("statisticsView");
        mustBeLoaded.add("users-view");
    }

    public static SceneManager getInstance() {
        return instance;
    }
    public void initStage(Stage stage){
        if(primaryStage != null){
            throw new IllegalArgumentException("The Stage has already been initialized");
        }
        primaryStage = stage;
    }

    public void loadView(String name){
        if(primaryStage==null){
            throw new RuntimeException("Stage Coordinator should be " +
                    "initialized with a Stage before it could be used");
        }
        if(!scenes.containsKey(name)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getResource(String.format("/com/whisper/server/views/%s.fxml", name)));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
                scenes.put(name, scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        primaryStage.setScene(scenes.get(name));
    }
    public Parent loadPane(String name){
        if(primaryStage==null){
            throw new RuntimeException("Stage Coordinator should be " +
                    "initialized with a Stage before it could be used");
        }
        if(!panes.containsKey(name) || mustBeLoaded.contains(name)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getResource(String.format("/com/whisper/server/views/%s.fxml", name)));
                Parent root = fxmlLoader.load();
                panes.put(name, root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return panes.get(name);
    }


}
