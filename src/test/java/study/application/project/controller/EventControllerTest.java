package study.application.project.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired private MockMvc mvc;

    @Test
    @DisplayName("[VIEW][GET] 이벤트 리스트 페이지")
    public void givenNothing_whenRequestEventPage_thenEventListHTML() throws Exception {
        // given

        // when & then
        mvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("event/index"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("events"))
                .andDo(print());
    }

//    @Test
//    @DisplayName("[VIEW][GET] 이벤트 세부 정보 페이지")
//    public void givenNothing_whenRequestEventPage_thenEventHTML() throws Exception {
//        // given
//        Long eventId = 1L;
//
//        // when & then
//        mvc.perform(get("/events/" + eventId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("event/detail"))
//                .andExpect(model().hasNoErrors())
//                .andExpect(model().attributeExists("event"))
//                .andDo(print());
//    }

}