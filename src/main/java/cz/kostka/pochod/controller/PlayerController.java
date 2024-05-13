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

    public void setPlayerToModel(final Long playerId, final Model model, final int year) {
        final Player player = playerService.getPlayerById(playerId);
        model.addAttribute(PLAYER_ATTR, player);
        model.addAttribute(STAMPS_ATTR, stampService.getCountOfStagesWithStamp(player, year));
    }

    public void setStartGameToModel(final Model model) {
        model.addAttribute(GAME_STARTED_ATTR, gameInfoService.isGameActive());
        model.addAttribute(GAME_STARTED_TIME_ATTR, gameInfoService.get().orElse(new GameInfo()).getStartGame());
    }

    public void setEndGameToModel(final Model model) {
        model.addAttribute(GAME_ENDED_ATTR, !gameInfoService.isGameActive());
        model.addAttribute(IS_END_WARNING_ACTIVE_ATTR, gameInfoService.isEndWarningActive());
        model.addAttribute(GAME_ENDED_TIME_ATTR, gameInfoService.get().orElse(new GameInfo()).getEndGame());
    }

    public void setPartnersToModel(final Model model) {
        final GameInfo gameInfo = gameInfoService.get().orElse(new GameInfo());
        model.addAttribute(PARTNERS_ATTR, gameInfo.getPartners().split(";"));
    }

    public void setMapUrlToModel(final Model model, final Integer year) {
        final GameInfo gameInfo = gameInfoService.get().orElse(new GameInfo());
        if (year == TimeUtils.getCurrentYear()) {
            model.addAttribute(MAP_URL_ATTR, gameInfo.getMapUrl());
        }
    }

    public Map<Integer, StampDTO> getStampsMapForPlayer(final Player player, final int year) {
        return stampService.getStampsMapForUser(player, year);
    }

    public StampDTO getStampDTOForPlayerAndStage(final Player player, final Stage stage) {
        return stampService.getStampDTOForPlayerAndStage(player, stage);
    }


    public void setYearToModel(final Model model, final Integer year) {
        model.addAttribute(AttributeConstants.YEAR, year);
    }
}
