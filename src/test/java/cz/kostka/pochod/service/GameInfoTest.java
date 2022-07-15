package cz.kostka.pochod.service;

import cz.kostka.pochod.repository.GameInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by dkostka on 7/15/2022.
 */
public class GameInfoTest {
    private GameInfoService service;
    private GameInfoRepository gameInfoRepository;
    private StageService stageService;

    @BeforeEach
    void setup() {
        gameInfoRepository = Mockito.mock(GameInfoRepository.class);
        stageService = Mockito.mock(StageService.class);
        service = new GameInfoService(gameInfoRepository, stageService);
    }

    @Test
    void testGetGameInfo_NotExist() {
        when(gameInfoRepository.findById(1L)).thenReturn(Optional.empty());

        final var result = service.get();

        assertThat(result).isEmpty();
    }
}
