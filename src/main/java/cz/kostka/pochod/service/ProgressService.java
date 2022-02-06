package cz.kostka.pochod.service;

import cz.kostka.pochod.api.ProgressApi;
import cz.kostka.pochod.dto.FinalProgressRequestDTO;
import cz.kostka.pochod.dto.FinalProgressResponseDTO;
import cz.kostka.pochod.dto.ProgressRequestDTO;
import cz.kostka.pochod.dto.ProgressResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class ProgressService implements ProgressApi {
    @Override
    public ProgressResponseDTO getVisitorsProgress(final ProgressRequestDTO progressRequestDTO) {
        return null;
    }

    @Override
    public FinalProgressResponseDTO getFinalProgress(final FinalProgressRequestDTO finalProgressRequestDTO) {
        return null;
    }
}
