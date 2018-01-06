package ru.eleron.osa.lris.otcenka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.LoadData;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.service.implementation.BaseOperation;

import java.util.List;

@Component
public class Main {

    @FXML
    private ProgressBar progressBarLoadProgramm;

    private LoadData loadData;

    @Autowired
    private BaseOperationIF<Department> departmentBaseOperation;

    public void initialize(){
        startInit();
    }

    public void startInit(){
        loadData = new LoadData();
        progressBarLoadProgramm.progressProperty().bind(loadData.progressProperty());
        new Thread(loadData).start();
        System.out.println("start");
        Department department = new Department("hellow");
        System.out.println("create new department");
        departmentBaseOperation.add(department);
        System.out.println("add new department");
        List<Department> list = departmentBaseOperation.getList();
        System.out.println("get new list of department");
        department = list.get(0);
        department.setName("world");
        departmentBaseOperation.update(department);
        System.out.println("update department");
        departmentBaseOperation.remove(department);
        System.out.println("remove department");
    }
}
