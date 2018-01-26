package ru.eleron.osa.lris.otcenka.service.dao;

import ru.eleron.osa.lris.otcenka.entities.User;

public interface ReportDao<Report> extends BaseOperationIF<Report> {
    Report getReportByLongNameAndOwner(String longName, User owner);
}
