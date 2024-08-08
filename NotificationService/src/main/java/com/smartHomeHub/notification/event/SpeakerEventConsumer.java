package com.smartHomeHub.notification.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SpeakerEventConsumer {

    @Bean
    public Consumer<SpeakerEvent> consumeSpeakerEvent() {
        return speakerEvent -> log.info(
                "Consumed {} event for the speakerId {}",
                speakerEvent.getAction(),
                speakerEvent.getSpeakerId());
    }
}
