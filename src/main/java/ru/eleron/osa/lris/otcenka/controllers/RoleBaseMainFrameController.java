package ru.eleron.osa.lris.otcenka.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.Report;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.service.dao.ReportDao;
import ru.eleron.osa.lris.otcenka.utilities.ConvertorForUse;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.util.List;

@Component
public class RoleBaseMainFrameController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private OpenReportDao<OpenReport> baseOperationOpenReport;

    @Autowired
    private ReportDao<Report> reportDao;

    @Autowired
    private MessageGenerator messageGenerator;

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
    @FXML
    private Button newUserButton;
    @FXML
    private Button newReviewButton;
    @FXML
    private Button changeUserButton;
    @FXML
    private Button newReporButton;
    @FXML
    private Button removeReportButton;
    @FXML
    private Button editReportButton;
    @FXML
    private Button fillReportButton;
    @FXML
    private Button sendReportButton;
    @FXML
    private Button clearButton;

    private FilteredList<OpenReport> filteredListOpenReport;

    private List<OpenReport> openReportList;

    public void initialize(){
        setAllTooltips();
        openReportList = userSession.getOpenreportList();
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
    public void addNewUser(ActionEvent event){
        userSession.addNewUserFrame(event);
    }

    @FXML
    public void addNewReview(ActionEvent event){
        userSession.addNewReviewFrame(event);
    }

    @FXML
    public void changeUser(ActionEvent event){ userSession.changeUserFrame(event); }

    @FXML
    public void addNewReport(){
        userSession.setChoosenOpenReport(null);
        SceneLoader.loadScene("view/NewReport.fxml");
    }

    @FXML
    public void editReport(){
        final OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if(openReport == null) {
            messageGenerator.getWarningMessage("Не выбран НИОКР для заполнения");
        }else{
            userSession.setChoosenOpenReport(openReport);
            SceneLoader.loadScene("view/NewReport.fxml");
        }
    }

    public Boolean filterOpenReport(OpenReport openReport){
        if(     textFieldNameOfReport.getText().equals("")&&
                choiceBoxOwnerOfReport.getValue() == null&&
                choiceBoxStatusOfReport.getValue() == null) {
            return true;
        }else if(

                openReport.getReport().getShortName().toLowerCase().contains(textFieldNameOfReport.getText().toLowerCase())&&
                (choiceBoxStatusOfReport.getSelectionModel().getSelectedIndex() == -1 || openReport.getStatus().equals(ConvertorForUse.getStatusId(choiceBoxStatusOfReport.getSelectionModel().getSelectedItem())))&&
                (choiceBoxOwnerOfReport.getValue() == null || openReport.getReport().getResponsible().equals(choiceBoxOwnerOfReport.getValue()))

                ) {
            return true;
        }
        return false;
    }

    @FXML
    public void deleteReport(){
        final OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if (openReport == null) {
            messageGenerator.getWarningMessage("Не выбран НИОКР для удаления");
        }else{
            reportDao.remove(openReport.getReport());
            userSession.setOpenreportList(baseOperationOpenReport.getListWithDepartments());
            tableViewOpenReport.setItems(FXCollections.observableArrayList(userSession.getOpenreportList()));
            messageGenerator.getInfoMessage("НИОКР успешно удален");
        }
    }

    @FXML
    public void sendOpenReport(){
        final OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if (openReport == null) {
           messageGenerator.getWarningMessage("Не выбран НИОКР для отправки");
        } else if (openReport.getStatus() != OpenReport.FILL_REPORT) {
            messageGenerator.getWarningMessage("НИОКР не заполнен");
        } else {
            openReport.setStatus(OpenReport.CONSIDERED);
            baseOperationOpenReport.update(openReport);
            userSession.getOpenreportList().set(tableViewOpenReport.getSelectionModel().getSelectedIndex(),openReport);
            //tableViewOpenReport.setItems(FXCollections.observableArrayList(userSession.getOpenreportList()));
            tableViewOpenReport.refresh();
        }
    }

    /**
     * Use for clear TextField and ChoiceBoxes
     * */

    @FXML
    public void clearSearch(){
        textFieldNameOfReport.clear();
        choiceBoxOwnerOfReport.setValue(null);
        choiceBoxStatusOfReport.setValue(null);
    }

    @FXML
    public void fillReport(){
        final OpenReport openReport = tableViewOpenReport.getSelectionModel().getSelectedItem();
        if (openReport == null) {
            messageGenerator.getWarningMessage("Не выбран НИОКР для заполнения");
        } else {
           userSession.setChoosenOpenReport(openReport);
           SceneLoader.loadScene("view/FillReportFrame.fxml");
        }
    }

    private void setAllTooltips() {
        newUserButton.setTooltip(new Tooltip("Добавить нового пользователя"));
        newReviewButton.setTooltip(new Tooltip("Добавить новый отзыв"));
        changeUserButton.setTooltip(new Tooltip("Смена пользователя"));
        clearButton.setTooltip(new Tooltip("Очистить поиск"));
        newReporButton.setTooltip(new Tooltip("Создать новый отчет"));
        removeReportButton.setTooltip(new Tooltip("Удалить отчет"));
        editReportButton.setTooltip(new Tooltip("Редактировать отчет"));
        fillReportButton.setTooltip(new Tooltip("Заполнить отчет"));
        sendReportButton.setTooltip(new Tooltip("Отправить отчет"));
        textFieldNameOfReport.setTooltip(new Tooltip("Введите название отчета"));
        choiceBoxStatusOfReport.setTooltip(new Tooltip("Выберете статус отчета"));
        choiceBoxOwnerOfReport.setTooltip(new Tooltip("Выберете ответственного за отчет"));
        tableViewOpenReport.setTooltip(new Tooltip("Табилца отчетов"));
        tableViewOpenReport.setPlaceholder(new Label("Таблица пуста"));
    }
}
