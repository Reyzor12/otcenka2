package ru.eleron.osa.lris.otcenka.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

@Component
public class UserLoginFrameController {

    @Autowired
    private UserDao<User> userDao;

    @Autowired
    private UserSession userSession;

    @FXML
    private ListView<User> listView;

    public void initialize(){
        listView.setItems(FXCollections.observableArrayList(userDao.getUserByDepartment(userSession.getComputerName().getDepartment())));
    }

    @FXML
    public void exit(){
        SceneLoader.stage.close();
    }

    @FXML
    public void enter() {
        if (listView.getSelectionModel().getSelectedIndex() == -1) return;
        userSession.setUser(listView.getSelectionModel().getSelectedItem());
        switch (userSession.getUser().getRole()) {
            case 1: {
                SceneLoader.loadScene("view/RoleBaseMainFrame.fxml");
                break;
            }
            default: {
                //EROR HERE;
            }
        }
    }
}
