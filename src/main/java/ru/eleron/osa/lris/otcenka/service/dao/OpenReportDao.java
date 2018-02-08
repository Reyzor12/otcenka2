package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.ReportYear;

import java.util.List;

public interface OpenReportDao<OpenReport> extends BaseOperationIF<OpenReport>{
    List<OpenReport> getListWithDepartments();
    List<OpenReport> getListWithDepartments(ReportYear reportYear, Integer month);
    List<OpenReport> getListWithDepartmentsCurrnetMonths();
}
