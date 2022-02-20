package cz.kostka.pochod.dto;

/**
 * Created by dkostka on 2/6/2022.
 */
public record StampRequestDTO(String stagePin, String nickname) {

    public String getStagePin() {
        return stagePin;
    }

    public String getNickname() {
        return nickname;
    }
}
