package cz.kostka.pochod.api;

import cz.kostka.pochod.dto.StageDetailDTO;
import cz.kostka.pochod.dto.StageRequestDTO;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.dto.StampResultDTO;

/**
 * Created by dkostka on 2/6/2022.
 */
public interface StageApi {
    StageDetailDTO getStage(StageRequestDTO stageRequestDTO);
}
