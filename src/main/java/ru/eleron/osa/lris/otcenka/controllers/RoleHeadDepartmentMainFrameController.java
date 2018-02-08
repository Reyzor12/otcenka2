package ru.eleron.osa.lris.otcenka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.User;

@Component
public class RoleHeadDepartmentMainFrameController {

    @FXML
    private TextField textFieldName;
    @FXML
    private ChoiceBox<String> choiceBoxStatus;
    @FXML
    private ChoiceBox<User> choiceBoxUser;
    @FXML
    private TableView<OpenReport> tableViewOpenReport;
    @FXML
    private TableColumn<OpenReport,String> tableColumnName;
    @FXML
    private TableColumn<OpenReport,User> tableColumnUser;
    @FXML
    private TableColumn<OpenReport,String> tableColumnStatus;

    public void initialize(){}

    @FXML
    public void addNewUser(){}
    @FXML
    public void addNewReview(){}
    @FXML
    public void changeCurrentUser(){}
    @FXML
    public void backChoosenOpenReport(){}
    @FXML
    public void backAllOpenReport(){}
    @FXML
    public void clearSearch(){}
    @FXML
    public void showChoosenOpenReportWORD(){}
    @FXML
    public void showAllOpenReportWORD(){}
    @FXML
    public void rejectChoosenOpenReport(){}
    @FXML
    public void sendChoosenOpenReport(){}
    @FXML
    public void sendAllOpenReport(){}
    @FXML
    public void editChoosenOpenReport(){}
    @FXML
    public void viewChoosenOpenReport(){}
}
