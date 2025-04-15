package ru.georgy.NauJava.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT", name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
