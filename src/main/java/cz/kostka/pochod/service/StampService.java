package cz.kostka.pochod.service;

import cz.kostka.pochod.api.StampApi;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.domain.Stamp;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.dto.StampResultDTO;
import cz.kostka.pochod.enums.StampSubmitStatus;
import cz.kostka.pochod.repository.StampRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class StampService implements StampApi {
    private final StampRepository stampRepository;
    private final PlayerService playerService;
    private final StageService stageService;

    public StampService(
            final StampRepository stampRepository,
            final PlayerService playerService,
            final StageService stageService) {
        this.stampRepository = stampRepository;
        this.playerService = playerService;
        this.stageService = stageService;
    }


    @Override
    public StampResultDTO submitStamp(final StampRequestDTO stampRequestDTO) {
        final Optional<Stage> optionalStage = stageService.getStageByPin(stampRequestDTO.getStagePin());
        final Optional<Player> optionalPlayer = playerService.getPlayerByNickname(stampRequestDTO.getNickname());
        if (optionalPlayer.isEmpty() || optionalStage.isEmpty()) {
            return new StampResultDTO(StampSubmitStatus.REJECTED);
        }

        final Optional<Stamp> optionalStamp = getStampForPlayerAndStage(optionalPlayer.get(), optionalStage.get());

        if (optionalStamp.isPresent()) {
            return new StampResultDTO(StampSubmitStatus.ALREADY_PRESENT, optionalStamp.get());
        }

        final LocalDateTime currentTime = LocalDateTime.now();
        final Stamp submittedStamp =  stampRepository.save(new Stamp(currentTime, optionalStage.get(), optionalPlayer.get()));

        return new StampResultDTO(StampSubmitStatus.OK, submittedStamp);
    }

    public Optional<Stamp> getStampForPlayerAndStage(final Player player, final Stage stage) {
        return stampRepository.getStampByPlayerAndStage(player, stage);
    }
}
