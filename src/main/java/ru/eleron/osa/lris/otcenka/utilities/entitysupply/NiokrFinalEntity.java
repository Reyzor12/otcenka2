package ru.eleron.osa.lris.otcenka.utilities.entitysupply;

public class NiokrFinalEntity {
    private String longName;
    private String deviation;
    private String problems;
    private String comment;
    private String perMonth;
    private String perYear;
    private String department;

    public NiokrFinalEntity(){}

    public NiokrFinalEntity(String longName, String deviation, String problems, String comment, String perMonth, String perYear, String department) {
        this.longName = longName;
        this.deviation = deviation;
        this.problems = problems;
        this.comment = comment;
        this.perMonth = perMonth;
        this.perYear = perYear;
        this.department = department;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPerMonth() {
        return perMonth;
    }

    public void setPerMonth(String perMonth) {
        this.perMonth = perMonth;
    }

    public String getPerYear() {
        return perYear;
    }

    public void setPerYear(String perYear) {
        this.perYear = perYear;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
