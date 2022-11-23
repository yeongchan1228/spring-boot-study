package springbootstudy.snsprojectweb.cache.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import springbootstudy.snsprojectweb.domain.member.entity.Member;
import springbootstudy.snsprojectweb.service.dto.MemberDto;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {

    private final static Duration MEMBER_CACHE_TTL = Duration.ofDays(1);
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public void setMemberDto(MemberDto memberDto) {
        String key = getKey(memberDto.getUsername());
        try {
            log.info("[Cache] Set {}, {}", key, objectMapper.writeValueAsString(memberDto));
        } catch (JsonProcessingException e) {
        }
        redisTemplate.opsForValue().set(key, memberDto, MEMBER_CACHE_TTL);
    }

    public Optional<Member> getMember(String username) {
        String key = getKey(username);
        Member findMember = null;
        try {
            findMember = objectMapper.convertValue(redisTemplate.opsForValue().get(key), Member.class);
            log.info("[Cache] Get {}, {}", key, objectMapper.writeValueAsString(findMember));
        } catch (JsonProcessingException e) {
        }
        return Optional.ofNullable(findMember);
    }

    private String getKey(String username) {
        return "Member:" + username;
    }
}
