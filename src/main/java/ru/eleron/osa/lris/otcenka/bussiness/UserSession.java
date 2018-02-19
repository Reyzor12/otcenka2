package ru.eleron.osa.lris.otcenka.bussiness;

import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.*;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;
import ru.eleron.osa.lris.otcenka.utilities.MessageGenerator;
import ru.eleron.osa.lris.otcenka.utilities.SceneLoader;

import java.util.Date;
import java.util.List;

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

    private Date serverDate;

    private ComputerName computerName;

    private User user;

    private List<OpenReport> openReportList;

    private OpenReport choosenOpenReport;

    private List<ReportYear> reportYearList;

    private List<User> usersOfDepartment;

    private Report report;

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

    public Date getServerDate() {
        return serverDate;
    }

    public void setServerDate(Date serverDate) {
        this.serverDate = serverDate;
    }

    public OpenReport getChoosenOpenReport() {
        return choosenOpenReport;
    }

    public void setChoosenOpenReport(OpenReport choosenOpenReport) {
        this.choosenOpenReport = choosenOpenReport;
    }

    public List<ReportYear> getReportYearList() {
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
        ReportYear y1 = new ReportYear(year);
        for(ReportYear y: getReportYearList()){
            if(y.equals(y1)) return y;
        }
        return null;
    }

    public void toMainFrame(ActionEvent event){
        switch(user.getRole()){
            case 1:
                SceneLoader.loadScene("view/RoleBaseMainFrame.fxml");break;
            case 2:
                SceneLoader.loadScene("view/RoleHeadDepartmentMainFrame.fxml");break;
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
