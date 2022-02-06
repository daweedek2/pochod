package cz.kostka.pochod.api;

import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.RegistrationResponseDTO;

/**
 * Created by dkostka on 2/6/2022.
 */
public interface RegistrationApi {
    RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO);
}
