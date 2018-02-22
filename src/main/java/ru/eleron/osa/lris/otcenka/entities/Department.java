package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="department")
public class Department extends AbstractEntities {
    @NotNull(message = "head of department don't set")
    @Column(name = "department_head")
    private String departmentHead;
    @NotNull(message = "department name is null")
    @Column(name = "name",unique = true,nullable = false)
    private String name;
    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users1;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    public Department(){super();};
    public Department(Long id){super(id);};
    public Department(String name, String departmentHead){
        super();
        this.name = name;
        this.departmentHead = departmentHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users1;
    }

    public void setUsers(List<User> users) {
        this.users1 = users;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString(){
        return name;
    }
}
