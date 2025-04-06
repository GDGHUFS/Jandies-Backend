package gdghufs.jandies.jwt;

import gdghufs.jandies.entity.User;
import gdghufs.jandies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.userRepository
                .findById(Long.parseLong(username))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid authentication!"));

        return new CustomUserDetails(user);
    }
}
