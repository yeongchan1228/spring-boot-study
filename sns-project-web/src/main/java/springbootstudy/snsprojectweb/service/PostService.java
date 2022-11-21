package springbootstudy.snsprojectweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Like;
import springbootstudy.snsprojectweb.domain.post.entity.Post;
import springbootstudy.snsprojectweb.domain.post.repository.LikeRepository;
import springbootstudy.snsprojectweb.domain.post.repository.PostRepository;
import springbootstudy.snsprojectweb.service.dto.PostDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberService memberService;

    private final LikeRepository likeRepository;
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

    public Page<PostDto> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::fromEntity);
    }

    public Page<PostDto> myList(String username, Pageable pageable) {
        Member findMember = memberService.findByUsername(username);

        return postRepository.findAllByMember(findMember, pageable).map(PostDto::fromEntity);
    }

    @Transactional
    public void like(long postId, String username) {
        Post post = findByIdWithMember(postId);
        Member findMember = memberService.findByUsername(username);

        validAlreadyLike(postId, username, post, findMember);

        likeRepository.save(Like.of(post, findMember));
    }

    public int likeCount(Long postId) {
        Post post = findById(postId);
        return likeRepository.getTotalCountByPostId(post.getId());
    }

    private void validAlreadyLike(long postId, String username, Post post, Member findMember) {
        likeRepository.findByMemberAndPost(findMember, post).ifPresent(it -> {
            throw new SnsApplicationException(ResponseCode.ALREADY_LIKED, String.format("%s already like post %d", username, postId));
        });
    }

    private Post findByIdWithMember(long postId) {
        return postRepository.findByIdWithMember(postId).orElseThrow(() -> new SnsApplicationException(
                ResponseCode.NOT_FOUND, String.format("%s not founded.", postId)
        ));
    }

    private Post findById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new SnsApplicationException(
                ResponseCode.NOT_FOUND, String.format("%s not founded.", postId)
        ));
    }
}
