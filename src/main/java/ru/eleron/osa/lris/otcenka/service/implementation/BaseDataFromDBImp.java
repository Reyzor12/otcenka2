package ru.eleron.osa.lris.otcenka.service.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.service.dao.BaseDataFromDB;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@EnableTransactionManagement
public class BaseDataFromDBImp implements BaseDataFromDB {

//    private static final String CURRENT_SERVER_DATE = "SELECT CONVER(date, GETDATE())";
    private static final String CURRENT_SERVER_DATE = "SELECT getdate() as date";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public LocalDate getServerData() {
        return ((Date)(sessionFactory.getCurrentSession().createNativeQuery(CURRENT_SERVER_DATE)).getResultList().get(0)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
