package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="department")
public class Department extends AbstractEntities {
    @NotNull(message = "department name is null")
    @Column(name = "name",unique = true)
    private String name;
    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    public Department(){super();};
    public Department(Long id){super(id);};
    public Department(String name){
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
