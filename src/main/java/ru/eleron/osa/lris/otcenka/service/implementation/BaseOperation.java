package ru.eleron.osa.lris.otcenka.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@EnableTransactionManagement
public class BaseOperation<T> implements BaseOperationIF<T> {

    @PersistenceContext
    EntityManager entityManager;

    private static final Logger log = LogManager.getLogger();
    private Class<T> clazz;
    private String className;

    public BaseOperation(Class<T> clazz){
        this.clazz = clazz;
        this.className = clazz.getName();
    }

    @Override
    @Transactional
    public void add(T object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void remove(T object) {

    }

    @Override
    @Transactional
    public void removeById(Long id) {
        entityManager.remove(entityManager.find(clazz,id));
        log.info("found " + className + " with id = " + id);
    }

    @Override
    @Transactional
    public List<T> getList() {
        return null;
    }

    @Override
    @Transactional
    public boolean update(T object) {
        return false;
    }
}
