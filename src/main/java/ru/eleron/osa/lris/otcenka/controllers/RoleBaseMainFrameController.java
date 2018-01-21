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
public class RoleBaseMainFrameController {

    @FXML
    private TableView<OpenReport> tableView;

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
    public void addNewUser(){}

    @FXML
    public void addNewReview(){}

    @FXML
    public void changeUser(){}
}
