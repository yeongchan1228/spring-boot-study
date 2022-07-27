package study.application.project.controller.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BaseErrorController.class)
class BaseErrorControllerTest {

    private final MockMvc mvc;

    public BaseErrorControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @DisplayName("[VIEW][GET] 에러 페이지 요청 - 페이지 없음")
    public void testBaseErrorController() throws Exception {

        mvc.perform(get("/wrong"))
                .andExpect(status().isNotFound())
                .andDo(print());

    }
}