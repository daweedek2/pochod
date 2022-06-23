package cz.kostka.pochod.service;

import cz.kostka.pochod.api.RegistrationApi;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.User;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.RegistrationResponseDTO;
import cz.kostka.pochod.enums.RegistrationStatus;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class RegistrationService implements RegistrationApi {
    private final UserService userService;
    private final PlayerService playerService;

    public RegistrationService(final UserService userService, final PlayerService playerService) {
        this.userService = userService;
        this.playerService = playerService;
    }

    @Override
    public RegistrationResponseDTO register(final RegistrationRequestDTO registrationRequestDTO) {
        if (isPlayerAlreadyExisting(registrationRequestDTO)) {
            return new RegistrationResponseDTO(RegistrationStatus.ALREADY_PRESENT);
        }
        final User newUser = userService.createUser(registrationRequestDTO);
        final Player newPlayer = playerService.createPlayer(registrationRequestDTO, newUser);

        if (newUser == null) {
            return new RegistrationResponseDTO(RegistrationStatus.ERROR);
        }

        return new RegistrationResponseDTO(RegistrationStatus.CREATED, newPlayer);
    }

    private boolean isPlayerAlreadyExisting(final RegistrationRequestDTO registrationRequestDTO) {
        final var existingPlayerByName = playerService.getPlayerByNickname(registrationRequestDTO.nickName());

        return existingPlayerByName.isPresent();
    }
}
