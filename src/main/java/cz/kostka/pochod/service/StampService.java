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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        final Stage stage = stageService.getStageById(stampRequestDTO.stageId());
        final Player player = playerService.getPlayerById(stampRequestDTO.playerId());
        if (stage == null || player == null) {
            return new StampResultDTO(StampSubmitStatus.REJECTED);
        }

        final Optional<Stamp> optionalStamp = getStampForPlayerAndStage(player, stage);

        if (optionalStamp.isPresent()) {
            return new StampResultDTO(StampSubmitStatus.ALREADY_PRESENT);
        }

        final LocalDateTime currentTime = LocalDateTime.now();
        final Stamp submittedStamp =  stampRepository.save(new Stamp(currentTime, stage, player));

        return new StampResultDTO(StampSubmitStatus.OK);
    }

    public Optional<Stamp> getStampForPlayerAndStage(final Player player, final Stage stage) {
        return stampRepository.getStampByPlayerAndStage(player, stage);
    }

    public StampDTO getStampDTOForPlayerAndStage(final Player player, final Stage stage) {
        return createStampDTO(getStampForPlayerAndStage(player, stage), player.getId());
    }

    public List<Stamp> getStampsByStage(final Stage stage) {
        return stampRepository.findAllByStage(stage);
    }

    public List<Stamp> getStampsByPlayer(final Player player) {
        return stampRepository.findAllByPlayer(player);
    }

    public Map<Integer, StampDTO> getStampsMapForUser(final Player player) {
        final List<Stamp> allUsersStamps = getStampsByPlayer(player);
        final Map<Integer, StampDTO> stampDTOHashMap = new HashMap<>(4);
        for (int i = 1; i <= 4; i++) {
            int finalI = i;
            final var optStamp = allUsersStamps.stream()
                    .filter(stamp -> stamp.getStage().getNumber() == finalI)
                    .findFirst();

            stampDTOHashMap.put(i, createStampDTO(optStamp, player.getId()));
        }

        return stampDTOHashMap;
    }

    private StampDTO createStampDTO(final Optional<Stamp> stamp, final Long playerId) {
        return stamp
                .map(value -> new StampDTO(value.getStage().getId(), playerId, true, stamp.get().getTimestamp()))
                .orElseGet(() -> new StampDTO(null, playerId, false, null));
    }
}
