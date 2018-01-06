package ru.eleron.osa.lris.otcenka.service.implementation;

import org.springframework.context.annotation.Configuration;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;

@Configuration
public class DepartmentBaseOperation extends BaseOperation implements BaseOperationIF{
    public DepartmentBaseOperation(){
        super(Department.class);
    }
}
