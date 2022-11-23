package springbootstudy.snsprojectweb.domain.alarm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @EntityGraph(attributePaths = {"fromMember", "targetPost"})
    @Query("select a from Alarm a where a.toMember.username = :username")
    Page<Alarm> findAllByUsername(@Param("username") String username, Pageable pageable);
}
