package ru.eleron.osa.lris.otcenka.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="report")
public class Report extends AbstractEntities {
    public Report(){super();};
    public Report(Long id){super(id);};
}
