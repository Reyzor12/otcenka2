package ru.eleron.osa.lris.otcenka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

/**
 * Contoller for FXML doc {@link view/FillReportFrame.fxml}
 *
 * @author reyzor
 * @version 1.0
 * @since 02.02.2018
 * */

@Controller
public class FillReportFrameController {

    @Autowired
    private UserSession userSession;

    @FXML
    private TextArea textAreaTextOfReport;
    @FXML
    private TextArea textAreaProblemsOfReport;
    @FXML
    private TextArea textAreaCommentOfReport;
    @FXML
    private Spinner<Integer> spinnerPersentageOfMonthReport;
    @FXML
    private CheckBox checkBoxDisableCommentOfReport;


    /**
     * First initialize method for loading data in controller
     * */

    public void initialize(){
        textAreaTextOfReport.setText(userSession.getChoosenOpenReport().getText());
        textAreaProblemsOfReport.setText(userSession.getChoosenOpenReport().getProblems());
        textAreaCommentOfReport.setText(userSession.getChoosenOpenReport().getComment());
        if(
                userSession.getChoosenOpenReport().getComment() == null ||
                userSession.getChoosenOpenReport().getComment().equals("")
          ){
            checkBoxDisableCommentOfReport.setDisable(true);
        }
        spinnerPersentageOfMonthReport.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
        if(userSession.getChoosenOpenReport().getPercentagePerMonth() != null) spinnerPersentageOfMonthReport.getValueFactory().setValue(userSession.getChoosenOpenReport().getPercentagePerMonth());
    }

    @FXML
    public void fillReport(ActionEvent event){}

    @FXML
    public void back(ActionEvent event) {
        userSession.toMainFrame(event);
    }


}
