package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
        public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry newTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<>(newTimeEntry, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(name = "id") long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        ResponseEntity<TimeEntry> responseEntity;
        if (timeEntry != null) {
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
            responseEntity = new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntries = timeEntryRepository.list();
        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity<>(timeEntries, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(name="id")long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
