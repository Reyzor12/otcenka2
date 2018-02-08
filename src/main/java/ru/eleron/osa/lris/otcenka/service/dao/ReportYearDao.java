package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.ReportYear;

public interface ReportYearDao<T> extends BaseOperationIF<T> {
    ReportYear getYear(Integer year);
}
