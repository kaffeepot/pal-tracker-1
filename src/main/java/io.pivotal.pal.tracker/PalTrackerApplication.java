package io.pivotal.pal.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PalTrackerApplication {
    TimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();

    @Bean
    public TimeEntryRepository getTimeEntryRepository() {
        return timeEntryRepository;
    }

    public static void main(String [] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }
}
