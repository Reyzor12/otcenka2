package ru.eleron.osa.lris.otcenka.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.Report;
import ru.eleron.osa.lris.otcenka.entities.ReportYear;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.service.dao.ReportDao;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;
import ru.eleron.osa.lris.otcenka.service.implementation.BaseOperation;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;
import ru.eleron.osa.lris.otcenka.utilities.entitysupply.Person;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewReportController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserSession userSession;

    @Autowired
    private MessageGenerator messageGenerator;

    @Autowired
    private BaseOperationIF<ReportYear> baseOperationReportYear;

    @Autowired
    private UserDao<User> userDao;

    @Autowired
    private ReportDao<Report> reportDao;

    @Autowired
    private OpenReportDao<OpenReport> baseOperationOpenReport;

    @FXML
    private ChoiceBox<ReportYear> choiceBoxStart;

    @FXML
    private ChoiceBox<ReportYear> choiceBoxEnd;

    @FXML
    private Spinner<Integer> spinnerYearPersentage;

    @FXML
    private TableView<Person> tableViewUsers;

    @FXML
    private TableColumn<Person,String> tableColumnFIO;

    @FXML
    private TableColumn<Person,Boolean> tableColumnPart;

    @FXML
    private ChoiceBox<User> choiceBoxOwner;

    @FXML
    private TextField textFieldShort;

    @FXML
    private TextField textFieldLong;

    private Integer timeFlag;

    private Integer initValuePersentageOfYear;

    public void initialize(){

        if(userSession.getReportYearList() == null){
            userSession.setReportYearList(baseOperationReportYear.getList());
        }
        if(userSession.getUsersOfDepartment() == null){
            userSession.setUsersOfDepartment(userDao.getUserByDepartment(userSession.getComputerName().getDepartment()));
        }
        timeFlag = 1;
        choiceBoxEnd.setItems(FXCollections.observableArrayList(userSession.getReportYearList()));
        choiceBoxStart.setItems(FXCollections.observableArrayList(userSession.getReportYearList()));
        choiceBoxOwner.setItems(FXCollections.observableArrayList(userSession.getUsersOfDepartment()));
        tableColumnFIO.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Person, String> param) {
                return new SimpleStringProperty(param.getValue().getUser().toString());
            }
        });

        List<Person> persons = new ArrayList<>();
        if(userSession.getChoosenOpenReport() == null){
            initValuePersentageOfYear = 0;
            tableColumnPart.setCellValueFactory(new PropertyValueFactory<Person,Boolean>("check"));
            for(User user : userSession.getUsersOfDepartment()){
                persons.add(new Person(user));
            }
        }else{
            //initValuePersentageOfYear = 0;
        }
        tableColumnPart.setCellFactory(column -> new CheckBoxTableCell());
        spinnerYearPersentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,initValuePersentageOfYear));
        tableColumnPart.setEditable(true);

        tableViewUsers.setEditable(true);


        tableViewUsers.setItems(FXCollections.observableArrayList(persons));
    }

    @FXML
    public void back(){
        switch(userSession.getUser().getRole()){
            case 1: userSession.setChoosenOpenReport(null);SceneLoader.loadScene("view/RoleBaseMainFrame.fxml");break;
            default: log.warn("user with id = " + userSession.getUser().getId() + " has undefined role with id = " + userSession.getUser().getRole());
            messageGenerator.getInfoMessage("Пользователь с неправильно заданной ролью, обратитесь к разработчику программы");
        }
    }

    @FXML
    public void endDate(){
        if(timeFlag == 0)return;
        timeFlag = 0;
        ReportYear r = choiceBoxStart.getValue();
        choiceBoxStart.setItems(FXCollections.observableArrayList(userSession.getReportYearList().subList(0,userSession.getReportYearList().indexOf(choiceBoxEnd.getValue())+1)));
        choiceBoxStart.setValue(r);
        timeFlag = 1;
    }

    @FXML
    public void startDate(){
        if(timeFlag == 0)return;
        timeFlag = 0;
        ReportYear r = choiceBoxEnd.getValue();
        choiceBoxEnd.setItems(FXCollections.observableArrayList(userSession.getReportYearList().subList(userSession.getReportYearList().indexOf(choiceBoxStart.getValue()),userSession.getReportYearList().size())));
        choiceBoxEnd.setValue(r);
        timeFlag = 1;
    }

    @FXML
    public void addReport(){
        if(check()){
            if(userSession.getChoosenOpenReport() == null){
                List<User> users = new ArrayList<>();
                for(Person person : tableViewUsers.getItems()){
                    if(person.getCheck().getValue()) users.add(person.getUser());
                }
                Report report = new Report(textFieldShort.getText(),
                        textFieldLong.getText(),
                        choiceBoxStart.getValue(),
                        choiceBoxEnd.getValue(),
                        choiceBoxOwner.getValue(),
                        users,
                        userSession.getComputerName().getDepartment(),
                        spinnerYearPersentage.getValue()
                        );
                reportDao.add(report);
                report = reportDao.getReportByLongNameAndOwner(textFieldLong.getText(),choiceBoxOwner.getValue());
                OpenReport openReport = new OpenReport(userSession.getCurrentReportYear(userSession.getServerDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()),userSession.getServerDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue(),report);
                baseOperationOpenReport.add(openReport);
                messageGenerator.getInfoMessage("Репорт успешно добавлен");
                back();
            }
        }else{
            messageGenerator.getWarningMessage("Не все поля были заполнены!");
        }
    }

    public Boolean check(){
        if(textFieldShort.getText().isEmpty() ||
                textFieldLong.getText().isEmpty()||
                choiceBoxEnd.getValue() == null||
                choiceBoxStart.getValue() == null||
                choiceBoxOwner.getValue() == null){
            return false;
        }
        return true;
    }
}
