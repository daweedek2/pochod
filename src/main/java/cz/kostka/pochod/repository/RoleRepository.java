package cz.kostka.pochod.repository;

import cz.kostka.pochod.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dkostka on 6/3/2022.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);
}
