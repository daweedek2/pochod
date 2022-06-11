package cz.kostka.pochod.controller.rest;

import cz.kostka.pochod.api.RegistrationApi;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.RegistrationResponseDTO;
import cz.kostka.pochod.enums.RegistrationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dkostka on 2/6/2022.
 */
@RestController
@RequestMapping(RegistrationRestController.ENDPOINT)
public class RegistrationRestController {

    public static final String ENDPOINT = "api/register";

    private final RegistrationApi registrationApi;
    private final Logger LOG = LoggerFactory.getLogger(RegistrationRestController.class);


    public RegistrationRestController(final RegistrationApi registrationApi) {
        this.registrationApi = registrationApi;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        LOG.info("Request to register player with name: {}, email: {}, phone: {} with pin: {}.",
                registrationRequestDTO.nickName(),
                registrationRequestDTO.email(),
                registrationRequestDTO.phone(),
                registrationRequestDTO.pin());
        final RegistrationResponseDTO responseDTO = registrationApi.register(registrationRequestDTO);
        LOG.info("Registration of player with name: {} finished with the status {}.",
                registrationRequestDTO.nickName(),
                responseDTO.getRegistrationStatus());
        return responseDTO.getRegistrationStatus() == RegistrationStatus.ERROR
                ? ResponseEntity.internalServerError().build()
                : ResponseEntity.ok(responseDTO);
    }
}
