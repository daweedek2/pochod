package cz.kostka.pochod.dto;

/**
 * Created by dkostka on 2/6/2022.
 */
public record RegistrationRequestDTO(String nickName, String email, String phone, Integer pin, Integer age, String city) {
}
