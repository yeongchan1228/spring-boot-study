package study.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.service.SortService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final SortService sortService;

    @GetMapping
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/sort")
    public List<String > sort(@RequestParam List<String> input) {
        return sortService.doSort(input);
    }
}
