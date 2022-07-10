package cz.kostka.pochod.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
}
