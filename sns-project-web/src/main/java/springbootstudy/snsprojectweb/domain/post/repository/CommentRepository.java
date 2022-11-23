package springbootstudy.snsprojectweb.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springbootstudy.snsprojectweb.domain.post.entity.Comment;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"member", "post"})
    Page<Comment> findAllByPost(Post post, Pageable pageable);

    @Modifying
    @Query("delete from Comment c where c.post.id = :postId")
    void deleteAllByPostId(@Param("postId") long postId);
}
