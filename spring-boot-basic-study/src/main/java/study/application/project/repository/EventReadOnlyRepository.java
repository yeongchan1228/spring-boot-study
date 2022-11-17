package study.application.project.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import study.application.project.domain.Event;

import java.util.List;

public interface EventReadOnlyRepository extends Repository<Event, Long> {
    List<Event> findAll();
    List<Event> findAll(Sort sort);
    List<Event> findAllById(Iterable<Long> ids);
}
