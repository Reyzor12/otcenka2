package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name","secondName","lastName","department"})
})
public class User extends AbstractEntities {

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

    public User(){super();}
    public User(Long id){super(id);}
    public User(String name, String secondName, String lastName){
        super();
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
    }
    public User(String name, String secondName, String lastName, Department department){
        this(name,secondName,lastName);
        this.department = department;
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
}
