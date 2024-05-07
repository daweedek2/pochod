package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.dto.GameInfoDTO;
import cz.kostka.pochod.repository.GameInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class GameInfoServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private GameInfoService gameInfoService;
    @Autowired
    private GameInfoRepository gameInfoRepository;

    @AfterEach
    void teardown() {
        gameInfoRepository.deleteAll();
    }

    @Test
    void get_NoGameInfo() {
        final var gameInfoOptional = gameInfoService.get();

        assertThat(gameInfoOptional).isEmpty();
    }

    @Test
    void get_GameInfoIsPresent() {
        gameInfoRepository.save(getDefaultGameInfo());

        final var gameInfoOptional = gameInfoService.get();

        assertThat(gameInfoOptional).isNotEmpty();
    }

    @Test
    void update_withoutAnyExistingGameInfo() {
        gameInfoService.update(
                new GameInfoDTO(
                        null,
                        "2023-06-11T08:37",
                        "2023-06-11T10:37",
                        "b",
                        "www.ef.at",
                        LocalDateTime.now().getYear()));

        assertThat(gameInfoRepository.findAll())
                .hasSize(1);
        assertThat(gameInfoRepository.findAll().get(0))
                .extracting(
                        GameInfo::getStartGame,
                        GameInfo::getEndGame,
                        GameInfo::getPartners,
                        GameInfo::getMapUrl)
                .containsExactly(
                        LocalDateTime.parse("2023-06-11T08:37"),
                        LocalDateTime.parse("2023-06-11T10:37"),
                       "b",
                       "www.ef.at"
                );
    }

    @Test
    void update_multipleUpdatesDoNotCreateNewGameInfo() {
        gameInfoRepository.save(getDefaultGameInfo());

        for (int i = 0; i < 10; i++) {
            gameInfoService.update(
                    new GameInfoDTO(
                            null,
                            "2023-06-11T08:37",
                            "2023-06-11T10:37",
                            "b",
                            "www.ef.at",
                            LocalDateTime.now().getYear()));
        }


        assertThat(gameInfoRepository.findAll())
                .hasSize(1);
        assertThat(gameInfoRepository.findAll().get(0))
                .extracting(
                        GameInfo::getStartGame,
                        GameInfo::getEndGame,
                        GameInfo::getPartners,
                        GameInfo::getMapUrl)
                .containsExactly(
                        LocalDateTime.parse("2023-06-11T08:37"),
                        LocalDateTime.parse("2023-06-11T10:37"),
                        "b",
                        "www.ef.at"
                );
    }

    private GameInfo getDefaultGameInfo() {
        return new GameInfo(
                null,
                LocalDateTime.parse("2023-06-10T08:37"),
                LocalDateTime.parse("2023-06-10T10:37"),
                "a",
                "www.se.se",
                LocalDateTime.now().getYear());
    }
}