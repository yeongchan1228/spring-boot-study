package springbootstudy.snsprojectweb.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import springbootstudy.snsprojectweb.api.controller.response.APIResponse;
import springbootstudy.snsprojectweb.api.controller.response.AlarmResponse;
import springbootstudy.snsprojectweb.common.ResponseCode;
import springbootstudy.snsprojectweb.service.AlarmService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public APIResponse alarmList(Pageable pageable, Authentication authentication) {
        return APIResponse.success(
                ResponseCode.OK,
                alarmService.alarmList(authentication.getName(), pageable).map(AlarmResponse::fromDto)
        );
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribe(Authentication authentication) {
        return alarmService.connectAlarm(authentication.getName());
    }
}
