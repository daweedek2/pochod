package cz.kostka.pochod.dto;

import java.time.LocalDateTime;

/**
 * Created by dkostka on 7/2/2022.
 */
public record StageStatisticDTO(
        StageAdminDTO stage,
        Integer stampsTakenNumber,
        Integer playersNumber,
        Float percentage,
        PlayerAdminDTO firstPlayer,
        LocalDateTime firstStampTimestamp,
        PlayerAdminDTO lastPlayer,
        LocalDateTime lastStampTimestamp) {
}
