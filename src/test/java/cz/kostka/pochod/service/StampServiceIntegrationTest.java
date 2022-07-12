package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StampDTO;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.dto.StampResultDTO;
import cz.kostka.pochod.enums.StampSubmitStatus;
import cz.kostka.pochod.util.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dkostka on 7/10/2022.
 */
public class StampServiceIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private StampService stampService;

    @Test
    void testSubmitStamp_Ok() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        final var result = stampService.submitStamp(new StampRequestDTO(player.getId(), stage.getPin()));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.OK);
    }

    @Test
    void testSubmitStamp_AlreadyExist() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        stampService.submitStamp(new StampRequestDTO(player.getId(), stage.getPin()));

        final var result = stampService.submitStamp(new StampRequestDTO(player.getId(), stage.getPin()));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.ALREADY_PRESENT);
    }

    @Test
    void testSubmitStamp_Rejected_WrongPin() {
        final Player player = createPlayer("pl", 1122);

        final var result = stampService.submitStamp(new StampRequestDTO(player.getId(), "wrong pin"));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.REJECTED);
    }

    @Test
    void testSubmitStamp_Rejected_PlayerNotExists() {
        final Stage stage = createStage("first", 1, "1234");

        final var result = stampService.submitStamp(new StampRequestDTO(11L, stage.getPin()));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.REJECTED);
    }

    @Test
    void testSubmitStamp_Rejected_WrongPinAndPlayerNotExists() {
        final var result = stampService.submitStamp(new StampRequestDTO(11L, "wrong_pin"));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.REJECTED);
    }

    @Test
    void testSubmitStamp_Rejected_GameHasEnded() {
        setupGameEnded();
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        final StampResultDTO stampResultDTO = stampService.submitStamp(new StampRequestDTO(player.getId(), stage.getPin()));

        final var stamps = stampService.getAllStampsByPlayerOrdered(player);

        assertThat(stampResultDTO.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.REJECTED);
    }

    @Test
    void testSubmitStamp_TimestampIsSet() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        stampService.submitStamp(new StampRequestDTO(player.getId(), stage.getPin()));

        final var stamps = stampService.getAllStampsByPlayerOrdered(player);

        assertThat(stamps).hasSize(1);

        final var timestamp = stamps.get(0).getTimestamp();
        assertThat(timestamp).isNotNull();
        assertThat(timestamp.minusHours(1)).isBefore(TimeUtils.getCurrentTime());
        assertThat(timestamp.plusHours(1)).isAfter(TimeUtils.getCurrentTime());
    }

    @Test
    void testPlayerSubmittedZeroStamps() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");

        final boolean result = stampService.hasPlayerSubmittedAllStamps(player, List.of(stage));

        assertThat(result).isFalse();
    }

    @Test
    void testPlayerSubmittedAllStamps_OneIsMissing() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        final Stage stage2 = createStage("second", 2, "1234");
        createSubmittedStamp(stage, player);

        final boolean result = stampService.hasPlayerSubmittedAllStamps(player, List.of(stage, stage2));

        assertThat(result).isFalse();
    }

    @Test
    void testPlayerSubmittedAllStamps_Ok() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        createSubmittedStamp(stage, player);

        final boolean result = stampService.hasPlayerSubmittedAllStamps(player, List.of(stage));

        assertThat(result).isTrue();
    }

    @Test
    void testPlayerSubmittedAllStamps_Ok_TwoOfTwoStages() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        final Stage stage2 = createStage("second", 2, "1234");
        createSubmittedStamp(stage, player);
        createSubmittedStamp(stage2, player);

        final boolean result = stampService.hasPlayerSubmittedAllStamps(player, List.of(stage, stage2));

        assertThat(result).isTrue();
    }

    @Test
    void testPlayerSubmittedAllStamps_MultipleTimes() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        createSubmittedStamp(stage, player);
        createSubmittedStamp(stage, player);

        final boolean result = stampService.hasPlayerSubmittedAllStamps(player, List.of(stage));

        assertThat(result).isTrue();
    }

    @Test
    void testGetStampsMapForUser_NoStage() {
        final Player player = createPlayer("pl", 1122);

        final var result = stampService.getStampsMapForUser(player);

        assertThat(result).isEmpty();
    }

    @Test
    void testGetStampsMapForUser() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        final Stage stage2 = createStage("second", 2, "1234");
        final Stage stage3 = createStage("third", 3, "1234");
        createSubmittedStamp(stage, player);
        createSubmittedStamp(stage2, player);

        final var result = stampService.getStampsMapForUser(player);

        assertThat(result).hasSize(3);
        assertThat(result.get(1))
                .extracting(StampDTO::stageId, StampDTO::playerId, StampDTO::taken)
                .containsExactly(stage.getId(), player.getId(), true);
        assertThat(result.get(2))
                .extracting(StampDTO::stageId, StampDTO::playerId, StampDTO::taken)
                .containsExactly(stage2.getId(), player.getId(), true);
        assertThat(result.get(3))
                .extracting(StampDTO::stageId, StampDTO::playerId, StampDTO::taken)
                .containsExactly(null, player.getId(), false);
    }

    @Test
    void testGetCountOfStagesWithStamp() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");
        final Stage stage2 = createStage("second", 2, "1234");
        final Stage stage3 = createStage("third", 3, "1234");
        createSubmittedStamp(stage, player);
        createSubmittedStamp(stage2, player);
        createSubmittedStamp(stage2, player);

        final var result = stampService.getCountOfStagesWithStamp(player);

        assertThat(result).isEqualTo(2);
    }
}
