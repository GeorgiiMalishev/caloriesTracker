package ru.georgy.NauJava.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Component;

@Component
public class ActuatorCommand {

    private final HealthEndpoint healthEndpoint;
    private final MetricsEndpoint metricsEndpoint;

    @Autowired
    public ActuatorCommand(HealthEndpoint healthEndpoint, MetricsEndpoint metricsEndpoint) {
        this.healthEndpoint = healthEndpoint;
        this.metricsEndpoint = metricsEndpoint;
    }

    public void showHealth() {
        HealthComponent health = healthEndpoint.health();
        System.out.println("Состояние приложения: " + health.getStatus());
    }

    public void showInfo() {
        System.out.println("===== ОСНОВНЫЕ МЕТРИКИ ПРИЛОЖЕНИЯ =====");

        MetricsEndpoint.MetricDescriptor cpuUsage = metricsEndpoint.metric("process.cpu.usage", null);
        if (cpuUsage != null && !cpuUsage.getMeasurements().isEmpty()) {
            double value = cpuUsage.getMeasurements().getFirst().getValue() * 100;
            System.out.println("Использование CPU: " + value + "%");
        }

        MetricsEndpoint.MetricDescriptor memoryUsed = metricsEndpoint.metric("jvm.memory.used", null);
        MetricsEndpoint.MetricDescriptor memoryMax = metricsEndpoint.metric("jvm.memory.max", null);

        if (memoryUsed != null && !memoryUsed.getMeasurements().isEmpty()) {
            double used = memoryUsed.getMeasurements().getFirst().getValue() / (1024 * 1024);
            System.out.println("Используемая память: " + used + " МБ");

            if (memoryMax != null && !memoryMax.getMeasurements().isEmpty()) {
                double max = memoryMax.getMeasurements().getFirst().getValue() / (1024 * 1024);
                if (max > 0) {
                    double percentage = (used / max) * 100;
                    System.out.println("Максимальная память: " + max + " МБ");
                    System.out.println("Использовано памяти: " + percentage + "%");
                }
            }
        }

        MetricsEndpoint.MetricDescriptor threads = metricsEndpoint.metric("jvm.threads.live", null);
        if (threads != null && !threads.getMeasurements().isEmpty()) {
            int threadCount = threads.getMeasurements().getFirst().getValue().intValue();
            System.out.println("Активные потоки: " + threadCount);
        }

        MetricsEndpoint.MetricDescriptor uptime = metricsEndpoint.metric("process.uptime", null);
        if (uptime != null && !uptime.getMeasurements().isEmpty()) {
            double uptimeSeconds = uptime.getMeasurements().getFirst().getValue();
            System.out.println("Время работы приложения: " + uptimeSeconds + " сек");
        }

        System.out.println("======================================");
    }
}