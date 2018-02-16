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

    public void initialize() {
        checkBoxComment.selectedProperty().addListener((observable,newValue,oldValue) -> textAreaComment.setEditable(newValue));
    }

    @FXML
    public void addComment(ActionEvent event) {
        OpenReport openReport = userSession.getChoosenOpenReport();
        if (checkBoxComment.isSelected()) {
            openReport.setStatus(OpenReport.REPORT_BACK_WITHOUT_COMMENT);
        } else {
            openReport.setStatus(OpenReport.REPORT_BACK_WITH_COMMENT);
            openReport.setComment(textAreaComment.getText());
        }
        openReportDao.update(openReport);
        back(event);
    }
    @FXML
    public void back(ActionEvent event) {
        userSession.setChoosenOpenReport(null);
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
}
