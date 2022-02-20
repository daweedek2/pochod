package cz.kostka.pochod.service;

import cz.kostka.pochod.api.StageApi;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StageDetailDTO;
import cz.kostka.pochod.dto.StageRequestDTO;
import cz.kostka.pochod.repository.StageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class StageService implements StageApi {
    private final StageRepository stageRepository;

    public StageService(final StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Override
    public StageDetailDTO getStage(final StageRequestDTO stageRequestDTO) {
        return null;
    }

    public Optional<Stage> getStageByPin(final String pin) {
        return stageRepository.getStageByPin(pin);
    }
}
