package ru.eleron.osa.lris.otcenka.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

    private static final String DEFAULT_NAME = "";
    private static final User DEFAULT_USER = null;
    private static final Integer DEFAULT_STATUS = -1;

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

    private FilteredList<OpenReport> filteredListOpenReport;

    private List<OpenReport> openReportList;

    public void initialize(){
        openReportList = baseOperationOpenReport.getList();
        choiceBoxOwnerOfReport.setItems(FXCollections.observableArrayList(userSession.getUsersOfDepartment()));
        choiceBoxStatusOfReport.setItems(FXCollections.observableArrayList(ConvertorForUse.getAllStatusInString()));
        tableColumnNameOfReport.setCellValueFactory((param ->  new SimpleStringProperty(param.getValue().getReport().getShortName())));
        tableColumnOwnerOfReport.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getReport().getResponsible().toString()));
        tableColumnStatusOfReport.setCellValueFactory((param) -> new SimpleStringProperty(ConvertorForUse.convertStatusToString(param.getValue().getStatus())));
        tableColumnComment.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getComment()==null?"нет комментариев":"есть комментарий"));
        filteredListOpenReport = new FilteredList<>(FXCollections.observableArrayList(openReportList),p->true);
        SortedList<OpenReport> sortedListOpenReport = new SortedList<>(filteredListOpenReport);
        sortedListOpenReport.comparatorProperty().bind(tableViewOpenReport.comparatorProperty());
        tableViewOpenReport.setItems(sortedListOpenReport);

        textFieldNameOfReport.textProperty().addListener((observable,oldValue,newValue)->{
            filteredListOpenReport.setPredicate(openReport -> filterOpenReport(openReport));
        });
        choiceBoxStatusOfReport.setOnAction(event->{
            filteredListOpenReport.setPredicate(openReport -> filterOpenReport(openReport));
        });
        choiceBoxOwnerOfReport.setOnAction(event->{
            filteredListOpenReport.setPredicate(openReport -> filterOpenReport(openReport));
        });
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

    public Boolean filterOpenReport(OpenReport openReport){
        if(     textFieldNameOfReport.getText().equals(DEFAULT_NAME)&&
                choiceBoxOwnerOfReport.getValue() == null&&
                choiceBoxStatusOfReport.getSelectionModel().getSelectedIndex() == -1) {
            System.out.println(1);
            return true;
        }else if(

                openReport.getReport().getShortName().toLowerCase().contains(textFieldNameOfReport.getText().toLowerCase())||
                openReport.getStatus().equals(choiceBoxStatusOfReport.getSelectionModel().getSelectedIndex()+1)||
                openReport.getReport().getResponsible().equals(choiceBoxOwnerOfReport.getValue())

                ) {
            System.out.println(openReport.getReport().getShortName().toLowerCase().contains(textFieldNameOfReport.getText().toLowerCase()));
            System.out.println(openReport.getStatus().equals(choiceBoxStatusOfReport.getSelectionModel().getSelectedIndex()+1));
            System.out.println(openReport.getReport().getResponsible().equals(choiceBoxOwnerOfReport.getValue()));
            return true;
        }
        return false;
    }
}
