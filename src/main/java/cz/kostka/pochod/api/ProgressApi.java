package cz.kostka.pochod.api;

import cz.kostka.pochod.dto.FinalProgressRequestDTO;
import cz.kostka.pochod.dto.FinalProgressResponseDTO;
import cz.kostka.pochod.dto.ProgressRequestDTO;
import cz.kostka.pochod.dto.ProgressResponseDTO;

/**
 * Created by dkostka on 2/6/2022.
 */
public interface ProgressApi {
    ProgressResponseDTO getVisitorsProgress(ProgressRequestDTO progressRequestDTO);
    FinalProgressResponseDTO getFinalProgress(FinalProgressRequestDTO finalProgressRequestDTO);
}
