package ru.eleron.osa.lris.otcenka.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.utilities.ConvertorForUse;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.util.List;

@Component
public class RoleBaseMainFrameController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private BaseOperationIF<OpenReport> baseOperationOpenReport;

    @FXML
    private TableView<OpenReport> tableViewOpenReport;

    @FXML
    private TableColumn<OpenReport,String> tableColumnNameOfReport;

    @FXML
    private TableColumn<OpenReport,String> tableColumnOwnerOfReport;

    @FXML
    private TableColumn<OpenReport,String> tableColumnStatusOfReport;

    @FXML
    private TableColumn<OpenReport,String> tableColumnComment;

    @FXML
    private TextField textFieldNameOfReport;

    @FXML
    private ChoiceBox<User> choiceBoxOwnerOfReport;

    @FXML
    private ChoiceBox<String> choiceBoxStatusOfReport;

    private List<OpenReport> openReportList;

    public void initialize(){
        openReportList = baseOperationOpenReport.getList();
        choiceBoxOwnerOfReport.setItems(FXCollections.observableArrayList(userSession.getUsersOfDepartment()));
        choiceBoxStatusOfReport.setItems(FXCollections.observableArrayList(ConvertorForUse.getAllStatusInString()));
        tableColumnNameOfReport.setCellValueFactory((param ->  new SimpleStringProperty(param.getValue().getReport().getShortName())));
        tableColumnOwnerOfReport.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getReport().getResponsible().toString()));
        tableColumnStatusOfReport.setCellValueFactory((param) -> new SimpleStringProperty(ConvertorForUse.convertStatusToString(param.getValue().getStatus())));
        tableColumnComment.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getComment()==null?"нет комментариев":"есть комментарий"));
        tableViewOpenReport.setItems(FXCollections.observableArrayList(openReportList));
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
