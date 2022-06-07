package cz.kostka.pochod.dto;

/**
 * Created by dkostka on 2/6/2022.
 */
public record RegistrationRequestDTO(String nickname, String email, Integer phone, Integer pin) {
}
