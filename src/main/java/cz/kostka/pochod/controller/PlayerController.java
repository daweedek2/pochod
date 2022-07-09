package cz.kostka.pochod.controller;

import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StampDTO;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StampService;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Created by dkostka on 6/6/2022.
 */

public class PlayerController {
    private static final String GAME_STARTED_ATTR = "gameStarted";
    private static final String GAME_STARTED_TIME_ATTR = "gameStartedTime";
    private static final String PLAYER_ATTR = "player";
    private static final String STAMPS_ATTR = "stamps";
    private static final String PARTNERS_ATTR = "partners";
    private static final String MAP_URL_ATTR = "mapUrl";

    private final PlayerService playerService;
    private final StampService stampService;
    private final GameInfoService gameInfoService;


    public PlayerController(
            final PlayerService playerService,
            final StampService stampService,
            final GameInfoService gameInfoService) {
        this.playerService = playerService;
        this.stampService = stampService;
        this.gameInfoService = gameInfoService;
    }

    public void setPlayerToModel(final Long playerId, final Model model) {
        final Player player = playerService.getPlayerById(playerId);
        model.addAttribute(PLAYER_ATTR, player);
        model.addAttribute(STAMPS_ATTR, stampService.getStampsByPlayer(player).size());
    }

    public void setStartGameToModel(final Model model) {
        final GameInfo gameInfo = gameInfoService.get().orElse(new GameInfo());
        final var startGame = gameInfo.getStartGame();
        model.addAttribute(
                GAME_STARTED_ATTR,
                startGame != null
                        && LocalDateTime.now(ZoneId.of("Europe/Vienna")).isAfter(startGame));
//        model.addAttribute(GAME_STARTED_ATTR, startGame != null && LocalDateTime.now().plusHours(2L).isAfter(startGame));
        model.addAttribute(GAME_STARTED_TIME_ATTR, startGame);
    }

    public void setPartnersToModel(final Model model) {
        final GameInfo gameInfo = gameInfoService.get().orElse(new GameInfo());
        model.addAttribute(PARTNERS_ATTR, gameInfo.getPartners().split(";"));
    }

    public void setMapUrlToModel(final Model model) {
        final GameInfo gameInfo = gameInfoService.get().orElse(new GameInfo());
        model.addAttribute(MAP_URL_ATTR, gameInfo.getMapUrl());
    }

    public Map<Integer, StampDTO> getStampsMapForPlayer(final Player player) {
        return stampService.getStampsMapForUser(player);
    }

    public StampDTO getStampDTOForPlayerAndStage(final Player player, final Stage stage) {
        return stampService.getStampDTOForPlayerAndStage(player, stage);
    }
}
