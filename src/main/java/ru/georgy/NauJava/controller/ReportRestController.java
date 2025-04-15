package ru.georgy.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.georgy.NauJava.model.Report;
import ru.georgy.NauJava.service.report.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {
    private final ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public Long createReport() {
        return reportService.createReport();
    }

    @GetMapping("/{id}")
    public Report findReport(@PathVariable Long id) {
        return reportService.findById(id);
    }

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id){
        reportService.delete(id);
    }
}
