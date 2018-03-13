package ru.eleron.osa.lris.otcenka.utilities;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Class for helping load the scene and show its on stage
 * It have methods for load on main stage or load on another stage
 *
 * @author reyzor
 * @version 1.0
 * @since 19.02.2018
 * */

public class SceneLoader {

    public static Stage stage;

    private static final Logger log = LogManager.getLogger();
    private static final SpringFXMLLoader loader = new SpringFXMLLoader();

    /**
     * Method load scene on main stage
     * @param url - path to load scene
     * */

    public static void loadScene(String url){
        if(stage == null) return;
        Parent root = (Parent) loader.load(url);
        stage.setScene(new Scene(root,1024,720));
        stage.show();
        log.info("scene " + url + " load");
    }
    public static void setStage(Stage stageArg){
        stage = stageArg;
    }

    /**
     * Method load scene on another stage without add delegate method
     * @param url - path to scene resource
     * */

    public static void loadSceneInNewFrame(String url) {
        Parent root = (Parent) loader.load(url);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 400, 250));
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initOwner(stage);
        log.info("scene " + url + " load in new Frame");
        newStage.showAndWait();
    }
}
