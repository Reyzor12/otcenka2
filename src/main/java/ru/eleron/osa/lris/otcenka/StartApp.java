package ru.eleron.osa.lris.otcenka;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;
import ru.eleron.osa.lris.otcenka.utilities.SpringFXMLLoader;

public class StartApp extends Application {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneLoader.setStage(primaryStage);
        SceneLoader.loadScene("view/Main.fxml");
    }
}
