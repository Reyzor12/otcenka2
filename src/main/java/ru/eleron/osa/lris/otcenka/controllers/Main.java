package ru.eleron.osa.lris.otcenka.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.ComputerName;
import ru.eleron.osa.lris.otcenka.service.dao.BaseDataFromDB;
import ru.eleron.osa.lris.otcenka.service.dao.ComputerNameDao;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class Main {

    @Autowired
    UserSession userSession;

    @Autowired
    private ComputerNameDao<ComputerName>  computerNameDao;

    @Autowired
    private BaseDataFromDB baseDataFromDB;

    public void initialize(){

        try {
            userSession.setComputerName(computerNameDao.containsInDB(InetAddress.getLocalHost().getHostAddress()));
            userSession.setServerDate(baseDataFromDB.getServerData());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.currentThread().sleep(2000);
                if(userSession.getComputerName() != null) {
                    Platform.runLater(new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            SceneLoader.loadScene("view/UserLoginFrame.fxml");
                            return null;
                        }
                    });
                } else{
                    Platform.runLater(new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            SceneLoader.loadScene("view/FirstNewUser.fxml");
                            return null;
                        }
                    });
                }
                return null;
            }
        });
        t.setDaemon(true);
        t.start();

    }

}
