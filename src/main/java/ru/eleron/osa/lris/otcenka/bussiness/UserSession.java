package ru.eleron.osa.lris.otcenka.bussiness;

import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.*;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserSession class is kind of cache of user session
 * This class have some common tools for any kind of user with various level of access
 *
 * @author reyzor
 * @version 1.0
 * @since 19.02.2018
 * */

@Component
public class UserSession {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private MessageGenerator messageGenerator;
    @Autowired
    private OpenReportDao<OpenReport> openReportDao;
    @Autowired
    private UserDao<User> userDao;
    @Autowired
    private BaseOperationIF<ReportYear> baseOperationReportYear;
    @Autowired
    private BaseOperationIF<Department> baseOperationDepartment;

    private LocalDate serverDate;
    private ComputerName computerName;
    private User user;
    private List<OpenReport> openReportList;
    private OpenReport choosenOpenReport;
    private List<ReportYear> reportYearList;
    private List<User> usersOfDepartment;
    private Report report;
    private Map<ReportYear,Map<Integer,Map<Department,List<Integer>>>> allDepartmentsStatistic;
    private List<Department> departmentList;

    public ComputerName getComputerName() {
        return computerName;
    }
    public void setComputerName(ComputerName computerName) {
        this.computerName = computerName;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    public List<OpenReport> getOpenreportList() {
        if (openReportList == null) {
            openReportList = openReportDao.getListWithDepartmentsCurrnetMonths();
        }
        return openReportList;
    }

    public void setOpenreportList(List<OpenReport> openReportList) {
        this.openReportList = openReportList;
    }
    public LocalDate getServerDate() {
        return serverDate;
    }
    public void setServerDate(LocalDate serverDate) {
        this.serverDate = serverDate;
    }
    public OpenReport getChoosenOpenReport() {
        return choosenOpenReport;
    }
    public void setChoosenOpenReport(OpenReport choosenOpenReport) {
        this.choosenOpenReport = choosenOpenReport;
    }
    public List<ReportYear> getReportYearList() {
        if(reportYearList == null) reportYearList = baseOperationReportYear.getList();
        return reportYearList;
    }
    public void setReportYearList(List<ReportYear> reportYearList) {
        this.reportYearList = reportYearList;
    }

    public List<User> getUsersOfDepartment() {
        if(usersOfDepartment == null) {
            usersOfDepartment = userDao.getUserByDepartment(getComputerName().getDepartment());
        }
        return usersOfDepartment;
    }

    public void setUsersOfDepartment(List<User> usersOfDepartment) {
        this.usersOfDepartment = usersOfDepartment;
    }

    public ReportYear getCurrentReportYear(Integer year){
        for(ReportYear y: getReportYearList()){
            if(y.getYear().equals(year)) return y;
        }
        return null;
    }

    public List<Department> getDepartmentList() {
        if(departmentList == null) this.departmentList = baseOperationDepartment.getList();
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    /**
     * Get List of departments statistic for set reportYear and month
     *
     * @param reportYear - statistic for this year
     * @param month - statistic for this month
     * @return - list of classes of  department's statistic
     * */

    public Map<Department,List<Integer>> getDepartmentStatisticForDate(ReportYear reportYear, Integer month) {
        if (allDepartmentsStatistic == null) initAllDepartmentsStatistic();
        Map<Department,List<Integer>> departmentStatisticMap = allDepartmentsStatistic.get(reportYear).get(month);
        if (departmentStatisticMap == null) {
            departmentStatisticMap = new HashMap<>();
            for(Department department : getDepartmentList()) {
                departmentStatisticMap.put(department, Arrays.asList(0,0));
            }
            List<OpenReport> openReportList = openReportDao.getListWithDepartments(reportYear, month);
            for (OpenReport openReport : openReportList) {
                List<Integer> list = departmentStatisticMap.get(openReport.getReport().getDepartment());
                Integer i = list.get(1);
                i += 1;
                list.set(1,i);
                if (openReport.getStatus().equals(OpenReport.REPORT_APPROVED)) {
                    Integer j = list.get(0);
                    j += 1;
                    list.set(0,j);
                }
            }
            allDepartmentsStatistic.get(reportYear).put(month,departmentStatisticMap);
        }
        return departmentStatisticMap;
    }

    /**
     * Init parameter allDepartmentsStatistic
     * */

    private void initAllDepartmentsStatistic() {
        allDepartmentsStatistic = new HashMap<>();
        for(ReportYear year : reportYearList) {
            Map<Integer,Map<Department,List<Integer>>> map = new HashMap<>();
            for(int i = 1; i < 13; i++) {
                map.put(i,null);
            }
            allDepartmentsStatistic.put(year,map);
        }
    }

    public void toMainFrame(ActionEvent event){
        switch(user.getRole()){
            case 1:
                SceneLoader.loadScene("view/RoleBaseMainFrame.fxml");break;
            case 2:
                SceneLoader.loadScene("view/RoleHeadDepartmentMainFrame.fxml");break;
            case 3:
                SceneLoader.loadScene("view/RoleNiokrHeadMainFrame.fxml");break;
            default:
                log.warn("User with id = " + user.getId() + " has undefined role = " + user.getRole());
                messageGenerator.getWarningMessage("Пользователь имеет не правильные права доступа, обратитись к разработчику программы");
        }
    }

    public void addNewUserFrame(ActionEvent event) {
        SceneLoader.loadScene("view/NewUser.fxml");
    }

    public void addNewReviewFrame(ActionEvent event) {
        SceneLoader.loadScene("view/ReviewFrame.fxml");
    }

    public void changeUserFrame(ActionEvent event) {
        SceneLoader.loadScene("view/UserLoginFrame.fxml");
    }
}
