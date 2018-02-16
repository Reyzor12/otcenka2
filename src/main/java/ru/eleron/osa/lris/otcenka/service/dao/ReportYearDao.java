package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.ReportYear;

import java.util.List;

public interface ReportYearDao<T> extends BaseOperationIF<T> {
    ReportYear getYear(Integer year);
    List<ReportYear> getAfterYear(Integer year);
}
