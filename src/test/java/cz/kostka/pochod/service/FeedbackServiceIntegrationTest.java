package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.domain.Feedback;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.dto.FeedbackDTO;
import cz.kostka.pochod.repository.FeedbackRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class FeedbackServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackRepository feedbackRepository;

    private Player player;

    @BeforeEach
    void setup() {
        player = createPlayer("pepa", 1233);
    }

    @Test
    void create() {
        final var feedbackDTO = new FeedbackDTO("very good", player.getId());
        feedbackService.create(feedbackDTO);

        assertThat(feedbackRepository.findAll())
                .extracting(
                        Feedback::getPlayer,
                        Feedback::getText)
                .containsExactly(Tuple.tuple(
                        player,
                        "very good"));
    }

    @Test
    void getAllFeedbacksByPlayer() {
        final var player2 = createPlayer("max", 99887);
        final var feedbackPlayer1DTO = new FeedbackDTO("very good", player.getId());
        final var feedbackPlayer2DTO = new FeedbackDTO("very bad", player2.getId());
        final var feedbackPlayer1 = feedbackService.create(feedbackPlayer1DTO);
        feedbackService.create(feedbackPlayer2DTO);

        assertThat(feedbackService.getAllFeedbacksByPlayer(player.getId()))
                .containsExactly(feedbackPlayer1);
    }

    @Test
    void getAllFeedbacks() {
        final var player2 = createPlayer("max", 99887);
        final var feedbackPlayer1DTO = new FeedbackDTO("very good", player.getId());
        final var feedbackPlayer2DTO = new FeedbackDTO("very bad", player2.getId());
        final var feedbackPlayer1 = feedbackService.create(feedbackPlayer1DTO);
        final var feedbackPlayer2 = feedbackService.create(feedbackPlayer2DTO);

        assertThat(feedbackService.getAllFeedbacks())
                .containsExactlyInAnyOrder(feedbackPlayer1, feedbackPlayer2);
    }
}