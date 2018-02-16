package ru.eleron.osa.lris.otcenka.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageGenerator {

    public static final String WARNING = "Предупреждение!";
    public static final String INFO = "Информация";

    public void getWarningMessage(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(WARNING);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void getInfoMessage(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(INFO);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public Optional<ButtonType> getConfirmMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(INFO);
        alert.setHeaderText(text);
        alert.setContentText(null);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert.showAndWait();
    }
}
