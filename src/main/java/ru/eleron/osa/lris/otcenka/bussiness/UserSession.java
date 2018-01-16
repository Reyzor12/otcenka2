package ru.eleron.osa.lris.otcenka.bussiness;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.ComputerName;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.entities.User;

@Component
public class UserSession {

    private ComputerName computerName;

    private User user;

    public ComputerName getComputerName() {
        return computerName;
    }

    public void setComputerName(ComputerName computerName) {
        this.computerName = computerName;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
