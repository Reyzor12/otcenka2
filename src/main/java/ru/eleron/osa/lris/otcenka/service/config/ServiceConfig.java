package ru.eleron.osa.lris.otcenka.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.eleron.osa.lris.otcenka.entities.*;
import ru.eleron.osa.lris.otcenka.service.dao.BaseOperationIF;
import ru.eleron.osa.lris.otcenka.service.implementation.BaseOperation;

@Configuration
public class ServiceConfig {

    @Bean
    public BaseOperationIF<ComputerName> getComputerNameBaseOperation(){
        return new BaseOperation<ComputerName>(ComputerName.class);
    }

    @Bean
    public BaseOperationIF<Department> getDepartmentBaseOperation(){
        return new BaseOperation<Department>(Department.class);
    }

    @Bean
    public BaseOperationIF<OpenReport> getOpenReportBaseOperation(){
        return new BaseOperation<OpenReport>(OpenReport.class);
    }

    @Bean
    public BaseOperationIF<Report> getReportBaseOperation(){
        return new BaseOperation<Report>(Report.class);
    }

    @Bean
    public BaseOperationIF<ReportYear> getReportYearBaseOperation(){
        return new BaseOperation<>(ReportYear.class);
    }

    @Bean
    public BaseOperationIF<Review> getReviewBaseOperation(){
        return new BaseOperation<>(Review.class);
    }

    @Bean
    public BaseOperationIF<User> getUserBaseOperation(){
        return new BaseOperation<>(User.class);
    }
}
