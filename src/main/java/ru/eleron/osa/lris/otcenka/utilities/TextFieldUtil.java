package ru.eleron.osa.lris.otcenka.utilities;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class TextFieldUtil {

    public static final String CHAR_ONLY_REGEX = "[А-Я']{1}([а-я']){0,30}";

    public static void insertOnly(TextField textField, String regex){
        Pattern pattern = Pattern.compile(regex);
        textField.textProperty().addListener((observable,oldValue,newValue)->{
            if(pattern.matcher(newValue).matches()||newValue.isEmpty()){
                ((StringProperty)observable).setValue(newValue);
            }else{
                ((StringProperty)observable).setValue(oldValue);
            }
        });
    }
}
