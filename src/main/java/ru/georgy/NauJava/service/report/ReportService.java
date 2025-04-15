package ru.georgy.NauJava.service.report;

import ru.georgy.NauJava.model.Report;
import java.util.List;

public interface ReportService {
    Long createReport();
    Report findById(Long id);
    List<Report> findAll();
    void formReport(Long id);
    void delete(Long id);
}
