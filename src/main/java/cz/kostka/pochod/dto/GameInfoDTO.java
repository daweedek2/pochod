package cz.kostka.pochod.dto;

/**
 * Created by dkostka on 6/14/2022.
 */
public record GameInfoDTO(Long id, String startGame, String endGame, String partners, String mapUrl, Integer year) {
}
