package ru.eleron.osa.lris.otcenka.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;
import ru.eleron.osa.lris.otcenka.utilities.TextFieldUtil;

@Component
public class FirstNewUserController {

    @Autowired
    private BaseOperationIF<Department> departmentBaseOperation;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TextField textFieldLastname;

    @FXML
    private ChoiceBox<Department> choiceBoxDepartment;

    public void initialize(){
        choiceBoxDepartment.setItems(FXCollections.observableArrayList(departmentBaseOperation.getList()));
        TextFieldUtil.insertOnly(textFieldName,TextFieldUtil.CHAR_ONLY_REGEX);
        TextFieldUtil.insertOnly(textFieldSurname,TextFieldUtil.CHAR_ONLY_REGEX);
        TextFieldUtil.insertOnly(textFieldLastname,TextFieldUtil.CHAR_ONLY_REGEX);
    }

    @FXML
    public void exit(){
        SceneLoader.stage.close();
    }

    public Boolean checkData(){
        return false;
    }
}
