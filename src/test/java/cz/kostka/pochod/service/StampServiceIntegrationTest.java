package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.enums.StampSubmitStatus;
import cz.kostka.pochod.util.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

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
        final Stage stage = createStage("first", 1, "1234");

        final var result = stampService.submitStamp(new StampRequestDTO(player.getId(), "wrong pin"));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.REJECTED);
    }

    @Test
    void testSubmitStamp_Rejected_PlayerNotExists() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");

        final var result = stampService.submitStamp(new StampRequestDTO(11L, stage.getPin()));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.REJECTED);
    }

    @Test
    void testSubmitStamp_Rejected_WrongPinAndPlayerNotExists() {
        final Player player = createPlayer("pl", 1122);
        final Stage stage = createStage("first", 1, "1234");

        final var result = stampService.submitStamp(new StampRequestDTO(11L, "wrong_pin"));

        assertThat(result.getStampSubmitStatus()).isEqualTo(StampSubmitStatus.REJECTED);
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
}
