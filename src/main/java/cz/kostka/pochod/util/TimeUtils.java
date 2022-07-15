package cz.kostka.pochod.util;

import cz.kostka.pochod.domain.GameInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * Created by dkostka on 7/10/2022.
 */
public final class TimeUtils {

    private TimeUtils() {
        // empty ctor for final class
    }

    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.of("Europe/Vienna"));
    }

    public static boolean hasGameStarted(final Optional<GameInfo> gameInfoOptional) {
        if (gameInfoOptional.isEmpty()) {
            return false;
        }

        return getCurrentTime().isAfter(gameInfoOptional.get().getStartGame());
    }

    public static boolean hasGameEnded(final Optional<GameInfo> gameInfoOptional) {
        if (gameInfoOptional.isEmpty()) {
            return false;
        }

        return getCurrentTime().isAfter(gameInfoOptional.get().getEndGame());
    }
}
