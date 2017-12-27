package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reportYear")
public class ReportYear extends AbstractEntities {

    @Column(name = "year")
    private Integer year;

    public ReportYear(){super();}
    public ReportYear(Long id){super(id);}
    public ReportYear(Integer year){
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportYear that = (ReportYear) o;

        return year != null ? year.equals(that.year) : that.year == null;
    }

    @Override
    public int hashCode() {
        return year != null ? year.hashCode() : 0;
    }

    @Override
    public String toString(){
        return String.valueOf(year);
    }
}
