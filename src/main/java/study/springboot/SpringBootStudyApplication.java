package study.springboot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import study.springboot.springcache.StudentService;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringBootStudyApplication {

    private final StudentService service;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStudyApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        service.printStudent("jack");
        service.printStudent("jack");
        service.printStudent("jack");
    }
}
