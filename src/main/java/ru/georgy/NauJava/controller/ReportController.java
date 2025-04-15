package ru.georgy.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.georgy.NauJava.model.Report;
import ru.georgy.NauJava.service.report.ReportService;

@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public String getReportsPage() {
        return "reportList";
    }

    @GetMapping("/reports/{id}")
    public String getReportDetail(@PathVariable Long id, Model model) {
        Report report = reportService.findById(id);
        model.addAttribute("report", report);
        return "reportDetail";
    }
}
