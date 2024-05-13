package cz.kostka.pochod.service;

import cz.kostka.pochod.api.StampApi;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.domain.Stamp;
import cz.kostka.pochod.dto.StampDTO;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.dto.StampResultDTO;
import cz.kostka.pochod.enums.StampSubmitStatus;
import cz.kostka.pochod.repository.StampRepository;
import cz.kostka.pochod.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class StampService implements StampApi {
    private static final Logger LOG = LoggerFactory.getLogger(StampService.class);
    private final StampRepository stampRepository;
    private final PlayerService playerService;
    private final StageService stageService;
    private final GameInfoService gameInfoService;

    public StampService(
            final StampRepository stampRepository,
            final PlayerService playerService,
            final StageService stageService,
            final GameInfoService gameInfoService) {
        this.stampRepository = stampRepository;
        this.playerService = playerService;
        this.stageService = stageService;
        this.gameInfoService = gameInfoService;
    }


    @Override
    public StampResultDTO submitStamp(final StampRequestDTO stampRequestDTO) {
        final Optional<Stage> optionalStage = stageService.getStageByPin(stampRequestDTO.pin());
        final Player player = playerService.getPlayerById(stampRequestDTO.playerId());

        if (optionalStage.isEmpty() || player == null) {
            return new StampResultDTO(StampSubmitStatus.REJECTED);
        }

        final Stage stage = optionalStage.get();

        if (stage.getYear() != TimeUtils.getCurrentTime().getYear() && gameInfoService.isGameActive()) {
            LOG.info("Player '{}' tries to submit stamp '{}' when the game is not active.", player.getNickname(), stage.getName());
            return new StampResultDTO(StampSubmitStatus.GAME_NOT_ACTIVE);
        }

        LOG.info("Player '{}' tries to submit stamp '{}'.", player.getNickname(), stage.getName());

        final List<Stamp> alreadySubmittedStamps = getStampsForPlayerAndStage(player, stage);

        if (!alreadySubmittedStamps.isEmpty()) {
            return new StampResultDTO(StampSubmitStatus.ALREADY_PRESENT);
        }

        stampRepository.save(new Stamp(TimeUtils.getCurrentTime(), stage, player));

        return new StampResultDTO(StampSubmitStatus.OK);
    }

    public boolean hasPlayerSubmittedAllStamps(final Player player, final int year) {
        final var playersStamps = this.getAllStampsByPlayerOrdered(player, year);
        final var allStages = stageService.getAllStages(year);
        return allStages.stream()
                .filter(stage ->
                        playersStamps.stream()
                                .anyMatch(stamp -> stamp.getStage() == stage))
                .toList()
                .size() == allStages.size();
    }

    public List<Stamp> getStampsForPlayerAndStage(final Player player, final Stage stage) {
        return stampRepository.findAllByPlayerAndStage(player, stage);
    }

    public StampDTO getStampDTOForPlayerAndStage(final Player player, final Stage stage) {
        final var stamps = getStampsForPlayerAndStage(player, stage);
        return createStampDTO(stamps.stream().findFirst(), player.getId());
    }

    public List<Stamp> getStampsByStageOrdered(final Stage stage) {
        return stampRepository.findAllByStageOrderByTimestamp(stage);
    }

    public List<Stamp> getAllStampsByPlayerOrdered(final Player player, final int year) {
        return stampRepository.findAllByPlayerOrderByTimestamp(player)
                .stream()
                .filter(stamp -> stamp.getStage().getYear() == year)
                .toList();
    }

    public List<Stamp> getAllStampsByPlayerOrderedAdmin(final Player player) {
        return stampRepository.findAllByPlayerOrderByTimestamp(player);
    }

    public int getCountOfStagesWithStamp(final Player player, final int year) {
        if (player == null) {
            return 0;
        }

        return stageService.getAllStages(year).stream()
                .filter(stage -> !getStampsForPlayerAndStage(player, stage).isEmpty())
                .toList()
                .size();
    }

    public Map<Integer, StampDTO> getStampsMapForUser(final Player player, final int year) {
        final List<Stage> allStages = stageService.getAllStages(year);
        final List<Stamp> allStampsOfPlayer = getAllStampsByPlayerOrdered(player, year);

        final Map<Integer, StampDTO> stampDTOMap = new HashMap<>(allStages.size());

        for (final Stage stage : allStages) {
            stampDTOMap.put(stage.getNumber(), createStampDTOFromStamps(allStampsOfPlayer, player.getId(), stage));
        }

        return stampDTOMap;
    }

    private static StampDTO createStampDTOFromStamps(
            final List<Stamp> allStampsOfPlayer,
            final Long playerId,
            final Stage stage) {

        final Optional<Stamp> optionalStamp = allStampsOfPlayer.stream()
                .filter(stamp -> stamp.getStage() == stage)
                .findFirst();

        return createStampDTO(optionalStamp, playerId);
    }

    private static StampDTO createStampDTO(final Optional<Stamp> optionalStamp, final Long playerId) {
        return optionalStamp
                .map(stamp -> StampDTO.taken(stamp, playerId))
                .orElseGet(() -> StampDTO.notTaken(playerId));
    }

    public void deleteStampsOfPlayer(final Player player) {
        if (player == null) {
            return;
        }
        stampRepository.deleteAll(getAllStampsByPlayerOrderedAdmin(player));
    }

    public void deleteStampsOfStage(final Long stageId) {
        stampRepository.deleteAll(getStampsByStageOrdered(stageService.getStageById(stageId)));
    }
}
