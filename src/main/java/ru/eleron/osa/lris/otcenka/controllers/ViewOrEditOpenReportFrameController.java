package ru.eleron.osa.lris.otcenka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public Button saveButton;
    public Button cancelButton;

    private OpenReport openReport;

    public void initialize() {
        openReport = userSession.getChoosenOpenReport();
        textAreaText.setText(openReport.getText());
        textAreaProblem.setText(openReport.getProblems());
        spinnerMonthPersentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,openReport.getPercentagePerMonth()));
        setAllTooltip();
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

    private void setAllTooltip() {
        textAreaText.setPromptText("Описание проделанной работы");
        textAreaProblem.setPromptText("Описание проблемы");
        textAreaText.setTooltip(new Tooltip("Описание проделанной работы"));
        textAreaProblem.setTooltip(new Tooltip("Описание проблемы"));
        spinnerMonthPersentage.setTooltip(new Tooltip("Процент выполнения за месяц"));
        saveButton.setTooltip(new Tooltip("Сохранить"));
        cancelButton.setTooltip(new Tooltip("Отмена"));
    }
}
