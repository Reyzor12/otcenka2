package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.ComputerName;

public interface ComputerNameDao<ComputerName> extends BaseOperationIF<ComputerName> {
    public ComputerName containsInDB(String computerName);
}
