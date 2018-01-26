package ru.eleron.osa.lris.otcenka.bussiness;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.*;

import java.util.Date;
import java.util.List;

@Component
public class UserSession {

    private Date serverDate;

    private ComputerName computerName;

    private User user;

    private List<OpenReport> openreportList;

    private OpenReport choosenOpenReport;

    private List<ReportYear> reportYearList;

    private List<User> usersOfDepartment;

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
        return openreportList;
    }

    public void setOpenreportList(List<OpenReport> openreportList) {
        this.openreportList = openreportList;
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
}
