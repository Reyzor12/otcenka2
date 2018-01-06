package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.AbstractEntities;

import java.util.List;

public interface BaseOperationIF<T extends AbstractEntities> {
    void add(T object);
    void remove(T object);
    void removeById(Long id);
    List<T> getList();
    boolean update(T object);
}
