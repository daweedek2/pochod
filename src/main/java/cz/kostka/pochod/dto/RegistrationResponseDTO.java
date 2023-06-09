package cz.kostka.pochod.dto;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.enums.RegistrationStatus;

/**
 * Created by dkostka on 2/6/2022.
 */
public class RegistrationResponseDTO {
    private RegistrationStatus registrationStatus;
    private Player registeredPlayer;

    public RegistrationResponseDTO(final RegistrationStatus status) {
        this.registrationStatus = status;
    }

    public RegistrationResponseDTO(final RegistrationStatus registrationStatus, final Player registeredPlayer) {
        this.registrationStatus = registrationStatus;
        this.registeredPlayer = registeredPlayer;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(final RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Player getRegisteredPlayer() {
        return registeredPlayer;
    }

    public void setRegisteredPlayer(final Player registeredPlayer) {
        this.registeredPlayer = registeredPlayer;
    }

    public static RegistrationResponseDTO error() {
        return new RegistrationResponseDTO(RegistrationStatus.ERROR);
    }

    public static RegistrationResponseDTO alreadyPresent() {
        return new RegistrationResponseDTO(RegistrationStatus.ALREADY_PRESENT);
    }

    public static RegistrationResponseDTO success(final Player player) {
        return new RegistrationResponseDTO(RegistrationStatus.CREATED, player);
    }
}
