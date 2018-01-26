package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.Department;

import java.util.List;

public interface UserDao<User> extends BaseOperationIF<User> {

    List<User> getUserByData(String name, String surname, String lastname, Department department);
    List<User> getUserByDepartment(Department department);
}
