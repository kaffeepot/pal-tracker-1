package io.pivotal.pal.tracker;

import org.springframework.data.repository.CrudRepository;

public interface JpaTimeEntryRepository extends CrudRepository<TimeEntry, Long> {


}
