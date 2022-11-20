package springbootstudy.snsprojectweb.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import springbootstudy.snsprojectweb.api.controller.request.PostRequest;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.common.exception.SnsApplicationException;
import springbootstudy.snsprojectweb.service.PostService;
import springbootstudy.snsprojectweb.service.dto.PostDto;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @Test
    @WithMockUser
    void 포스트_작성_정상_동작() throws Exception {
        // given
        PostRequest postRequest = PostRequest.of("title", "content");

        // when
        doNothing().when(postService).create(any(), any(), any());

        // then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest))
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithAnonymousUser
    void 포스트_작성_로그인_하지_않은_유저() throws Exception {
        // given
        PostRequest postRequest = PostRequest.of("title", "content");

        // when & then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest))
                ).andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void 포스트_수정_정상_동작() throws Exception {
        // given
        long postId = 1;
        PostRequest postRequest = PostRequest.of("title", "content");

        // when
        when(postService.modify(any(), any(), any(), eq(postId))).thenReturn(createPostDto(postId, postRequest.getTitle(), postRequest.getContent()));

        // then
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest))
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 포스트_수정시_로그인_하지_않은_유저() throws Exception {
        // given
        PostRequest postRequest = PostRequest.of("title", "content");

        // then
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest))
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트_수정_본인의_작성한_글이_아닐_경우() throws Exception {
        // given
        PostRequest postRequest = PostRequest.of("title", "content");

        // when
        doThrow(new SnsApplicationException(ResponseCode.INVALID_PERMISSION)).when(postService).modify(any(), any(), any(), eq(1l));

        // then
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest))
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트_수정_글이_없는_경우() throws Exception {
        // given
        PostRequest postRequest = PostRequest.of("title", "content");

        // when
        doThrow(new SnsApplicationException(ResponseCode.NOT_FOUND)).when(postService).modify(any(), any(), any(), eq(1l));

        // then
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest))
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    private PostDto createPostDto(long postId, String title, String content) {
        return PostDto.of(postId, title, content, LocalDateTime.now());
    }
}
