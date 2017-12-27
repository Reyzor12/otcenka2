package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "review")
public class Review extends AbstractEntities {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department")
    private Department department;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;
    @Column(name = "text")
    private String text;
    public Review(){super();}
    public Review(Long id){super(id);}
    public Review(Department department, User user, String text){
        this.department = department;
        this.user = user;
        this.text = text;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (department != null ? !department.equals(review.department) : review.department != null) return false;
        if (user != null ? !user.equals(review.user) : review.user != null) return false;
        return text != null ? text.equals(review.text) : review.text == null;
    }

    @Override
    public int hashCode() {
        int result = department != null ? department.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return department + " " + user + " " + text;
    }
}
