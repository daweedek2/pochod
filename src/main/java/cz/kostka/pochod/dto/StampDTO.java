package cz.kostka.pochod.dto;

import java.time.LocalDateTime;

/**
 * Created by dkostka on 6/13/2022.
 */
public record StampDTO(Long stageId, Long playerId, Boolean taken, LocalDateTime time) {
}
