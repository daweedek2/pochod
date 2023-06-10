package cz.kostka.pochod.controller;

import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StampDTO;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StampService;
import cz.kostka.pochod.util.TimeUtils;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by dkostka on 6/6/2022.
 */

public class PlayerController {
    private static final String GAME_STARTED_ATTR = "hasGameStarted";
    private static final String GAME_ENDED_ATTR = "hasGameEnded";
    private static final String GAME_STARTED_TIME_ATTR = "gameStartedTime";
    private static final String GAME_ENDED_TIME_ATTR = "gameEndedTime";
    private static final String IS_END_WARNING_ACTIVE_ATTR = "isEndWarningActive";
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
        model.addAttribute(STAMPS_ATTR, stampService.getCountOfStagesWithStamp(player));
    }

    public void setStartGameToModel(final Model model) {
        final GameInfo gameInfo = gameInfoService.get().orElse(new GameInfo());
        final LocalDateTime startGameTime = gameInfo.getStartGame();
        model.addAttribute(GAME_STARTED_ATTR, hasGameAlreadyEnded(startGameTime));
        model.addAttribute(GAME_STARTED_TIME_ATTR, startGameTime);
    }

    public void setEndGameToModel(final Model model) {
        final GameInfo gameInfo = gameInfoService.get().orElse(new GameInfo());
        final LocalDateTime endGameTime = gameInfo.getEndGame();
        model.addAttribute(GAME_ENDED_ATTR, hasGameAlreadyEnded(endGameTime));
        model.addAttribute(IS_END_WARNING_ACTIVE_ATTR, isEndWarningActive(endGameTime));
        model.addAttribute(GAME_ENDED_TIME_ATTR, endGameTime);
    }

    private static boolean hasGameAlreadyEnded(LocalDateTime endGameTime) {
        return endGameTime != null && TimeUtils.getCurrentTime().isAfter(endGameTime);
    }

    private static boolean isEndWarningActive(LocalDateTime endGameTime) {
        return endGameTime != null && TimeUtils.getCurrentTime().isAfter(endGameTime.minusHours(1));
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
