package cz.kostka.pochod.service;

import cz.kostka.pochod.api.StageApi;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StageAdminDTO;
import cz.kostka.pochod.dto.StageCreationDTO;
import cz.kostka.pochod.dto.StageDetailDTO;
import cz.kostka.pochod.dto.StageRequestDTO;
import cz.kostka.pochod.repository.StageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Stage> getAllStages() {
        return stageRepository.findAllByOrderByNumber();
    }

    public int getAllStagesCount() {
        return getAllStages().size();
    }

    public Stage create(final StageCreationDTO dto) {
        return stageRepository.save(
                new Stage(dto.name(), dto.number(), dto.location(), dto.pin(), dto.info(), dto.color(), dto.year()));
    }

    public Stage getStageById(final Long id) {
        return stageRepository.getById(id);
    }
    public Optional<Stage> getStageByPin(final String pin) {
        return stageRepository.getStageByPin(pin);
    }

    public void delete(final Long id) {
        stageRepository.deleteById(id);
    }

    public Stage update(final StageAdminDTO dto) {
        return stageRepository.save(
                new Stage(
                        dto.id(), dto.name(), dto.number(),
                        dto.location(), dto.pin(), dto.info(),
                        dto.color(), dto.year(), dto.distanceInMeters()));
    }

    public List<Stage> getAllStagesByYear(final int year) {
        return stageRepository.findAllByYearOrderByNumber(year);
    }
}
