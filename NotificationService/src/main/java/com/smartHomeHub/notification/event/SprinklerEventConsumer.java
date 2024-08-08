package com.smartHomeHub.notification.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SprinklerEventConsumer {

    @Bean
    public Consumer<SprinklerEvent> consumeSprinklerEvent() {
        return sprinklerEvent -> log.info(
                "Consumed {} event for the sprinklerId {}",
                sprinklerEvent.getAction(),
                sprinklerEvent.getSprinklerId());
    }
}
