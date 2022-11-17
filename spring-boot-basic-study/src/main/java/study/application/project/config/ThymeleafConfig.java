package study.application.project.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(SpringResourceTemplateResolver defaultTemplateResolver,
                                                                    Thymeleaf3Properties thymeleaf3Properties) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        return defaultTemplateResolver;
    }

    @Getter
    @ConstructorBinding
    @RequiredArgsConstructor
    @ConfigurationProperties(prefix = "spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Thymeleaf Decoupled Logic 기능 활성화
         */
        private final boolean decoupledLogic;
    }
}
