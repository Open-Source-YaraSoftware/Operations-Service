package com.workshopngine.platform.serviceoperations.operations.infrastructure.brokers.kafka;

import com.workshopngine.platform.serviceoperations.shared.domain.model.events.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class AppointmentEventSource {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Bean
    public Consumer<AppointmentCreatedEvent> appointmentCreatedEvent() {
        return applicationEventPublisher::publishEvent;
    }
}
