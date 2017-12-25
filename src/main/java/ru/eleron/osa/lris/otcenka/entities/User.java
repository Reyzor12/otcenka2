package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name","secondName","lastName","department"})
})
public class User extends AbstractEntities {

    public static final Integer BASE_ROLE = 1;
    public static final Integer HEAD_DEPARTMENT_ROLE = 2;
    public static final Integer HEAD_ROLE = 3;

    @NotNull(message = "name of user is null")
    @Size(max = 30, message = "user name can't be more than 30 chars")
    @Column(name = "name")
    private String name;
    @NotNull(message = "second name of user is null")
    @Size(max = 30, message = "user second name can't be more than 30 chars")
    @Column(name = "secondName")
    private String secondName;
    @NotNull(message = "last name of user is null")
    @Size(max = 30, message = "user last name can't be more than 30 chars")
    @Column(name = "lastName")
    private String lastName;
    @NotNull(message = "department of user is null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department",referencedColumnName = "id")
    private Department department;
    @NotNull(message = "role of user is null")
    @Column(name = "role")
    private Integer role;

    public User(){super();}
    public User(Long id){super(id);}
    public User(String name, String secondName, String lastName){
        super();
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.role = HEAD_ROLE;
    }
    public User(String name, String secondName, String lastName, Department department){
        this(name,secondName,lastName);
        this.department = department;
    }
    public User(String name, String secondName, String lastName, Department department, Integer role){
        this(name,secondName,lastName,department);
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
