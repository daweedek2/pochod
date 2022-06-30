package cz.kostka.pochod.repository;

import cz.kostka.pochod.domain.Feedback;
import cz.kostka.pochod.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dkostka on 6/30/2022.
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> getAllByPlayer(Player player);
}
