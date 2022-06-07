package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.User;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by dkostka on 6/3/2022.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder,
            final RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User createUser(final RegistrationRequestDTO dto) {
        if (dto == null || isPresentUserByUsernameAndPin(dto.nickname(), dto.pin())) {
            return null;
        }

        final User user = getUserForPlayer(dto.nickname(), dto.pin());
        return userRepository.save(user);
    }

    public boolean isPresentUserByUsernameAndPin(final String username, final int pin) {
        final String encodedPin = passwordEncoder.encode(String.valueOf(pin));
        return userRepository.findByUsernameAndPassword(username, encodedPin).isPresent();
    }

    private User getUserForPlayer(final String userName, final int pin) {
        final User user = new User();
        user.setUsername(userName);
        user.setPassword(passwordEncoder.encode(String.valueOf(pin)));
        user.setRoles(Set.of(roleService.getUserRole()));

        return user;
    }
}
