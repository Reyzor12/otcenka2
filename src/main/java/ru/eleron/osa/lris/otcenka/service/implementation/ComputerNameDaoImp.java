package ru.eleron.osa.lris.otcenka.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.entities.ComputerName;
import ru.eleron.osa.lris.otcenka.service.dao.ComputerNameDao;

import java.util.List;

@Component
public class ComputerNameDaoImp extends BaseOperation<ComputerName> implements ComputerNameDao<ComputerName> {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private SessionFactory sessionFactory;

    public ComputerNameDaoImp() {
        super(ComputerName.class);
    }

    @Override
    @Transactional
    public ComputerName containsInDB(String computerName) {
        List<ComputerName> list = sessionFactory.getCurrentSession().createQuery("FROM ComputerName WHERE computerName=:computerName")
                .setParameter("computerName",computerName).list();
        if(list.isEmpty())return null;
        if(list.size()>1){
            log.warn("Have to check DB");
        }
        return list.get(0);
    }
}
