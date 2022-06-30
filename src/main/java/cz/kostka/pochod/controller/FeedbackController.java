package cz.kostka.pochod.controller;

import cz.kostka.pochod.dto.FeedbackDTO;
import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.FeedbackService;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StampService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/4/2022.
 */
@Controller
@RequestMapping("/pop/feedback")
public class FeedbackController extends PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);
    private final FeedbackService feedbackService;

    public FeedbackController(
            final PlayerService playerService,
            final StampService stampService,
            final GameInfoService gameInfoService,
            final FeedbackService feedbackService) {
        super(playerService, stampService, gameInfoService);
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public String getFeedbackView(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        final Long playerId = user.getPlayer().getId();
        setPlayerToModel(playerId, model);
        model.addAttribute("feedbackDTO", new FeedbackDTO(null, playerId));
        model.addAttribute("playersFeedbacks", feedbackService.getAllFeedbacksByPlayer(playerId));
        return "pop/feedback";
    }

    @PostMapping
    public String submit(@AuthenticationPrincipal final CustomUserDetails user,
                         @ModelAttribute("feedbackDTO") final FeedbackDTO dto) {
        LOG.info("User with id '{}' created feedback '{}'.", dto.playerId(), dto.text());
        feedbackService.create(dto);
        return "redirect:/pop/feedback";
    }
}
