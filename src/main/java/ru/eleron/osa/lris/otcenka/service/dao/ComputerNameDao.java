package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.ComputerName;

public interface ComputerNameDao<ComputerName> extends BaseOperationIF<ComputerName> {
    ComputerName containsInDB(String computerName);
}
