package cz.kostka.pochod.controller;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StampService;
import org.springframework.ui.Model;

/**
 * Created by dkostka on 6/6/2022.
 */

public class PlayerController {
    private final PlayerService playerService;
    private final StampService stampService;

    private static final String PLAYER_ATTR = "player";
    private static final String STAMPS_ATTR = "stamps";

    public PlayerController(final PlayerService playerService, final StampService stampService) {
        this.playerService = playerService;
        this.stampService = stampService;
    }

    public void setPlayerToModel(final Long playerId, final Model model) {
        final Player player = playerService.getPlayerById(playerId);
        model.addAttribute(PLAYER_ATTR, player);
        model.addAttribute(STAMPS_ATTR, stampService.getStampsByPlayer(player).size());
    }
}
