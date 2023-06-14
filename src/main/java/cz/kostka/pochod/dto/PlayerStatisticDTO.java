package cz.kostka.pochod.dto;

import java.time.LocalDateTime;

/**
 * Created by dkostka on 7/2/2022.
 */
public record PlayerStatisticDTO(
        PlayerAdminDTO playerAdminDTO,
        Integer stampsTakenNumber,
        LocalDateTime firstStampTakenTimestamp,
        LocalDateTime lastStampTakenTimestamp) {
}
