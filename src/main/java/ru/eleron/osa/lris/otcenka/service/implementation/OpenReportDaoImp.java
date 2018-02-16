package ru.eleron.osa.lris.otcenka.service.implementation;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.entities.OpenReport;
import ru.eleron.osa.lris.otcenka.entities.ReportYear;
import ru.eleron.osa.lris.otcenka.service.dao.BaseDataFromDB;
import ru.eleron.osa.lris.otcenka.service.dao.OpenReportDao;
import ru.eleron.osa.lris.otcenka.service.dao.ReportYearDao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@EnableTransactionManagement
public class OpenReportDaoImp extends BaseOperation<OpenReport> implements OpenReportDao<OpenReport> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private BaseDataFromDB baseDataFromDB;

    @Autowired
    private ReportYearDao<ReportYear> reportYearDao;

    public OpenReportDaoImp(){super(OpenReport.class);}

    @Override
    @Transactional
    public List<OpenReport> getListWithDepartments() {
        List<OpenReport> list = getList();
        for(OpenReport openReport : list){
            Hibernate.initialize(openReport.getReport());
            Hibernate.initialize(openReport.getReport().getResponsible());
        }
        return list;
    }

    @Override
    @Transactional
    public List<OpenReport> getListWithDepartments(ReportYear reportYear, Integer month) {
        List<OpenReport> openReportList = sessionFactory
                .getCurrentSession()
                .createQuery("FROM OpenReport WHERE reportYear = :reportYear and reportMonth = :reportMonth")
                .setParameter("reportYear",reportYear)
                .setParameter("reportMonth",month)
                .list();
        for(OpenReport openReport : openReportList){
            Hibernate.initialize(openReport.getReport());
            Hibernate.initialize(openReport.getReport().getResponsible());
        }
        return openReportList;
    }

    @Override
    @Transactional
    public List<OpenReport> getListWithDepartmentsCurrnetMonths() {
        Date currentDate = baseDataFromDB.getServerData();
        LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer currentM = currentLocalDate.getMonthValue();
        Integer previousM = currentM - 1 == 0 ? 12 : currentM - 1;
        ReportYear currentY = reportYearDao.getYear(currentLocalDate.getYear());
        List<ReportYear> reportYearList = reportYearDao.getAfterYear(currentLocalDate.getYear());

        NativeQuery query0 = sessionFactory.getCurrentSession()
                .createNativeQuery(
                        "update dbo.report " +
                          "set percentagePerYear = (percentagePerYear + percentagePerMonth) " +
                          "from dbo.report r, dbo.openReport o " +
                          "where r.dateEnd in :listY and o.report = r.id and o.reportMonth = :previousM and not exists( " +
                          "select 1 from dbo.openReport o1 " +
                          "where o1.report = r.id and o1.reportMonth = :currentM) "
                ).setParameter("listY",reportYearList)
                .setParameter("previousM",previousM)
                .setParameter("currentM",currentM);
        query0.executeUpdate();

        NativeQuery query = sessionFactory.getCurrentSession()
                .createNativeQuery(
                        "insert into dbo.openReport(status,report,reportYear,reportMonth,percentagePerMonth)" +
                                "select 2,r.id,:currentY,:currentM,0" +
                                "from dbo.report r " +
                                "where r.dateEnd in :listY and not exists( " +
                                "select 1 from dbo.openReport o where " +
                                "o.report = r.id and o.reportYear = :currentY and o.reportMonth = :currentM)"
                ).setParameter("currentY",currentY)
                .setParameter("currentM",currentM)
                .setParameter("listY",reportYearList);
        query.executeUpdate();
        return getListWithDepartments(currentY,currentM);
    }
}
