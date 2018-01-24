package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "computerName")
public class ComputerName extends AbstractEntities {
    @NotNull(message = "computerName of ComputerName can't be null")
    @Column(name = "computerName",nullable = false)
    private String computerName;
    @NotNull(message = "department of ComputerName can't be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department",nullable = false)
    private Department department;

    public ComputerName(){super();}
    public ComputerName(Long id){super(id);}
    public ComputerName(String computerName, Department department){
        super();
        this.computerName = computerName;
        this.department = department;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComputerName that = (ComputerName) o;

        if (computerName != null ? !computerName.equals(that.computerName) : that.computerName != null) return false;
        return department != null ? department.equals(that.department) : that.department == null;
    }

    @Override
    public int hashCode() {
        int result = computerName != null ? computerName.hashCode() : 0;
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return computerName;
    }
}
