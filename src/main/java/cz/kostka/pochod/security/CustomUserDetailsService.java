package cz.kostka.pochod.security;

import cz.kostka.pochod.domain.User;
import cz.kostka.pochod.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dkostka on 5/29/2022.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        LOG.info("User with name '{}' tries to login.", username);

        final Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            LOG.info("User with name '{}' does not exist", username);
            throw new UsernameNotFoundException("Uživatel neexistuje.");
        }

        LOG.info("User with name '{}' is found: {}", username, user.get());
        return new CustomUserDetails(user.get());
    }
}
