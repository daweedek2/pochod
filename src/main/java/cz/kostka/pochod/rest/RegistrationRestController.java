package cz.kostka.pochod.rest;

import cz.kostka.pochod.api.RegistrationApi;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.RegistrationResponseDTO;
import cz.kostka.pochod.enums.RegistrationStatus;
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


    public RegistrationRestController(final RegistrationApi registrationApi) {
        this.registrationApi = registrationApi;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        System.out.println("I am here...");
        final RegistrationResponseDTO responseDTO = registrationApi.register(registrationRequestDTO);
        System.out.printf("I am there");
        return responseDTO.getRegistrationStatus() == RegistrationStatus.ERROR
                ? ResponseEntity.internalServerError().build()
                : ResponseEntity.ok(responseDTO);
    }
}
