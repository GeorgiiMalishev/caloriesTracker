package ru.georgy.NauJava.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.georgy.NauJava.exception.EntityNotFoundException;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.model.Report;
import ru.georgy.NauJava.model.ReportStatus;
import ru.georgy.NauJava.repository.ProductRepository;
import ru.georgy.NauJava.repository.ReportRepository;
import ru.georgy.NauJava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        Report savedReport = reportRepository.save(report);

        CompletableFuture.runAsync(
                () -> formReport(savedReport)
        );

        return savedReport.getId();
    }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report", id));
    }

    @Override
    public List<Report> findAll() {
        return (List<Report>) reportRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Report report = findById(id);
        reportRepository.delete(report);
    }

    public void formReport(Report report) {
        AtomicReference<Long> userCount = new AtomicReference<>();
        AtomicReference<Long> userCountTimeMs = new AtomicReference<>();
        AtomicReference<List<Product>> products = new AtomicReference<>();
        AtomicReference<Long> productListTimeMs = new AtomicReference<>();

        long currentTime = System.currentTimeMillis();
        Thread userCountThread = new Thread(() -> {
            long startUserCountTime = System.currentTimeMillis();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            userCount.set(userRepository.count());
            userCountTimeMs.set(System.currentTimeMillis() - startUserCountTime);
        });

        Thread productListThread = new Thread(() -> {
            long startFindProductsTime = System.currentTimeMillis();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            products.set((List<Product>) productRepository.findAll());
            productListTimeMs.set(System.currentTimeMillis() - startFindProductsTime);
        });

        userCountThread.start();
        productListThread.start();

        try {
            userCountThread.join();
            productListThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            report.setStatus(ReportStatus.ERROR);
        }

        report.setUsersCount(userCount.get());
        report.setUserCountTimeMs(userCountTimeMs.get());
        report.setProducts(products.get());
        report.setProductListTimeMs(productListTimeMs.get());
        report.setStatus(ReportStatus.READY);
        report.setTotalReportTimeMs(System.currentTimeMillis() - currentTime);
        reportRepository.save(report);
    }
}
