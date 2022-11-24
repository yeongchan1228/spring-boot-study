package springbootstudy.snsprojectweb.cache.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.service.MemberService;
import springbootstudy.snsprojectweb.service.dto.MemberDto;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {

    private final static Duration MEMBER_CACHE_TTL = Duration.ofDays(1);
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberService memberService;

    public void setMemberDto(MemberDto memberDto) {
        String key = getKey(memberDto.getUsername());
        try {
            log.info("[Cache] Set {}, {}", key, objectMapper.writeValueAsString(memberDto));
        } catch (JsonProcessingException e) {
        }
        redisTemplate.opsForValue().set(key, memberDto, MEMBER_CACHE_TTL);
    }

    public Member getMember(String username) {
        String key = getKey(username);
        Member findMember = null;
        try {
            findMember = objectMapper.convertValue(redisTemplate.opsForValue().get(key), Member.class);
            log.info("[Cache] Get {}, {}", key, objectMapper.writeValueAsString(findMember));
        } catch (JsonProcessingException e) {
        }

        // DB 조회
        if (findMember == null) {
            findMember = memberService.findByUsername(username);
        }

        return findMember;
    }

    private String getKey(String username) {
        return "Member:" + username;
    }
}
