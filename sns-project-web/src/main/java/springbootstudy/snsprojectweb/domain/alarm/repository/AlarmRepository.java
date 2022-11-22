package springbootstudy.snsprojectweb.domain.alarm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootstudy.snsprojectweb.domain.alarm.entity.Alarm;
import springbootstudy.snsprojectweb.domain.member.entity.Member;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @EntityGraph(attributePaths = {"fromMember", "toMember", "targetPost"})
    Page<Alarm> findAllByToMember(Member member, Pageable pageable);
}
