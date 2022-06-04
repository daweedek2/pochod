package cz.kostka.pochod.dto;

/**
 * Created by dkostka on 2/6/2022.
 */
public record RegistrationRequestDTO(String nickName, String email, Integer pin) {

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPin() {
        return pin;
    }
}
