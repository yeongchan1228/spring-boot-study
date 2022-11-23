package springbootstudy.snsprojectweb.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Like;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPost(Member member, Post post);

    @Query("select count(l.id) from Like l where l.post.id = :postId")
    int getTotalCountByPostId(@Param("postId") long postId);

    @Modifying
    @Query("delete from Like l where l.post.id = :postId")
    void deleteAllByPostId(@Param("postId") long postId);
}
