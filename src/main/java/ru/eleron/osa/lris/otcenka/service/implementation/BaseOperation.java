package ru.eleron.osa.lris.otcenka.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class BaseOperation<T> implements BaseOperationIF<T> {

    @PersistenceContext
    EntityManager entityManager;

    private static final Logger log = LogManager.getLogger();
    private Class<T> clazz;
    private String className;

    public BaseOperation(){}

    public BaseOperation(Class<T> clazz){
        this.clazz = clazz;
        this.className = clazz.getName();
    }

    @Override
    public void add(T object) {
        entityManager.persist(object);
        log.info("add " + className + " with object " + object);
    }

    @Override
    public void remove(T object) {
        entityManager.remove(object);
        log.info("remove " + className + " with object " + object);
    }

    @Override
    public void removeById(Long id) {
        entityManager.remove(entityManager.find(clazz,id));
        log.info("found " + className + " with id = " + id);
    }

    @Override
    public List<T> getList() {
        return entityManager.createQuery("from " + className).getResultList();
    }

    @Override
    public boolean update(T object) {
        return false;
    }
}
