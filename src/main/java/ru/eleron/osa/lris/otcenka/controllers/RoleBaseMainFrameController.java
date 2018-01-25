package ru.eleron.osa.lris.otcenka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

@Component
public class RoleBaseMainFrameController {

    @Autowired
    private UserSession userSession;

    @FXML
    private TableView<OpenReport> tableViewOpenReport;

    @FXML
    private TableColumn<OpenReport,String> tableColumnNameOfReport;

    @FXML
    private TableColumn<OpenReport,User> tableColumnOwnerOfReport;

    @FXML
    private TableColumn<OpenReport,Integer> tableColumnStatusOfReport;

    @FXML
    private TextField textFieldNameOfReport;

    @FXML
    private ChoiceBox<User> choiceBoxOwnerOfReport;

    @FXML
    private ChoiceBox<Integer> choiceBoxStatusOfReport;

    public void initialize(){
    }

    @FXML
    public void addNewUser(){
        SceneLoader.loadScene("view/NewUser.fxml");
    }

    @FXML
    public void addNewReview(){
        SceneLoader.loadScene("view/ReviewFrame.fxml");
    }

    @FXML
    public void changeUser(){
        SceneLoader.loadScene("view/UserLoginFrame.fxml");
    }

    @FXML
    public void addNewReport(){
        userSession.setChoosenOpenReport(null);
        SceneLoader.loadScene("view/NewReport.fxml");
    }
}
