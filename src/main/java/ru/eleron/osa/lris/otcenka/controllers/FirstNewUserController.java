package ru.eleron.osa.lris.otcenka.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.ComputerName;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.service.dao.ComputerNameDao;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;
import ru.eleron.osa.lris.otcenka.utilities.TextFieldUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class FirstNewUserController {

    @Autowired
    private BaseOperationIF<Department> departmentBaseOperation;

    @Autowired
    private UserDao<User> userDao;

    @Autowired
    private MessageGenerator messageGenerator;

    @Autowired
    private UserSession userSession;

    @Autowired
    private ComputerNameDao<ComputerName> computerNameDao;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TextField textFieldLastname;

    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<Department> choiceBoxDepartment;

    private User newUser;

    public void initialize(){
        choiceBoxDepartment.setItems(FXCollections.observableArrayList(departmentBaseOperation.getList()));
        TextFieldUtil.insertOnly(textFieldName,TextFieldUtil.CHAR_ONLY_REGEX);
        TextFieldUtil.insertOnly(textFieldSurname,TextFieldUtil.CHAR_ONLY_REGEX);
        TextFieldUtil.insertOnly(textFieldLastname,TextFieldUtil.CHAR_ONLY_REGEX);
        setAllTooltip();
    }

    @FXML
    public void exit(){
        SceneLoader.stage.close();
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
    public void saveUser(){
        if(checkData()){
            try {
                computerNameDao.add(new ComputerName(InetAddress.getLocalHost().getHostAddress(),choiceBoxDepartment.getValue()));
                userSession.setComputerName(computerNameDao.containsInDB(InetAddress.getLocalHost().getHostAddress()));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            userDao.add(new User(textFieldName.getText(), textFieldSurname.getText(), textFieldLastname.getText(),choiceBoxDepartment.getValue()));
            userSession.setUser(userDao.getUserByData(textFieldName.getText(),textFieldSurname.getText(),textFieldLastname.getText(),choiceBoxDepartment.getValue()).get(0));
            SceneLoader.loadScene("view/RoleBaseMainFrame.fxml");
        } else{
            messageGenerator.getWarningMessage("Не все поля были заполнены или данный пользователь уже существует");
        }
    }

    private void setAllTooltip() {
        addButton.setTooltip(new Tooltip("Добавить пользователя"));
        cancelButton.setTooltip(new Tooltip("Отмена"));
        textFieldName.setTooltip(new Tooltip("Имя пользователя"));
        textFieldSurname.setTooltip(new Tooltip("Фамилия пользователя"));
        textFieldLastname.setTooltip(new Tooltip("Отчество пользователя"));
    }
}
