package cz.kostka.pochod.dto;

import cz.kostka.pochod.domain.Stamp;

import java.time.LocalDateTime;

/**
 * Created by dkostka on 6/13/2022.
 */
public record StampDTO(Long stageId, Long playerId, Boolean taken, LocalDateTime time) {
    public static StampDTO notTaken(final Long playerId) {
        return new StampDTO(null, playerId, false, null);
    }

    public static StampDTO taken(final Stamp stamp, final Long playerId) {
        return new StampDTO(stamp.getStage().getId(), playerId, true, stamp.getTimestamp());
    }
}
