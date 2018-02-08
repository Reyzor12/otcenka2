package ru.eleron.osa.lris.otcenka.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import ru.eleron.osa.lris.otcenka.utilities.ConvertorForUse;

@Component
public class RoleHeadDepartmentMainFrameController {

    @Autowired
    private UserSession userSession;

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
    private TableColumn<OpenReport,String> tableColumnUser;
    @FXML
    private TableColumn<OpenReport,String> tableColumnStatus;

    private ObservableList<OpenReport> observableListOpenReport;
    private FilteredList<OpenReport> filteredListOpenReport;
    private SortedList<OpenReport> sortedListOpenReport;

    public void initialize() {

        choiceBoxUser.setItems(FXCollections.observableArrayList(userSession.getUsersOfDepartment()));
        choiceBoxStatus.setItems(FXCollections.observableArrayList(ConvertorForUse.getAllStatusInString()));

        textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListOpenReport.setPredicate(openReport -> filterOpenReport(openReport));
        });
        choiceBoxStatus.setOnAction(event -> {
            filteredListOpenReport.setPredicate(openReport -> filterOpenReport(openReport));
        });
        choiceBoxUser.setOnAction(event -> {
            filteredListOpenReport.setPredicate(openReport -> filterOpenReport(openReport));
        });

        tableColumnName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getReport().getShortName()));
        tableColumnUser.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getReport().getResponsible().toString()));
        tableColumnStatus.setCellValueFactory(param -> new SimpleStringProperty(ConvertorForUse.convertStatusToString(param.getValue().getStatus())));

        observableListOpenReport = FXCollections.observableArrayList(userSession.getOpenreportList());
        filteredListOpenReport = new FilteredList<OpenReport>(observableListOpenReport, p -> true);
        sortedListOpenReport = new SortedList<OpenReport>(filteredListOpenReport);
        sortedListOpenReport.comparatorProperty().bind(tableViewOpenReport.comparatorProperty());
        tableViewOpenReport.setItems(sortedListOpenReport);
    }

    @FXML
    public void addNewUser(ActionEvent event) {
        userSession.addNewUserFrame(event);
    }
    @FXML
    public void addNewReview(ActionEvent event) {
        userSession.addNewReviewFrame(event);
    }
    @FXML
    public void changeCurrentUser(ActionEvent event) {
        userSession.changeUserFrame(event);
    }
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

    public Boolean filterOpenReport (OpenReport openReport) {
        if (
                textFieldName.getText().isEmpty() &&
                choiceBoxUser.getValue() == null &&
                choiceBoxStatus.getValue() == null
                ) {
            return true;
        } else if (
                openReport.getReport().getShortName().toLowerCase().contains(textFieldName.getText().toLowerCase()) &&
                (choiceBoxStatus.getValue() == null || openReport.getStatus().equals(choiceBoxStatus.getSelectionModel().getSelectedIndex()+1)) &&
                (choiceBoxUser.getValue() == null || openReport.getReport().getResponsible().equals(choiceBoxUser.getValue()))
                ) {
            return true;
        }
        return false;
    }
}
