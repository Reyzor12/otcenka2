package ru.eleron.osa.lris.otcenka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;

@Component
public class ViewOrEditOpenReportFrameController {

    @Autowired
    private UserSession userSession;
    @Autowired
    private MessageGenerator messageGenerator;
    @Autowired
    private OpenReportDao<OpenReport> openReportDao;

    @FXML
    private TextArea textAreaText;
    @FXML
    private TextArea textAreaProblem;
    @FXML
    private Spinner<Integer> spinnerMonthPersentage;

    private OpenReport openReport;

    public void initialize() {
        openReport = userSession.getChoosenOpenReport();
        textAreaText.setText(openReport.getText());
        textAreaProblem.setText(openReport.getProblems());
        spinnerMonthPersentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,openReport.getPercentagePerMonth()));
    }

    @FXML
    public void back(ActionEvent event) {
        userSession.setChoosenOpenReport(null);
        userSession.toMainFrame(event);
    }
    @FXML
    public void updateOpenReport(ActionEvent event) {
        openReport.setText(textAreaText.getText());
        openReport.setProblems(textAreaProblem.getText());
        openReport.setPercentagePerMonth(spinnerMonthPersentage.getValue());
        openReportDao.update(openReport);
        messageGenerator.getInfoMessage("НИОКР был обновлен");
        userSession.setChoosenOpenReport(null);
        back(event);
    }

}
