package ru.eleron.osa.lris.otcenka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.LoadData;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.service.implementation.BaseOperation;

@Component
public class Main {

    @FXML
    private ProgressBar progressBarLoadProgramm;

    private LoadData loadData;

    public void initialize(){
        startInit();
    }

    public void startInit(){
        loadData = new LoadData();
        progressBarLoadProgramm.progressProperty().bind(loadData.progressProperty());
        new Thread(loadData).start();
    }
}
