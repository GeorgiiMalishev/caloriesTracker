package ru.georgy.NauJava.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "users_count")
    private Long usersCount;

    @Column(name = "products")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "user_count_time_ms")
    private Long userCountTimeMs;

    @Column(name = "product_list_time_ms")
    private Long productListTimeMs;

    @Column(name = "total_report_time_ms")
    private Long totalReportTimeMs;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Long usersCount) {
        this.usersCount = usersCount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserCountTimeMs() {
        return userCountTimeMs;
    }

    public void setUserCountTimeMs(Long userCountTimeMs) {
        this.userCountTimeMs = userCountTimeMs;
    }

    public Long getProductListTimeMs() {
        return productListTimeMs;
    }

    public void setProductListTimeMs(Long productListTimeMs) {
        this.productListTimeMs = productListTimeMs;
    }

    public Long getTotalReportTimeMs() {
        return totalReportTimeMs;
    }

    public void setTotalReportTimeMs(Long totalReportTimeMs) {
        this.totalReportTimeMs = totalReportTimeMs;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
