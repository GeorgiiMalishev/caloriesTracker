package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.georgy.NauJava.model.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
