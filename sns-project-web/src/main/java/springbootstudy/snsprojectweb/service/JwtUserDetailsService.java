package springbootstudy.snsprojectweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springbootstudy.snsprojectweb.cache.repository.MemberCacheRepository;
import springbootstudy.snsprojectweb.domain.member.entity.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final MemberService memberService;
    private final MemberCacheRepository memberCacheRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberCacheRepository.getMember(username).orElseGet(() -> memberService.findByUsername(username));
        return new User(findMember.getUsername(), findMember.getPassword(), List.of(new SimpleGrantedAuthority(findMember.getRole().toString())));
    }
}
