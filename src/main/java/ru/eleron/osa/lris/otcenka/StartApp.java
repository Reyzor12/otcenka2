package ru.eleron.osa.lris.otcenka;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.awt.*;

@Component
public class StartApp extends Application {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
//        context.scan("ru.eleron.osa.lris.otcenka");
//        context.refresh();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setHeight(720);
        primaryStage.setWidth(1024);
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1024);
        primaryStage.setMaxHeight(1080);
        primaryStage.setMaxWidth(1920);
        primaryStage.setResizable(true);
        primaryStage.setTitle("ЭЛЕРОН Программа оценки НИОКР");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/css/img/icon.png")));
        SceneLoader.setStage(primaryStage);
        SceneLoader.loadScene("view/Main.fxml");
    }
}
