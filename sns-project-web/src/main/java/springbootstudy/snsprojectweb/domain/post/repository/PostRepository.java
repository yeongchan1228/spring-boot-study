package springbootstudy.snsprojectweb.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootstudy.snsprojectweb.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
