package ru.eleron.osa.lris.otcenka.service.implementation;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.entities.Report;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.ReportDao;

@Component
@EnableTransactionManagement
public class ReportDaoImp extends BaseOperation<Report> implements ReportDao<Report> {

    private final static String GET_REPORT_BY_LONG_NAME_AND_OWNER = "FROM Report WHERE fullName = :fullName AND responsible = :responsible";

    @Autowired
    private SessionFactory sessionFactory;

    public ReportDaoImp() {
        super(Report.class);
    }

    @Override
    @Transactional
    public Report getReportByLongNameAndOwner(String longName, User owner) {
        return (Report) sessionFactory.getCurrentSession().createQuery(GET_REPORT_BY_LONG_NAME_AND_OWNER)
                .setParameter("fullName",longName)
                .setParameter("responsible",owner).list().get(0);
    }

    @Override
    @Transactional
    public Report getReportWithAll(Report report) {
        Report reportDB =  get(report.getId());
        Hibernate.initialize(reportDB.getPerformers());
        return reportDB;
    }
}
