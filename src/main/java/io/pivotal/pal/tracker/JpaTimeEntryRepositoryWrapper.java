package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaTimeEntryRepositoryWrapper implements TimeEntryRepository {

    private JpaTimeEntryRepository jpaTimeEntryRepository;

    public JpaTimeEntryRepositoryWrapper(JpaTimeEntryRepository jpaTimeEntryRepository) {
        this.jpaTimeEntryRepository = jpaTimeEntryRepository;
    }

    @Override
    public TimeEntry create(TimeEntry any) {
        return jpaTimeEntryRepository.save(any);
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        Optional<TimeEntry> timeEntry = jpaTimeEntryRepository.findById(timeEntryId);
        return timeEntry.orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> entries = new ArrayList<>();
        jpaTimeEntryRepository.findAll().forEach(timeEntry -> entries.add(timeEntry));
        return entries;
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry any) {
        any.setId(timeEntryId);
        return jpaTimeEntryRepository.save(any);
    }

    @Override
    public void delete(long timeEntryId) {
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(timeEntryId);
        jpaTimeEntryRepository.delete(timeEntry);
    }
}
