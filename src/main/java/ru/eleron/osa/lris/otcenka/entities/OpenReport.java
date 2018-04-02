package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "openReport")
public class OpenReport extends AbstractEntities {
    @Column(name = "text")
    private String text;
    @Column(name = "problems")
    private String problems;
    @NotNull(message = "percentagePerMonth of OpenReport can't be null")
    @Max(value = 100)
    @Column(name = "percentagePerMonth",nullable = false)
    private Integer percentagePerMonth;
    @NotNull(message = "reportYear of OpenReport can't be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportYear",nullable = false)
    private ReportYear reportYear;
    @NotNull(message = "reportMonth of OpenReport can't be null")
    @Column(name = "reportMonth",nullable = false)
    private Integer reportMonth;
    @NotNull(message = "report of OpenReport can't be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report",nullable = false)
    private Report report;
    @Column(name = "comment")
    private String comment;
    @NotNull(message = "status of OpenReport can't be null")
    @Column(name = "status", nullable = false)
    private Integer status;

    public static final Integer NOT_FILL_REPORT = 2;
    public static final Integer FILL_REPORT = 3;
    public static final Integer REPORT_BACK_WITHOUT_COMMENT = 4;
    public static final Integer REPORT_BACK_WITH_COMMENT = 5;
    public static final Integer REPORT_APPROVED = 6;
    public static final Integer CONSIDERED = 7;

    public OpenReport(){super();}
    public OpenReport(Long id){super();}
    public OpenReport(ReportYear reportYear,Integer reportMonth,Report report){
        super();
        this.reportYear = reportYear;
        this.reportMonth = reportMonth;
        this.report = report;
        this.percentagePerMonth = 0;
        this.status = NOT_FILL_REPORT;
    }
    public OpenReport(String text, String problems, Integer percentagePerMonth, ReportYear reportYear,Integer reportMonth, Report report,Integer status){
        this(reportYear,reportMonth,report);
        this.text = text;
        this.problems = problems;
        this.percentagePerMonth = percentagePerMonth;
        this.status = status;
    }
    public OpenReport(String text,String problems,Integer percentagePerMonth,ReportYear reportYear,Integer reportMonth,Report report,String comment,Integer status){
        this(text,problems,percentagePerMonth,reportYear,reportMonth,report,status);
        this.comment = comment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public Integer getPercentagePerMonth() {
        return percentagePerMonth;
    }

    public void setPercentagePerMonth(Integer percentagePerMonth) {
        this.percentagePerMonth = percentagePerMonth;
    }

    public ReportYear getReportYear() {
        return reportYear;
    }

    public void setReportYear(ReportYear reportYear) {
        this.reportYear = reportYear;
    }

    public Integer getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(Integer reportMonth) {
        this.reportMonth = reportMonth;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpenReport that = (OpenReport) o;

        if (reportYear != null ? !reportYear.equals(that.reportYear) : that.reportYear != null) return false;
        if (reportMonth != null ? !reportMonth.equals(that.reportMonth) : that.reportMonth != null) return false;
        if (report != null ? !report.equals(that.report) : that.report != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = reportYear != null ? reportYear.hashCode() : 0;
        result = 31 * result + (reportMonth != null ? reportMonth.hashCode() : 0);
        result = 31 * result + (report != null ? report.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return report + " " + reportYear + " " + reportMonth;
    }
}
