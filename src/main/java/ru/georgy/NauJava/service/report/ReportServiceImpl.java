package ru.georgy.NauJava.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.georgy.NauJava.config.ThymeleafHtmlRenderer;
import ru.georgy.NauJava.exception.EntityNotFoundException;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.model.Report;
import ru.georgy.NauJava.model.ReportStatus;
import ru.georgy.NauJava.repository.ProductRepository;
import ru.georgy.NauJava.repository.ReportRepository;
import ru.georgy.NauJava.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ThymeleafHtmlRenderer htmlRenderer;
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             UserRepository userRepository,
                             ProductRepository productRepository,
                             ThymeleafHtmlRenderer htmlRenderer) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.htmlRenderer = htmlRenderer;
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        Report savedReport = reportRepository.save(report);

        CompletableFuture.runAsync(
                () -> formReport(savedReport.getId()));

        return savedReport.getId();
    }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report", id));
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Report report = findById(id);
        reportRepository.delete(report);
    }

    @Override
    public void formReport(Long id) {
        long startTotalTime = System.currentTimeMillis();
        Report report = reportRepository.findById(id).get();
        class TimedResult<T> {
            final T result;
            final long durationMs;

            TimedResult(T result, long durationMs) {
                this.result = result;
                this.durationMs = durationMs;
            }
        }

        Future<TimedResult<Long>> userCountFuture = executorService.submit(() -> {
            long start = System.currentTimeMillis();
            Long count = userRepository.count();
            long duration = System.currentTimeMillis() - start;
            return new TimedResult<>(count, duration);
        });

        Future<TimedResult<List<Product>>> productListFuture = executorService.submit(() -> {
            long start = System.currentTimeMillis();
            List<Product> products = productRepository.findAll();
            long duration = System.currentTimeMillis() - start;
            return new TimedResult<>(products, duration);
        });

        try {
            TimedResult<Long> userCountResult = userCountFuture.get();
            TimedResult<List<Product>> productListResult = productListFuture.get();

            long totalDuration = System.currentTimeMillis() - startTotalTime;

            Map<String, Object> variables = new HashMap<>();
            variables.put("userCount", userCountResult.result);
            variables.put("userTime", userCountResult.durationMs);
            variables.put("productTime", productListResult.durationMs);
            variables.put("totalTime", totalDuration);
            variables.put("products", productListResult.result);

            String html = htmlRenderer.render("reportTemplate", variables);
            report.setContent(html);
            report.setStatus(ReportStatus.READY);

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            report.setStatus(ReportStatus.ERROR);
        } finally {
            System.out.println("Before save: " + report.getStatus());
            Report savedReport = reportRepository.save(report);
            System.out.println("After save: " + savedReport.getStatus());
        }
    }
}
