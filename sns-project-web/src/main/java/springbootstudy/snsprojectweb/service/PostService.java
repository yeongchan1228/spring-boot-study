package springbootstudy.snsprojectweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Post;
import springbootstudy.snsprojectweb.domain.post.repository.PostRepository;
import springbootstudy.snsprojectweb.service.dto.PostDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberService memberService;
    private final PostRepository postRepository;

    @Transactional
    public void create(String title, String content, String username) {
        Member findMember = memberService.findByUsername(username);
        postRepository.save(Post.of(title, content, findMember));
    }

    @Transactional
    public PostDto modify(String title, String content, String username, Long postId) {
        Post findPost = findByIdWithMember(postId);
        if (!findPost.getMember().getUsername().equals(username)) {
            throw new SnsApplicationException(ResponseCode.INVALID_PERMISSION);
        }

        findPost.modifyPost(title, content);

        return PostDto.fromEntity(findPost);
    }

    @Transactional
    public void delete(String username, Long postId) {
        Post findPost = findByIdWithMember(postId);
        if (!findPost.getMember().getUsername().equals(username)) {
            throw new SnsApplicationException(ResponseCode.INVALID_PERMISSION);
        }

        postRepository.deleteByPostId(postId);
    }

    private Post findByIdWithMember(long postId) {
        return postRepository.findByIdWithMember(postId).orElseThrow(() -> new SnsApplicationException(
                ResponseCode.NOT_FOUND, String.format("%s not founded.", postId)
        ));
    }
}
