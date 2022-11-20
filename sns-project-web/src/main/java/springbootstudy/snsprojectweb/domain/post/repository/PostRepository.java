package springbootstudy.snsprojectweb.domain.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"member"})
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findByIdAndUsername(@Param("postId") long postId);
}
