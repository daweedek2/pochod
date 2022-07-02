package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.Feedback;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.dto.FeedbackDTO;
import cz.kostka.pochod.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dkostka on 6/30/2022.
 */
@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final PlayerService playerService;

    public FeedbackService(final FeedbackRepository feedbackRepository, final PlayerService playerService) {
        this.feedbackRepository = feedbackRepository;
        this.playerService = playerService;
    }

    public Feedback create(final FeedbackDTO feedbackDTO) {
        return feedbackRepository
                .save(new Feedback(
                        feedbackDTO.text(),
                        getPlayer(feedbackDTO.playerId())));
    }

    public List<Feedback> getAllFeedbacksByPlayer(final Long playerId) {
        return feedbackRepository.getAllByPlayer(getPlayer(playerId));
    }

    private Player getPlayer(final Long playerId) {
        return playerService.getPlayerById(playerId);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}
