package ru.eleron.osa.lris.otcenka.service.dao;

import java.util.List;

public interface OpenReportDao<OpenReport> extends BaseOperationIF<OpenReport>{
    List<OpenReport> getListWithDepartments();
}
