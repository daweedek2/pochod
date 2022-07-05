package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.domain.Stamp;
import cz.kostka.pochod.dto.PlayerAdminDTO;
import cz.kostka.pochod.dto.StageStatisticDTO;
import cz.kostka.pochod.mapper.PlayerMapper;
import cz.kostka.pochod.mapper.StageMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dkostka on 7/2/2022.
 */
@Service
public class StatisticsService {
    private final PlayerService playerService;
    private final StageService stageService;
    private final StampService stampService;

    public StatisticsService(final PlayerService playerService, final StageService stageService, final StampService stampService) {
        this.playerService = playerService;
        this.stageService = stageService;
        this.stampService = stampService;
    }

    public List<StageStatisticDTO> collect() {
        final List<Stage> allStages = stageService.getAllStages();

        return allStages.stream()
                .map(this::fetchStatisticsForStage)
                .collect(Collectors.toList());
    }

    private StageStatisticDTO fetchStatisticsForStage(final Stage stage) {
        final var stampsTaken = stampService.getStampsByStageOrdered(stage);

        if (stampsTaken.isEmpty()) {
            return new StageStatisticDTO(
                    StageMapper.INSTANCE.stageToDTO(stage),
                    stampsTaken.size(),
                    playerService.getAllPlayers().size(),
                    getPercentage(stampsTaken.size(), playerService.getAllPlayers().size()),
                    null,
                    null,
                    null,
                    null
            );
        }

        final Stamp firstTakenStamp = stampsTaken.get(0);
        final Stamp lastTakenStamp = stampsTaken.get(stampsTaken.size() - 1);

        return new StageStatisticDTO(
                StageMapper.INSTANCE.stageToDTO(stage),
                stampsTaken.size(),
                playerService.getAllPlayers().size(),
                getPercentage(stampsTaken.size(), playerService.getAllPlayers().size()),
                PlayerMapper.INSTANCE.playerToDTO(firstTakenStamp.getPlayer()),
                firstTakenStamp.getTimestamp().plusHours(2),
                PlayerMapper.INSTANCE.playerToDTO(lastTakenStamp.getPlayer()),
                lastTakenStamp.getTimestamp().plusHours(2));
    }

    private static Float getPercentage(final int success, final int total) {
        BigDecimal bd = new BigDecimal(Float.toString(success * 100.0f / total));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
