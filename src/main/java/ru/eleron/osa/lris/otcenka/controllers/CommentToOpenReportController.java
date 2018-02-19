package ru.eleron.osa.lris.otcenka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for fxml file CommentToOpenReportController
 * main functions : edit Open Report changing status on edit with or without comments and add commnet (if required)
 *
 * @author reyzor
 * @version 1.0
 * @since 19.02.2018
 * */

@Component
public class CommentToOpenReportController {

    @Autowired
    private UserSession userSession;

    @Autowired
    private OpenReportDao<OpenReport> openReportDao;

    @FXML
    private TextArea textAreaComment;
    @FXML
    private CheckBox checkBoxComment;

    private List<OpenReport> openReportList;

    public void initialize() {
        checkBoxComment.selectedProperty().addListener((observable,newValue,oldValue) -> textAreaComment.setEditable(newValue));
    }

    /**
     * Method update Open Report (All Open Reports) changing status and add status if needed
     * @param event - event, that used than method required
     * */

    @FXML
    public void addComment(ActionEvent event) {
        OpenReport openReport = userSession.getChoosenOpenReport();
        if (openReport != null) {
            updateStatusOfOpenReport(openReport);
        } else {
            updateStatusOfAllOpenReport ();
        }
        back(event);
    }
    @FXML
    public void back(ActionEvent event) {
        userSession.setChoosenOpenReport(null);
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Method update status and comment of choosen openReport
     * @param openReport - choosen open report
     * */

    private void updateStatusOfOpenReport (OpenReport openReport) {
        if (checkBoxComment.isSelected()) {
            openReport.setStatus(OpenReport.REPORT_BACK_WITHOUT_COMMENT);
        } else {
            openReport.setStatus(OpenReport.REPORT_BACK_WITH_COMMENT);
            openReport.setComment(textAreaComment.getText());
        }
        openReportDao.update(openReport);
    }

    /**
     * Method update status and comment of all openReport with status send
     * */

    private void updateStatusOfAllOpenReport () {
        List<OpenReport> list = new ArrayList<>();
        userSession.getOpenreportList().stream().filter(s -> s.getStatus() == OpenReport.CONSIDERED).forEach(s -> list.add(s));
        if (list.isEmpty()) return;
        if (checkBoxComment.isSelected()) {
            list.stream().forEach(s -> s.setStatus(OpenReport.REPORT_BACK_WITHOUT_COMMENT));
        } else {
            list.stream().forEach(s -> {
                s.setStatus(OpenReport.REPORT_BACK_WITH_COMMENT);
                s.setComment(textAreaComment.getText());
                openReportDao.update(s);
            });
        }
    }
}
