package cz.kostka.pochod.repository;

import cz.kostka.pochod.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by dkostka on 5/29/2022.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
