package ru.eleron.osa.lris.otcenka.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

@Component
public class UserLoginFrameController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserDao<User> userDao;

    @Autowired
    private UserSession userSession;

    @Autowired
    private MessageGenerator messageGenerator;

    @FXML
    private ListView<User> listView;
    public Button enterButton;
    public Button exitButton;

    public void initialize(){
        listView.setItems(FXCollections.observableArrayList(userSession.getUsersOfDepartment()));
        setAllTooltip();
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
            case 2: {
                SceneLoader.loadScene("view/RoleHeadDepartmentMainFrame.fxml");
                break;
            }
            case 3: {
                SceneLoader.loadScene("view/RoleNiokrHeadMainFrame.fxml");
                break;
            }
            default: {
                log.warn("User have undefined role id = " + userSession.getUser().getRole());
                messageGenerator.getWarningMessage("Пользователь имеет недопутимую роль, свяжитесь с разработчиками!");
            }
        }
    }

    private void setAllTooltip() {
        listView.setPlaceholder(new Label("Список пст"));
        listView.setTooltip(new Tooltip("Список пользователей"));
        enterButton.setTooltip(new Tooltip("Выбрать пользователя"));
        exitButton.setTooltip(new Tooltip("Выйти"));
    }
}
