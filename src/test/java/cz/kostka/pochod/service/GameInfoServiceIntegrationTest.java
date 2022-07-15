package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.dto.GameInfoDTO;
import cz.kostka.pochod.util.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dkostka on 7/12/2022.
 */
public class GameInfoServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private GameInfoService service;

    @Test
    void testGetAllStagesCount_ZeroStages() {
        assertThat(service.getAllStagesCount()).isZero();
    }

    @Test
    void testCreateGameInfo() {
        final var startGame = TimeUtils.getCurrentTime();
        final var endGame = startGame.plusHours(6);
        final GameInfoDTO dto = new GameInfoDTO(
                1L,
                startGame.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                endGame.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "joska;pepin",
                "www.mapy.cz");

        service.update(dto);

        assertThat(service.get()).isPresent();
        assertThat(service.get().get()).extracting(
                GameInfo::getId,
                GameInfo::getStartGame,
                GameInfo::getEndGame,
                GameInfo::getPartners,
                GameInfo::getMapUrl
        ).containsExactly(
                dto.id(),
                LocalDateTime.parse(dto.startGame()),
                LocalDateTime.parse(dto.endGame()),
                dto.partners(),
                dto.mapUrl()
        );
    }
}
