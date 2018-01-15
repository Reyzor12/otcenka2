package ru.eleron.osa.lris.otcenka.bussiness;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.otcenka.entities.ComputerName;
import ru.eleron.osa.lris.otcenka.entities.Department;

@Component
public class UserSession {

    public ComputerName computerName;

    public ComputerName getComputerName() {
        return computerName;
    }

    public void setComputerName(ComputerName computerName) {
        this.computerName = computerName;
    }
}
