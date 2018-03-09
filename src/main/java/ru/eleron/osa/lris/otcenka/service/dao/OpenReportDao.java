package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.entities.ReportYear;
import ru.eleron.osa.lris.otcenka.utilities.entitysupply.NiokrFinalEntity;

import java.util.List;

public interface OpenReportDao<OpenReport> extends BaseOperationIF<OpenReport>{
    List<OpenReport> getListWithDepartments();
    List<OpenReport> getListWithDepartments(ReportYear reportYear, Integer month);
    List<OpenReport> getListWithDepartmentsCurrnetMonths();
    List<NiokrFinalEntity> getListNiokrFinalEntity(ReportYear year, Integer month, Department department);
    List<NiokrFinalEntity> getListNiokrFinalEntity(ReportYear year, Integer month);
}
