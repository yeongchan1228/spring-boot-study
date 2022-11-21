package springbootstudy.snsprojectweb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.domain.post.entity.Comment;
import springbootstudy.snsprojectweb.domain.post.entity.Like;
import springbootstudy.snsprojectweb.domain.post.entity.Post;
import springbootstudy.snsprojectweb.domain.post.repository.CommentRepository;
import springbootstudy.snsprojectweb.domain.post.repository.LikeRepository;
import springbootstudy.snsprojectweb.domain.post.repository.PostRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    MemberService memberService;

    @Mock
    LikeRepository likeRepository;

    @Mock
    CommentRepository commentRepository;

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
        when(postRepository.findByIdWithMember(postId)).thenReturn(Optional.of(createPost(postId, member)));

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
        when(postRepository.findByIdWithMember(postId)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

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
        when(postRepository.findByIdWithMember(postId)).thenReturn(Optional.of(createPost(postId, member)));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.modify(title, content, username, postId));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.INVALID_PERMISSION);
    }

    @Test
    void 포스트_삭제_성공() throws Exception {
        // given
        long postId = 1;
        String username = "username";
        Member member = createMember(username);

        // when
        when(postRepository.findByIdWithMember(postId)).thenReturn(Optional.of(createPost(postId, member)));
        doNothing().when(postRepository).deleteByPostId(postId);

        // then
        Assertions.assertDoesNotThrow(() -> postService.delete(username, postId));
    }

    @Test
    void 포스트_삭제_실패_등록된_글이_없을_경우() throws Exception {
        // given
        long postId = 1;
        String username = "username";

        // when
        when(postRepository.findByIdWithMember(postId)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete(username, postId));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.NOT_FOUND);
    }

    @Test
    void 포스트_삭제_실패_작성자와_다를_경우() throws Exception {
        // given
        long postId = 1;
        String username = "username";
        String wrongUsername = "wrongUsername";
        Member member = createMember(username);

        // when
        when(postRepository.findByIdWithMember(postId)).thenReturn(Optional.of(createPost(postId, member)));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.delete(wrongUsername, postId));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.INVALID_PERMISSION);
    }

    @Test
    void 포스트_목록_조회_성공() throws Exception {
        // given
        Pageable pageable = mock(Pageable.class);

        // when
        when(postRepository.findAll(pageable)).thenReturn(Page.empty());

        // then
        Assertions.assertDoesNotThrow(() -> postService.list(pageable));
    }

    @Test
    void 포스트_내_목록_조회_성공() throws Exception {
        // given
        Member member = mock(Member.class);
        Pageable pageable = mock(Pageable.class);

        // when
        when(memberService.findByUsername(any())).thenReturn(member);
        when(postRepository.findAllByMember(member, pageable)).thenReturn(Page.empty());

        // then
        Assertions.assertDoesNotThrow(() -> postService.myList("", pageable));
    }

    @Test
    void 좋아요_기능_성공() throws Exception {
        // given
        long postId = 1;
        Post post = createPost(postId);
        String username = "username";
        Member member = createMember(username);

        // when
        when(postRepository.findByIdWithMember(postId)).thenReturn(Optional.of(post));
        when(memberService.findByUsername(username)).thenReturn(member);
        when(likeRepository.findByMemberAndPost(member, post)).thenReturn(Optional.empty());
        when(likeRepository.save(any())).thenReturn(mock(Like.class));

        // then
        Assertions.assertDoesNotThrow(() -> postService.like(postId, username));
    }

    @Test
    void 좋아요_기능_실패_게시글이_존재하지_않는_경우() throws Exception {
        // given
        long postId = 1;
        String username = "username";

        // when
        when(postRepository.findByIdWithMember(anyLong())).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.like(postId, username));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.NOT_FOUND);
    }

    @Test
    void 좋아요_기능_실패_이미_좋아요를_누른_경우() throws Exception {
        // given
        long postId = 1;
        Post post = createPost(postId);
        String username = "username";
        Member member = createMember(username);

        // when
        when(postRepository.findByIdWithMember(postId)).thenReturn(Optional.of(post));
        when(memberService.findByUsername(username)).thenReturn(member);
        when(likeRepository.findByMemberAndPost(member, post)).thenThrow(new SnsApplicationException(ResponseCode.ALREADY_LIKED));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.like(postId, username));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.ALREADY_LIKED);
    }

    @Test
    void 좋아요_개수_조회_성공() throws Exception {
        // given
        long postId = 1;
        Post post = createPost(postId);

        // when
        when(postRepository.findByIdWithMember(postId)).thenReturn(Optional.of(post));
        when(likeRepository.getTotalCountByPostId(postId)).thenReturn(anyInt());

        // then
        Assertions.assertDoesNotThrow(() -> postService.likeCount(postId));
    }

    @Test
    void 좋아요_개수_조회_실패_게시글이_존재하지_않는_경우() throws Exception {
        // given
        long postId = 1;

        // when
        when(postRepository.findByIdWithMember(postId)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.likeCount(postId));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.NOT_FOUND);
    }

    @Test
    @WithMockUser
    void 댓글_작성_성공() throws Exception {
        // given
        long postId = 1;
        String content = "content";
        Post post = createPost(postId);

        // when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.save(any())).thenReturn(mock(Comment.class));

        // then
        Assertions.assertDoesNotThrow(() -> postService.comment(postId, content));
    }

    @Test
    @WithMockUser
    void 댓글_작성_실패_게시글이_존재하지_않는_경우() throws Exception {
        // given
        long postId = 1;
        String content = "content";

        // when
        when(postRepository.findById(postId)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.comment(postId, content));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.NOT_FOUND);
    }

    @Test
    @WithMockUser
    void 댓글_페이징_조회_성공() throws Exception {
        // given
        long postId = 1;
        String content = "content";
        Pageable pageable = mock(Pageable.class);
        Post post = createPost(postId);

        // when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findAllByPost(post, pageable)).thenReturn(Page.empty());

        // then
        Assertions.assertDoesNotThrow(() -> postService.getComments(postId, pageable));
    }

    @Test
    @WithMockUser
    void 댓글_페이징_조회_실패_게시글이_존재하지_않는_경우() throws Exception {
        // given
        long postId = 1;
        Pageable pageable = mock(Pageable.class);

        // when
        when(postRepository.findById(postId)).thenThrow(new SnsApplicationException(ResponseCode.NOT_FOUND));

        // then
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.getComments(postId, pageable));
        Assertions.assertEquals(e.getResponseCode(), ResponseCode.NOT_FOUND);
    }


    private Member createMember(String username) {
        return Member.of(username, null);
    }

    private Post createPost(long postId, Member member) {
        return Post.of(postId, null, null, member);
    }

    private Post createPost(long postId) {
        return Post.of(postId, null, null, null);
    }
}
