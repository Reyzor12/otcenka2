package ru.eleron.osa.lris.otcenka.service.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.entities.ReportYear;
import ru.eleron.osa.lris.otcenka.service.dao.ReportYearDao;

import java.util.List;

@Component
public class ReportYearDaoImp extends BaseOperation<ReportYear> implements ReportYearDao<ReportYear> {

    @Autowired
    SessionFactory sessionFactory;

    public ReportYearDaoImp() {
        super(ReportYear.class);
    }

    @Override
    @Transactional
    public ReportYear getYear(Integer year) {
        List<ReportYear> list = sessionFactory.getCurrentSession().createQuery("FROM ReportYear WHERE year = :year")
                .setParameter("year",year)
                .list();
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    @Transactional
    public List<ReportYear> getAfterYear(Integer year) {
        List<ReportYear> list = sessionFactory.getCurrentSession().createQuery("FROM ReportYear WHERE year >= :year")
                .setParameter("year",year)
                .list();
        return list;
    }
}
