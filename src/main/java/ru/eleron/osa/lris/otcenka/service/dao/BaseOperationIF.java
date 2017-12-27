package ru.eleron.osa.lris.otcenka.service.dao;

import java.util.List;

public interface BaseOperationIF<T> {
    void add(T object);
    void remove(T object);
    void removeById(Long id);
    List<T> getList();
    boolean update(T object);
}
