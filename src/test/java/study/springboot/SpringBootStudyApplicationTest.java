package study.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;

//@Testcontainers
@SpringBootTest
class SpringBootStudyApplicationTest {

//    private static Logger logger = LoggerFactory.getLogger(SpringBootStudyApplicationTest.class);
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Container
//    private static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
//            .withExposedPorts(6379);
//
//    @BeforeAll
//    static void setup() {
//        redisContainer.followOutput(new Slf4jLogConsumer(logger));
//    }
//
//    @DynamicPropertySource // 테스트를 실행할 때 동적으로 Properties 값을 변경한다.
//    static void properties(DynamicPropertyRegistry registry) {
////        registry.add("spring.cache.type", () -> "redis");
//        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
//    }
//
//    @Test
//    void main() throws IOException, InterruptedException {
//        // Redis 실행 시 기본 포트 6379로 실행되는데 테스트 컨테이너에서 실행되는 이미지는 랜덤포트로 진행하기 때문에
//        // Redis 기본 포트를 찾아갈 수 없다.
//        // withExposedPorts(6379) 포트 지정
//        // registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379)); 랜덤 포트 지정
//        Assertions.assertThat(redisContainer.isRunning()).isTrue();
//    }
}