package cz.kostka.pochod.repository;

import cz.kostka.pochod.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by dkostka on 2/6/2022.
 */
@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    Optional<Stage> getStageByPinAndYear(String pin, int year);
    List<Stage> findAllByOrderByNumberDesc();
    List<Stage> findAllByYearOrderByNumber(int year);
}
