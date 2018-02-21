package ru.eleron.osa.lris.otcenka.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.MicrosoftReports;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.utilities.ConvertorForUse;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Controller for RoleHeadDepartmentMainFrame.fxml
 * Used to head of department main frame
 *
 * @author reyzor
 * @version 1.0
 * @since 19.02.2018
 * */

@Component
public class RoleHeadDepartmentMainFrameController {

    @Autowired
    private UserSession userSession;
    @Autowired
    private MessageGenerator messageGenerator;
    @Autowired
    private OpenReportDao<OpenReport> openReportDao;
    @Autowired
    private MicrosoftReports microsoftReports;

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

    /**
     * return report to responsible person with comment or without if report not correspond
     * */

    @FXML
    public void backChoosenOpenReport(){
        OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if (openReport == null) {
            messageGenerator.getWarningMessage("Не выбран НИОКР!");
        } else {
            Optional<ButtonType> option = messageGenerator.getConfirmMessage("Вы дейтсвительно хотите отправить НИОКР на доработку?");
            if (option.get().equals(ButtonType.OK)) {
                userSession.setChoosenOpenReport(openReport);
                SceneLoader.loadSceneInNewFrame("view/CommentToOpenReport.fxml");
                tableViewOpenReport.refresh();
            }
        }
    }

    /**
     * return all reports to responsible person with comment or without if report not correspond
     * */

    @FXML
    public void backAllOpenReport(){
        if (userSession.getChoosenOpenReport() != null) userSession.setChoosenOpenReport(null);
        Optional<ButtonType> option = messageGenerator.getConfirmMessage("Вы действительно хотите отправить все НИОКР'ы на доработку?");
        if (option.get().equals(ButtonType.OK)) {
            SceneLoader.loadSceneInNewFrame("view/CommentToOpenReport.fxml");
            tableViewOpenReport.refresh();
        }
    }
    @FXML
    public void clearSearch(){
        textFieldName.setText("");
        choiceBoxStatus.setValue(null);
        choiceBoxUser.setValue(null);
    }

    /**
     * Open docx document generate on chosen open report
     * */

    @FXML
    public void showChosenOpenReportWORD(){
        String path = getClass().getClassLoader().getResource("docs/template.docx").getPath();
        System.out.println(path);
        OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if(openReport == null) {
            System.out.println("NOT SELECTED");
            return;
        }
        try {
            XWPFDocument document = new XWPFDocument(OPCPackage.open(path));
            Map<String,String> replacer = new HashMap<>();
            replacer.put("{$hello}","world");
            //document = microsoftReports.replaceInDocument(replacer,document);
            //document = microsoftReports.fillDataFromOpenReport(openReport,document);
            String pathToSave = getClass().getClassLoader().getResource("docs/test1.docx").getPath();
            //microsoftReports.saveDocument(document,pathToSave);
//            Map<String,String> replacer = microsoftReports.replacerForOpenReport(openReport);
//            microsoftReports.replace(document,replacer);
            String pathReport = getClass().getClassLoader().getResource("docs/reporttempl.docx").getPath();
            microsoftReports.GenerateAndSaveReportUseTemplates(path,replacer,pathReport,Arrays.asList(openReport),pathToSave);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showAllOpenReportWORD(){}

    /**
     * Method change status of chosen report and update it in database
     * */

    @FXML
    public void sendChoosenOpenReport(){
        OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if (openReport == null) {
            messageGenerator.getWarningMessage("Не выбран НИКОР для отправки!");
        } else {
            Optional<ButtonType> option = messageGenerator.getConfirmMessage("Вы действительно хотите отправить выбранный НИКОР?");
            if (option.get().equals(ButtonType.OK)) {
                if (openReport.getStatus() == OpenReport.CONSIDERED) {
                    openReport.setStatus(OpenReport.REPORT_APPROVED);
                    openReportDao.update(openReport);
                    tableViewOpenReport.refresh();
                }
            }
        }
    }

    /**
     * Method change all status of open report with status considered on status approved and update it in database
     * */

    @FXML
    public void sendAllOpenReport() {
        Optional<ButtonType> option = messageGenerator.getConfirmMessage("Вы действительно хотите отправить все НИОКРЫ?");
        if (option.get().equals(ButtonType.OK)) {
            userSession.getOpenreportList().stream().filter(p -> p.getStatus() == OpenReport.CONSIDERED).forEach(p -> {
                p.setStatus(OpenReport.REPORT_APPROVED);
                openReportDao.update(p);
            });
            tableViewOpenReport.refresh();
        }

    }
    @FXML
    public void editChoosenOpenReport(){
        OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if (openReport == null) {
            messageGenerator.getWarningMessage("Не выбран не один НИОКР!");
        } else {
            userSession.setChoosenOpenReport(openReport);
            SceneLoader.loadScene("view/ViewOrEditOpenReportFrame.fxml");
        }
    }

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
