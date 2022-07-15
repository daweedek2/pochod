package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.domain.Stamp;
import cz.kostka.pochod.dto.StampDTO;
import cz.kostka.pochod.repository.StampRepository;
import cz.kostka.pochod.util.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dkostka on 7/11/2022.
 */
public class StampServiceTest {

    private StampRepository stampRepository;
    private PlayerService playerService;
    private StageService stageService;
    private GameInfoService gameInfoService;
    private StampService service;

    @BeforeEach
    void setup() {
        stampRepository = Mockito.mock(StampRepository.class);
        playerService = Mockito.mock(PlayerService.class);
        stageService = Mockito.mock(StageService.class);
        gameInfoService = Mockito.mock(GameInfoService.class);
        service = new StampService(stampRepository, playerService, stageService, gameInfoService);
    }

    @Test
    void testDeleteStampsOfPlayer() {
        final Stamp stamp = new Stamp(99L, null, null, null);
        when(stampRepository.findAllByPlayerOrderByTimestamp(any())).thenReturn(List.of(stamp));

        service.deleteStampsOfPlayer(new Player());

        verify(stampRepository).deleteAll(List.of(stamp));
    }

    @Test
    void testDeleteStampsOfPlayer_NoStamps() {
        when(stampRepository.findAllByPlayerOrderByTimestamp(any())).thenReturn(Collections.emptyList());

        service.deleteStampsOfPlayer(new Player());

        verify(stampRepository).deleteAll(Collections.emptyList());
    }

    @Test
    void testGetAllStampsByPlayer() {
        final Stamp stamp = new Stamp(99L, null, null, null);
        when(stampRepository.findAllByPlayerOrderByTimestamp(any())).thenReturn(List.of(stamp));

        final var result = service.getAllStampsByPlayerOrdered(new Player());

        assertThat(result).containsExactly(stamp);
    }

    @Test
    void testGetStampsByStageOrdered() {
        final Stage stage = new Stage();

        final var result = service.getStampsByStageOrdered(stage);

        verify(stampRepository).findAllByStageOrderByTimestamp(stage);
    }

    @Test
    void testGetStampsForPlayerAndStage() {
        final Stage stage = new Stage();
        final Player player = new Player();

        final var result = service.getStampsForPlayerAndStage(player, stage);

        verify(stampRepository).findAllByPlayerAndStage(player, stage);
    }

    @Test
    void testGetStampDTOForPlayerAndStage_NoStamp() {
        final Stage stage = new Stage();
        final Player player = new Player();
        when(stampRepository.findAllByPlayerAndStage(player, stage)).thenReturn(Collections.emptyList());

        final var stampDTO = service.getStampDTOForPlayerAndStage(player, stage);

        assertThat(stampDTO)
                .extracting(
                        StampDTO::playerId, StampDTO::stageId, StampDTO::taken, StampDTO::time
                )
                .containsExactly(
                        player.getId(), null, false, null
                );
    }

    @Test
    void testGetStampDTOForPlayerAndStage_Taken() {
        final Stage stage = new Stage();
        stage.setId(44L);
        final Player player = new Player();
        player.setId(66L);
        final LocalDateTime time = TimeUtils.getCurrentTime();
        final Stamp stamp = new Stamp(56L, time, stage, player);
        when(stampRepository.findAllByPlayerAndStage(player, stage)).thenReturn(List.of(stamp));

        final var stampDTO = service.getStampDTOForPlayerAndStage(player, stage);

        assertThat(stampDTO)
                .extracting(
                        StampDTO::playerId, StampDTO::stageId, StampDTO::taken, StampDTO::time
                )
                .containsExactly(
                        player.getId(), stage.getId(), true, time
                );
    }

    @Test
    void testGetCountOfSubmittedStamps_NullPlayer() {
        final int result = service.getCountOfStagesWithStamp(null);

        assertThat(result).isZero();
    }
}
