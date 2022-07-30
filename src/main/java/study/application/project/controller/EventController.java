package study.application.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import study.application.project.controller.dto.EventResponse;
import study.application.project.domain.constant.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    @GetMapping
    public String events(Model model) {
        List<EventResponse> data = List.of(
                EventResponse.of(
                        1L,
                        "오후 운동",
                        EventStatus.OPENED,
                        LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                        0,
                        24,
                        "마스크를 꼭 착용하세요"
                ),
                EventResponse.of(
                        1L,
                        "오후 운동",
                        EventStatus.OPENED,
                        LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                        0,
                        24,
                        "마스크를 꼭 착용하세요"
                )
        );
        model.addAttribute("events", data);
        return  "event/index";
    }

    @GetMapping("/{eventId}")
    public String eventDetail(@PathVariable Integer eventId,
                              Model model) {
        EventResponse data = EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크를 꼭 착용하세요"
        );
        model.addAttribute("event", data);
        return "event/detail";
    }

}
