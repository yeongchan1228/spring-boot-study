package springbootstudy.snsprojectweb.common.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import springbootstudy.snsprojectweb.service.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ") && !request.getRequestURI().contains("auth")) {
            try {
                final String token = header.split(" ")[1].trim();

                if (jwtTokenProvider.isExpired(token)) {
                    log.error("Jwt is expired");
                    return;
                }

                setSecurityAuthentication(token);
            } catch (Exception e) {
                log.error("[Error] ", e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
