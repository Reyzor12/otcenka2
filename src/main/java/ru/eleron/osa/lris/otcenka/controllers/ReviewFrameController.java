package ru.eleron.osa.lris.otcenka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.Review;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

@Component
public class ReviewFrameController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserSession userSession;

    @Autowired
    private MessageGenerator messageGenerator;

    @Autowired
    private BaseOperationIF<Review> baseOperationReview;

    @FXML
    private TextArea textAreaReview;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    public void initialize(){
        setAllTooltips();
    }

    @FXML
    public void sendReview(ActionEvent event){
        if(textAreaReview.getText().trim().isEmpty()) {
            messageGenerator.getWarningMessage("Поле сообщения не заполнено1");
        } else {
            baseOperationReview.add(new Review(userSession.getComputerName().getDepartment(), userSession.getUser(), textAreaReview.getText()));
            messageGenerator.getInfoMessage("Отзыв успешно добавлен");
            back(event);
        }
    }

    @FXML
    public void back(ActionEvent event){
        userSession.toMainFrame(event);
    }

    private void setAllTooltips() {
        textAreaReview.setTooltip(new Tooltip("Поле ввода сообщения"));
        addButton.setTooltip(new Tooltip("Добавить запись"));
        cancelButton.setTooltip(new Tooltip("Отмена"));
    }
}
