package cz.kostka.pochod.repository;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.domain.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by dkostka on 2/6/2022.
 */
@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {
    Optional<Stamp> getStampByPlayerAndStage(Player player, Stage stage);
    List<Stamp> findAllByStageOrderByTimestamp(Stage stage);
    List<Stamp> findAllByPlayer(Player player);
}
