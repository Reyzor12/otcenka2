package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="report")
public class Report extends AbstractEntities {

    @NotNull(message = "shortName of report can't be null")
    @Column(name = "shortName",nullable = false)
    private String shortName;
    @NotNull(message = "fullName of report can't be null")
    @Column(name = "fullName",nullable = false)
    private String fullName;
    @NotNull(message = "dateStart of report can't be null")
    @ManyToOne
    @JoinColumn(name = "dateStart",nullable = false)
    private ReportYear dateStart;
    @NotNull(message = "dateEnd of report can't be null")
    @ManyToOne
    @JoinColumn(name = "dateEnd",nullable = false)
    private ReportYear dateEnd;
    @NotNull(message = "responsible of report can't be null")
    @ManyToOne
    @JoinColumn(name = "responsible",nullable = false)
    private User responsible;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="report_user", joinColumns={@JoinColumn(name="report_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> performers;
    @NotNull(message = "department of report can't be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department",nullable = false)
    private Department department;
    @NotNull(message = "percentagePerYear of report can't be null")
    @Max(value=100)
    @Column(name = "percentagePerYear",nullable = false)
    private Integer percentagePerYear;
    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpenReport> openReportList;

    public Report(){super();};
    public Report(Long id){super(id);};
    public Report(String shortName, String fullName,ReportYear dateStart, ReportYear dateEnd,User responsible,Department department,Integer percentagePerYear){
        super();
        this.shortName = shortName;
        this.fullName = fullName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.responsible = responsible;
        this.department = department;
        this.percentagePerYear = percentagePerYear;
    }
    public Report(String shortName,String fullName,ReportYear dateStart,ReportYear dateEnd,User responsible,List<User> performers,Department department,Integer percentagePerYear){
        this(shortName,fullName,dateStart,dateEnd,responsible,department,percentagePerYear);
        this.performers = performers;
    }
    public Report(String shortName,String fullName,ReportYear dateStart,ReportYear dateEnd,User responsible,List<User> performers,Department department,Integer percentagePerYear,List<OpenReport> openReportList){
        this(shortName,fullName,dateStart,dateEnd,responsible,department,percentagePerYear);
        this.performers = performers;
        this.openReportList = openReportList;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ReportYear getDateStart() {
        return dateStart;
    }

    public void setDateStart(ReportYear dateStart) {
        this.dateStart = dateStart;
    }

    public ReportYear getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(ReportYear dateEnd) {
        this.dateEnd = dateEnd;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public List<User> getPerformers() {
        return performers;
    }

    public void setPerformers(List<User> performers) {
        this.performers = performers;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getPercentagePerYear() {
        return percentagePerYear;
    }

    public void setPercentagePerYear(Integer percentagePerYear) {
        this.percentagePerYear = percentagePerYear;
    }

    public List<OpenReport> getOpenReportList() {
        return openReportList;
    }

    public void setOpenReportList(List<OpenReport> openReportList) {
        this.openReportList = openReportList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (!shortName.equals(report.shortName)) return false;
        if (!fullName.equals(report.fullName)) return false;
        if (!responsible.equals(report.responsible)) return false;
        return department.equals(report.department);
    }

    @Override
    public int hashCode() {
        int result = shortName.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + responsible.hashCode();
        result = 31 * result + department.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return shortName;
    }
}
