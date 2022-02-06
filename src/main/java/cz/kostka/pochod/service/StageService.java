package cz.kostka.pochod.service;

import cz.kostka.pochod.api.StageApi;
import cz.kostka.pochod.dto.StageDetailDTO;
import cz.kostka.pochod.dto.StageRequestDTO;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.dto.StampResultDTO;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class StageService implements StageApi {
    @Override
    public StageDetailDTO getStage(final StageRequestDTO stageRequestDTO) {
        return null;
    }

    @Override
    public StampResultDTO submitStamp(final StampRequestDTO stampRequestDTO) {
        return null;
    }
}
