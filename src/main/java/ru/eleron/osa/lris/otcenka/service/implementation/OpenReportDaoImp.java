package ru.eleron.osa.lris.otcenka.service.implementation;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;

import java.util.List;

@Component
@EnableTransactionManagement
public class OpenReportDaoImp extends BaseOperation<OpenReport> implements OpenReportDao<OpenReport> {

    @Autowired
    private SessionFactory sessionFactory;

    public OpenReportDaoImp(){super(OpenReport.class);}

    @Override
    @Transactional
    public List<OpenReport> getListWithDepartments() {
        List<OpenReport> list = getList();
        for(OpenReport openReport : list){
            Hibernate.initialize(openReport.getReport());
        }
        return list;
    }
}
