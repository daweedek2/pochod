package cz.kostka.pochod.dto;

/**
 * Created by dkostka on 6/14/2022.
 */
public record PlayerAdminDTO(Long id, String nickname, String phoneNumber, String email, Integer pin, Integer age, String city) {
}
