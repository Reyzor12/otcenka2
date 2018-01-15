package ru.eleron.osa.lris.otcenka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.ComputerName;
import ru.eleron.osa.lris.otcenka.service.dao.ComputerNameDao;
import ru.eleron.osa.lris.otcenka.service.implementation.ComputerNameDaoImp;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class Main {

    @Autowired
    UserSession userSession;

    @Autowired
    private ComputerNameDao<ComputerName>  computerNameDao;

    public void initialize(){
        startInit();
    }

    public void startInit(){
        System.out.println(userSession.getClass());
        try {
            userSession.setComputerName(computerNameDao.containsInDB(InetAddress.getLocalHost().getHostAddress()));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(userSession.getComputerName() != null) {
            SceneLoader.loadScene("view/UserLoginFrame.fxml");
        } else{
            SceneLoader.loadScene("view/FirstNewUser.fxml");
        }
    }
}
