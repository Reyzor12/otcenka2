package ru.eleron.osa.lris.otcenka.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.bussiness.UserSession;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.ReportYear;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.utilities.entitysupply.NiokrFinalEntity;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for RoleNiokrHeadMainFrame.fxml
 *
 * @author reyzor
 * @version 1.0
 * @since 02.03.2018
 * */

@Component
public class RoleNiokrHeadMainFrameController {

    @Autowired
    private UserSession userSession;
    @Autowired
    private OpenReportDao<OpenReport> openReportDao;

    @FXML private ChoiceBox<ReportYear> choiceBoxYear;
    @FXML private ChoiceBox<Integer> choiceBoxMonth;
    @FXML private TableView<Department> tableViewDepartment;
    @FXML private TableColumn<Department,String> tableColumnDepartmentName;
    @FXML private TableColumn<Department,String> tableColumnDepartmentDoneWorks;
    @FXML private TableColumn<Department,String> tableColumnDepartmentAllWorks;
    @FXML private Label labelDepartmentDoneWork;
    @FXML private Label labelDepartmentNotDoneWork;
    @FXML private Label labelDepartmentAll;

    private Map<Department,List<Integer>> departmentStatisticMap;
    private ReportYear reportYear;
    private Integer month;

    public void initialize() {
        reportYear = userSession.getCurrentReportYear(userSession.getServerDate().getYear());
        month = userSession.getServerDate().getMonthValue();
        departmentStatisticMap = userSession.getDepartmentStatisticForDate(reportYear,month);

        labelDepartmentAll.setText(labelDepartmentAll.getText() + " " + departmentStatisticMap.keySet().size());
        labelDepartmentDoneWork.setText(labelDepartmentDoneWork.getText() + " " + doneWorkDepartmentCount());
        labelDepartmentNotDoneWork.setText(labelDepartmentNotDoneWork.getText() + " " + (departmentStatisticMap.keySet().size() - doneWorkDepartmentCount()));

        choiceBoxYear.setItems(FXCollections.observableArrayList(userSession.getReportYearList()));
        choiceBoxMonth.setItems(FXCollections.observableArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12)));

        tableColumnDepartmentName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        tableColumnDepartmentDoneWorks.setCellValueFactory(param -> new SimpleStringProperty(departmentStatisticMap.get(param.getValue()).get(0).toString()));
        tableColumnDepartmentAllWorks.setCellValueFactory(param -> new SimpleStringProperty(departmentStatisticMap.get(param.getValue()).get(1).toString()));

        choiceBoxMonth.setValue(month);
        choiceBoxYear.setValue(reportYear);
        choiceBoxMonth.setOnAction(event -> {
            departmentStatisticMap = userSession.getDepartmentStatisticForDate(choiceBoxYear.getValue(),choiceBoxMonth.getValue());
            tableViewDepartment.refresh();
        });
        choiceBoxYear.setOnAction(event -> {
            departmentStatisticMap = userSession.getDepartmentStatisticForDate(choiceBoxYear.getValue(),choiceBoxMonth.getValue());
            tableViewDepartment.refresh();
        });

        tableViewDepartment.setItems(FXCollections.observableArrayList(departmentStatisticMap.keySet()));
    }

    @FXML public void newUser(ActionEvent event) {
        userSession.addNewUserFrame(event);
    }
    @FXML public void newReview(ActionEvent event) {
        userSession.addNewReviewFrame(event);
    }
    @FXML public void changeUser(ActionEvent event) {
        userSession.changeUserFrame(event);
    }
    @FXML public void createWordForChosenDepartment() {

    }

    /**
     * Create all niokrs for all departments
     * */

    @FXML public void createWordForAllDepartment() {
        Map<String,Object> parameters = new HashMap();
        parameters.put("year",choiceBoxYear.getValue().getYear().toString());
        parameters.put("cDate",choiceBoxMonth.getValue().toString() + "." + choiceBoxYear.getValue().getYear());
/*
        List<NiokrFinalEntity> list = Arrays.asList(
                new NiokrFinalEntity("longName1","deviation1","problems1","comment1","perMonth1","perYear1","department1"),
                new NiokrFinalEntity("longName2","deviation2","problems2","comment2","perMonth2","perYear2","department1"),
                new NiokrFinalEntity("longName3","deviation3","problems3","comment3","perMonth3","perYear3","department2")
        );*/
        List<NiokrFinalEntity> list = openReportDao.getListNiokrFinalEntity(choiceBoxYear.getValue(), choiceBoxMonth.getValue());

        JRDataSource dataSource = new JRBeanCollectionDataSource(list);
        String path = getClass().getClassLoader().getResource("docs/AllDepartmentReports.jasper").getPath();

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(path,parameters, dataSource);
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            File file = new File(getClass().getClassLoader().getResource("docs/test1.docx").getPath());
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            exporter.exportReport();
            System.out.println("hello world");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class count amount of departments which send all niokrs in current month
     * @return amount of department with done work
     * */

    public Integer doneWorkDepartmentCount() {
        Integer result = 0;
        for(List<Integer> list : departmentStatisticMap.values()) {
            if (list.get(0).equals(list.get(1))) result += 1;
        }
        return result;
    }
}
