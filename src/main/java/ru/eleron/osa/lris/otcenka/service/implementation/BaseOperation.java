package ru.eleron.osa.lris.otcenka.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;

import java.util.List;

@EnableTransactionManagement
public class BaseOperation<T> implements BaseOperationIF<T> {

    @Autowired
    SessionFactory sessionFactory;

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

        sessionFactory.getCurrentSession().persist(object);

    }

    @Override
    @Transactional
    public void remove(T object) {

    }

    @Override
    @Transactional
    public void removeById(Long id) {

    }

    @Override
    @Transactional
    public List<T> getList() {
        return (List<T>) sessionFactory.getCurrentSession().createQuery("FROM " + className).list();
    }

    @Override
    @Transactional
    public boolean update(T object) {
        return false;
    }
}
