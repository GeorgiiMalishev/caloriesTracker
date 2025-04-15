package ru.georgy.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.georgy.NauJava.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
