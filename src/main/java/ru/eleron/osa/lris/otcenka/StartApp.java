package ru.eleron.osa.lris.otcenka;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.service.config.HibernateConfig;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

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
        primaryStage.setResizable(false);
        SceneLoader.setStage(primaryStage);
        SceneLoader.loadScene("view/Main.fxml");
    }
}
