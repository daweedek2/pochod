package cz.kostka.pochod.controller;

import cz.kostka.pochod.service.PlayerService;
import org.springframework.ui.Model;

/**
 * Created by dkostka on 6/6/2022.
 */

public class PlayerController {
    private final PlayerService playerService;

    private static final String PLAYER_ATTR = "player";

    public PlayerController(final PlayerService playerService) {
        this.playerService = playerService;
    }

    public void setPlayerToModel(final Long playerId, final Model model) {
        model.addAttribute(PLAYER_ATTR, playerService.getPlayerById(playerId));
    }
}
