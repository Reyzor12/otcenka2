package ru.eleron.osa.lris.otcenka.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.ReportYear;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

@Component
public class NewReportController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserSession userSession;

    @Autowired
    private MessageGenerator messageGenerator;

    @Autowired
    private BaseOperationIF<ReportYear> baseOperationReportYear;

    @FXML
    private ChoiceBox<ReportYear> choiceBoxStart;

    @FXML
    private ChoiceBox<ReportYear> choiceBoxEnd;

    //private Integer timeFlag;

    public void initialize(){

        if(userSession.getReportYearList() == null){
            userSession.setReportYearList(baseOperationReportYear.getList());
        }

        choiceBoxEnd.setItems(FXCollections.observableArrayList(userSession.getReportYearList()));
        choiceBoxStart.setItems(FXCollections.observableArrayList(userSession.getReportYearList()));

        if(userSession.getChoosenOpenReport() == null){

        }else{
            //...
        }
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
        choiceBoxStart.setItems(FXCollections.observableArrayList(userSession.getReportYearList().subList(0,userSession.getReportYearList().indexOf(choiceBoxEnd.getValue()))));
    }

    @FXML
    public void startDate(){
        choiceBoxEnd.setItems(FXCollections.observableArrayList(userSession.getReportYearList().subList(userSession.getReportYearList().indexOf(choiceBoxEnd.getValue())+1,userSession.getReportYearList().size())));
    }
}
