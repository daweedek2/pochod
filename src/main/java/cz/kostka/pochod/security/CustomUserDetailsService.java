package cz.kostka.pochod.security;

import cz.kostka.pochod.domain.User;
import cz.kostka.pochod.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 5/29/2022.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Uživatel neexistuje."));

        return new CustomUserDetails(user);
    }
}
