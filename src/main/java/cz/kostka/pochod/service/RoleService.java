package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.Role;
import cz.kostka.pochod.repository.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 6/3/2022.
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getUserRole() {
        return roleRepository.getRoleByName("USER");
    }
}
