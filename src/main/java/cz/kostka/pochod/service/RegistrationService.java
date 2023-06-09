package cz.kostka.pochod.service;

import cz.kostka.pochod.api.RegistrationApi;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.User;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.RegistrationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class RegistrationService implements RegistrationApi {
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final UserService userService;
    private final PlayerService playerService;

    public RegistrationService(final UserService userService, final PlayerService playerService) {
        this.userService = userService;
        this.playerService = playerService;
    }

    @Override
    public RegistrationResponseDTO register(final RegistrationRequestDTO registrationRequestDTO) {
        if (registrationRequestDTO == null) {
            logger.error("Nothing to register.");
            return RegistrationResponseDTO.error();
        }

        if (isPlayerAlreadyExisting(registrationRequestDTO)) {
            logger.warn("User with name {} already exists.", registrationRequestDTO.nickName());
            return RegistrationResponseDTO.alreadyPresent();
        }

        return registerWithNormalizedUsername(registrationRequestDTO);
    }

    private RegistrationResponseDTO registerWithNormalizedUsername(final RegistrationRequestDTO registrationRequestDTO) {
        final User newUser = userService.createUser(registrationRequestDTO);
        final Player newPlayer = playerService.createPlayer(registrationRequestDTO, newUser);

        if (newUser == null) {
            return RegistrationResponseDTO.error();
        }

        logger.info("New player registered successfully: {}.", newPlayer);
        return RegistrationResponseDTO.success(newPlayer);
    }

    private boolean isPlayerAlreadyExisting(final RegistrationRequestDTO registrationRequestDTO) {
        final var existingPlayerByName = playerService.getPlayerByNickname(registrationRequestDTO.nickName());

        return existingPlayerByName.isPresent();
    }
}
