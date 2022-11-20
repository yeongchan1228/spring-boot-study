package springbootstudy.snsprojectweb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Post;
import springbootstudy.snsprojectweb.domain.post.repository.PostRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    MemberService memberService;

    @Test
    void 포스트_작성_성공() throws Exception {
        // given
        String title = "title";
        String content = "content";
        String username = "username";
        Member member = createMember(username);

        // when
        when(memberService.findByUsername(username)).thenReturn(member);
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        // then
        Assertions.assertDoesNotThrow(() -> postService.create(title, content, username));
    }

    @Test
    void 포스트_작성_실패_작성_요청_유저가_존재하지_않는_경우() throws Exception {
        // given
        String title = "title";
        String content = "content";
        String username = "username";
        Member member = createMember(username);

        // when
        when(memberService.findByUsername(username)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.create(title, content, username));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.NOT_FOUND);
    }

    @Test
    void 포스트_수정_성공() throws Exception {
        // given
        String title = "title";
        String content = "content";
        String username = "username";
        Member member = createMember(username);
        long postId = 1;

        // when
        when(postRepository.findByIdAndUsername(postId)).thenReturn(Optional.of(createPost(postId, member)));

        // then
        Assertions.assertDoesNotThrow(() -> postService.modify(title, content, username, postId));
    }

    @Test
    void 포스트_수정_포스트가_존재하지_않는_경우() throws Exception {
        // given
        String title = "title";
        String content = "content";
        String username = "username";
        long postId = 1;

        // when
        when(postRepository.findByIdAndUsername(postId)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, content, username, postId));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.NOT_FOUND);
    }

    @Test
    void 포스트_수정_작성자가_일치하지_않는_경우() throws Exception {
        // given
        String title = "title";
        String content = "content";
        String username = "username";
        Member member = createMember("wrongUsername");
        long postId = 1;

        // when
        when(postRepository.findByIdAndUsername(postId)).thenReturn(Optional.of(createPost(postId, member)));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, content, username, postId));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.INVALID_PERMISSION);
    }

    private Member createMember(String username) {
        return Member.of(username, null);
    }

    private Post createPost(long postId, Member member) {
        return Post.of(postId, null, null, member);
    }
}
