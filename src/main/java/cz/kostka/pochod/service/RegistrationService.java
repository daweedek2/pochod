package cz.kostka.pochod.service;

import cz.kostka.pochod.api.RegistrationApi;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.RegistrationResponseDTO;
import cz.kostka.pochod.enums.RegistrationStatus;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class RegistrationService implements RegistrationApi {
    private PlayerService playerService;

    @Override
    public RegistrationResponseDTO register(final RegistrationRequestDTO registrationRequestDTO) {
        final Player registeredPlayer = playerService
                .createPlayer(registrationRequestDTO.getNickname(), registrationRequestDTO.getPhoneNumber());

        if (registeredPlayer == null) {
            return new RegistrationResponseDTO(RegistrationStatus.ERROR);
        }

        return new RegistrationResponseDTO(RegistrationStatus.CREATED, registeredPlayer);
    }
}
