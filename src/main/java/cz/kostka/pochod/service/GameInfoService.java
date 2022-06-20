package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.repository.GameInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by dkostka on 6/20/2022.
 */
@Service
public class GameInfoService {
    private final GameInfoRepository gameInfoRepository;


    public GameInfoService(final GameInfoRepository gameInfoRepository) {
        this.gameInfoRepository = gameInfoRepository;
    }

    public Optional<GameInfo> get() {
        return gameInfoRepository.findById(1L);
    }

    public GameInfo update(final String startGame, final String partners) {
        final GameInfo gameInfo = get().orElse(new GameInfo());
        gameInfo.setStartGame(toTimestamp(startGame));
        gameInfo.setPartners(partners);
        return gameInfoRepository.save(gameInfo);
    }

    private LocalDateTime toTimestamp(final String startGame) {
        return LocalDateTime.parse(startGame, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
