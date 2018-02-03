package ru.eleron.osa.lris.otcenka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
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
    private TextField textFieldTextOfReport;
    @FXML
    private TextField textFieldProblemsOfReport;
    @FXML
    private TextField textFieldCommentOfReport;
    @FXML
    private Spinner<Integer> spinnerPersentageOfMonthReport;
    @FXML
    private CheckBox checkBoxDisableCommentOfReport;


    /**
     * First initialize method for loading data in controller
     * */

    public void initialize(){

    }

    @FXML
    public void fillReport(ActionEvent event){}

    @FXML
    public void back(ActionEvent event) {
        userSession.toMainFrame(event);
    }
}
