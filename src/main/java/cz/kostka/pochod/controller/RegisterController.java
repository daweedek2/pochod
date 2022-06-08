package cz.kostka.pochod.controller;

import cz.kostka.pochod.api.RegistrationApi;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final RegistrationApi registrationApi;
    private final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    public RegisterController(final RegistrationApi registrationApi) {
        this.registrationApi = registrationApi;
    }

    @GetMapping
    public String getRegisterView() {
        return "login/register";
    }

    @PostMapping
    public String createRegistration(@RequestBody RegistrationRequestDTO registrationRequestDTO, final Model model) {
        LOG.info("Request to register player with name: {}, email: {} and phone: {}.",
                registrationRequestDTO.nickName(),
                registrationRequestDTO.email(),
                registrationRequestDTO.phone());
        final var result = registrationApi.register(registrationRequestDTO);

        model.addAttribute("registrationStatus", result.getRegistrationStatus());
        return "login/login";
    }
}
