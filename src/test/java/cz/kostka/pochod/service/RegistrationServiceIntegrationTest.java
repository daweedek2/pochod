package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.RegistrationResponseDTO;
import cz.kostka.pochod.enums.RegistrationStatus;
import cz.kostka.pochod.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void register_Created() {
        final var result = registrationService.register(new RegistrationRequestDTO("johny", "map@pat.cz", "112", 9856));

        assertThat(result)
                .extracting(
                        RegistrationResponseDTO::getRegisteredPlayer,
                        RegistrationResponseDTO::getRegistrationStatus)
                .containsExactly(
                        playerRepository.findAll().get(0),
                        RegistrationStatus.CREATED);
    }

    @Test
    void register_AlreadyPresent() {
        createPlayer("johny", 9855);
        final var result = registrationService.register(new RegistrationRequestDTO("johny", "map@pat.cz", "112", 9856));

        assertThat(result)
                .extracting(
                        RegistrationResponseDTO::getRegisteredPlayer,
                        RegistrationResponseDTO::getRegistrationStatus)
                .containsExactly(
                        null,
                        RegistrationStatus.ALREADY_PRESENT);
    }

    @Test
    void register_Error() {
        final var result = registrationService.register(null);

        assertThat(result)
                .extracting(
                        RegistrationResponseDTO::getRegisteredPlayer,
                        RegistrationResponseDTO::getRegistrationStatus)
                .containsExactly(
                        null,
                        RegistrationStatus.ERROR);
    }
}