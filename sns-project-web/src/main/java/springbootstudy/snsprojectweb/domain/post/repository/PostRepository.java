package springbootstudy.snsprojectweb.domain.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"member"})
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findByIdWithMember(@Param("postId") long postId);

    @Modifying
    @Query("delete from Post p where p.id = :postId")
    void deleteByPostId(@Param("postId") long postId);
}
