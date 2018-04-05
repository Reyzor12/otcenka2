package ru.eleron.osa.lris.otcenka.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;
import ru.eleron.osa.lris.otcenka.utilities.TextFieldUtil;



@Component
public class NewUserController {

    public static final Logger log = LogManager.getLogger();

    @Autowired
    private UserSession userSession;

    @Autowired
    private MessageGenerator messageGenerator;

    @Autowired
    private UserDao<User> userDao;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TextField textFieldLastname;

    @FXML
    private ChoiceBox<Department> choiceBoxDepartment;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    public void initialize(){
        choiceBoxDepartment.setItems(FXCollections.observableArrayList(userSession.getComputerName().getDepartment()));
        choiceBoxDepartment.setValue(userSession.getComputerName().getDepartment());
        choiceBoxDepartment.setDisable(true);
        TextFieldUtil.insertOnly(textFieldName,TextFieldUtil.CHAR_ONLY_REGEX);
        TextFieldUtil.insertOnly(textFieldSurname,TextFieldUtil.CHAR_ONLY_REGEX);
        TextFieldUtil.insertOnly(textFieldLastname,TextFieldUtil.CHAR_ONLY_REGEX);
        setAllTooltips();
    }

    public Boolean checkData(){
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        String lastname = textFieldLastname.getText();
        Department department = choiceBoxDepartment.getValue();
        if(name.equals("")||surname.equals("")||lastname.equals("")|| department == null){
            return false;
        }
        if(!userDao.getUserByData(name,surname,lastname,department).isEmpty()){
            return false;
        }
        return true;
    }

    @FXML
    public void addNewUser(ActionEvent event){

        if(checkData()){
            userDao.add(new User(textFieldName.getText(), textFieldSurname.getText(), textFieldLastname.getText(),choiceBoxDepartment.getValue()));
            userSession.setUsersOfDepartment(null);
            userSession.getUsersOfDepartment();
            back(event);
        } else{
            messageGenerator.getWarningMessage("Не все поля были заполнены или данный пользователь уже существует");
        }
    }

    @FXML
    public void back(ActionEvent event){
        userSession.toMainFrame(event);
    }

    private void setAllTooltips() {
        textFieldName.setTooltip(new Tooltip("Введите имя пользователя"));
        textFieldSurname.setTooltip(new Tooltip("Введите фамилию пользователя"));
        textFieldLastname.setTooltip(new Tooltip("Введите отчество пользователя"));
        addButton.setTooltip(new Tooltip("Добавить пользователя"));
        cancelButton.setTooltip(new Tooltip("Отмена"));
    }

}
