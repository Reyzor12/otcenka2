package ru.eleron.osa.lris.otcenka.utilities;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.springframework.stereotype.Component;

@Component
public class MessageGenerator {

    public static final String WARNING = "Предупреждение!";

    public void getWarningMessage(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(WARNING);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
