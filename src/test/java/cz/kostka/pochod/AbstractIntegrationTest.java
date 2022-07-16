package cz.kostka.pochod;

import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.domain.Stamp;
import cz.kostka.pochod.dto.GameInfoDTO;
import cz.kostka.pochod.repository.GameInfoRepository;
import cz.kostka.pochod.repository.PlayerRepository;
import cz.kostka.pochod.repository.StageRepository;
import cz.kostka.pochod.repository.StampRepository;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by dkostka on 7/10/2022.
 */
@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/data.sql")
public class AbstractIntegrationTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private StageRepository stageRepository;
    @Autowired
    private StampRepository stampRepository;
    @Autowired
    protected GameInfoService gameInfoService;
    @Autowired
    protected GameInfoRepository gameInfoRepository;

    public Player createPlayer(final String nickName, final int pin) {
        return playerRepository.save(new Player(nickName, "dummy@email.com", "+420 800123456", pin, 30, "Polanka"));
    }

    public Stage createStage(final String name, final int number, final String pin) {
        return stageRepository.save(new Stage(name, number, new Point(12L, 21L), pin, "info"));
    }

    public void createSubmittedStamp(final Stage stage, final Player player) {
        stampRepository.save(new Stamp(TimeUtils.getCurrentTime(), stage, player));
    }

    public void setupGameEnded() {
        if (gameInfoService.get().isEmpty()) {
            gameInfoRepository.save(new GameInfo());
        }

        gameInfoService.update(
                new GameInfoDTO(
                        1L,
                        getTimeMinusHours(2L),
                        getTimeMinusHours(1L),
                    "",
                    ""));
    }

    public void setupGameStarted() {
        if (gameInfoService.get().isEmpty()) {
            gameInfoRepository.save(new GameInfo());
        }

        gameInfoService.update(
                new GameInfoDTO(
                        1L,
                        getTimeMinusHours(1L),
                        getTimePlusHours(1L),
                        "",
                        ""));
    }

    private static String getTimePlusHours(final long hours) {
        return getTimeString(hours, true);
    }

    private static String getTimeMinusHours(final long hours) {
        return getTimeString(hours, false);
    }

    private static LocalDateTime getTime(final long hours, final boolean inFuture) {
        return inFuture
                ? TimeUtils.getCurrentTime().plusHours(hours)
                : TimeUtils.getCurrentTime().minusHours(hours);
    }

    private static String getTimeString(final long hours, final boolean inFuture) {
        return getTime(hours, inFuture).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
