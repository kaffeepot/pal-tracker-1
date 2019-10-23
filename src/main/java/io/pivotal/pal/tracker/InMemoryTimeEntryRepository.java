package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    Map<Long, TimeEntry> timeEntries = new HashMap<>();
    private long id = 1L;


    @Override
    public TimeEntry create(TimeEntry any) {
        any.setId(id);
        timeEntries.put(any.getId(), any);
        id++;
        return any;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return timeEntries.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList(timeEntries.values());
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry any) {
        if (timeEntries.containsKey(timeEntryId)) {
            any.setId(timeEntryId);
            timeEntries.put(timeEntryId, any);
            return any;
        }
        return null;
    }

    @Override
    public void delete(long timeEntryId) {
        timeEntries.remove(timeEntryId);
    }
}
