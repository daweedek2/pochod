package cz.kostka.pochod.api;

import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.dto.StampResultDTO;

/**
 * Created by dkostka on 2/6/2022.
 */
public interface StampApi {
    StampResultDTO submitStamp(StampRequestDTO stampRequestDTO);
}
