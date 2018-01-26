package ru.eleron.osa.lris.otcenka.utilities.entitysupply;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ru.eleron.osa.lris.otcenka.entities.User;

public class Person {

    private User user;

    private BooleanProperty check;

    public Person(User user){
        this.user = user;
        this.check = new SimpleBooleanProperty(false);
    }

    public Person(User user, Boolean check){
        this(user);
        this.check = new SimpleBooleanProperty(check);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BooleanProperty getCheck() {
        return check;
    }

    public boolean isCheck() {
        return check.get();
    }
    public BooleanProperty checkProperty() {
        return check;
    }
    public void setCheck(boolean check) {
        this.check.set(check);
    }
}
