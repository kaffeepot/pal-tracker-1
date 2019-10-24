package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(
            TimeEntryRepository timeEntryRepository,
            MeterRegistry meterRegistry
    ) {
        this.timeEntryRepository = timeEntryRepository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
        public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry newTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<>(newTimeEntry, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(name = "id") long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        ResponseEntity<TimeEntry> responseEntity;
        if (timeEntry != null) {
            actionCounter.increment();
            responseEntity = new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable(name="id")long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId, expected);
        ResponseEntity<TimeEntry> responseEntity;
        if (timeEntry != null) {
            actionCounter.increment();
            responseEntity = new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        List<TimeEntry> timeEntries = timeEntryRepository.list();
        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity<>(timeEntries, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(name="id")long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
