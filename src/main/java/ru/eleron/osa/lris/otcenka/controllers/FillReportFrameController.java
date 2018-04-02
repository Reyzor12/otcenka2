package ru.eleron.osa.lris.otcenka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;

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

    @Autowired
    private OpenReportDao<OpenReport> openReportDao;

    @Autowired
    private MessageGenerator messageGenerator;

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
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;


    /**
     * First initialize method for loading data in controller
     * */

    public void initialize(){
        textAreaTextOfReport.setText(userSession.getChoosenOpenReport().getText());
        textAreaProblemsOfReport.setText(userSession.getChoosenOpenReport().getProblems());
        textAreaCommentOfReport.setText(userSession.getChoosenOpenReport().getComment());
        textAreaCommentOfReport.setEditable(false);
        if(
                userSession.getChoosenOpenReport().getComment() == null ||
                userSession.getChoosenOpenReport().getComment().equals("")
          ){
            checkBoxDisableCommentOfReport.setDisable(true);
        }
        spinnerPersentageOfMonthReport.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
        if(userSession.getChoosenOpenReport().getPercentagePerMonth() != null) spinnerPersentageOfMonthReport.getValueFactory().setValue(userSession.getChoosenOpenReport().getPercentagePerMonth());
        setAllTooltip();
    }

    @FXML
    public void fillReport(ActionEvent event){
        OpenReport openReport = userSession.getChoosenOpenReport();
        if (constructOpenReport(openReport)) {
            openReport.setStatus(OpenReport.FILL_REPORT);
            openReportDao.update(openReport);
            messageGenerator.getInfoMessage("НИОКР был успешно заполнен");
            back(event);
        }
    }

    @FXML
    public void back(ActionEvent event) {
        userSession.toMainFrame(event);
    }

    private boolean constructOpenReport(OpenReport openReport){
        if (textAreaTextOfReport.getText() == null && textAreaProblemsOfReport.getText() == null) {
            messageGenerator.getWarningMessage("Не были заполнены поля");
            return false;
        }
        if (    !(openReport.getComment() == null ||
                openReport.getComment().isEmpty() ||
                openReport.getStatus() != OpenReport.REPORT_BACK_WITH_COMMENT)
           ) {
            if (checkBoxDisableCommentOfReport.isSelected()) {
                openReport.setComment(null);
            } else {
                messageGenerator.getWarningMessage("Не был учтен комментарий");
                return false;
            }
        }
        openReport.setText(textAreaTextOfReport.getText());
        openReport.setProblems(textAreaProblemsOfReport.getText());
        openReport.setPercentagePerMonth(spinnerPersentageOfMonthReport.getValue());
        return true;
    }

    private void setAllTooltip() {
        textAreaTextOfReport.setTooltip(new Tooltip("Тест проделанной работы"));
        textAreaProblemsOfReport.setTooltip(new Tooltip("Проблемные вопросы"));
        textAreaCommentOfReport.setTooltip(new Tooltip("Замечания от руководителя"));
        saveButton.setTooltip(new Tooltip("Сохранить"));
        cancelButton.setTooltip(new Tooltip("Отмена"));
        checkBoxDisableCommentOfReport.setTooltip(new Tooltip("Учесть комментарий руководителя"));
        spinnerPersentageOfMonthReport.setTooltip(new Tooltip("Процент выполнения за месяц"));
    }
}
