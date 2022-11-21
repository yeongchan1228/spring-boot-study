package springbootstudy.snsprojectweb.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootstudy.snsprojectweb.domain.post.entity.Comment;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"member", "post"})
    Page<Comment> findAllByPost(Post post, Pageable pageable);
}
