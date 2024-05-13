package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StageService;
import cz.kostka.pochod.service.StampService;
import cz.kostka.pochod.util.TimeUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/3/2022.
 */
@Controller
@RequestMapping("/pop")
public class PopController extends PlayerController {

    private final StageService stageService;

    public PopController(
            final PlayerService playerService,
            final StampService stampService,
            final GameInfoService gameInfoService,
            final StageService stageService) {
        super(playerService, stampService, gameInfoService);
        this.stageService = stageService;
    }

    @GetMapping
    public String viewHome(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setStartGameToModel(model);
        setEndGameToModel(model);
        model.addAttribute(AttributeConstants.ALL_STAGES_COUNT, stageService.getAllStagesCount(TimeUtils.getCurrentYear()));
        return "pop/welcome";
    }

    @GetMapping("/progress")
    public String viewProgress(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setEndGameToModel(model);
        model.addAttribute(AttributeConstants.ALL_STAGES_COUNT, stageService.getAllStagesCount(TimeUtils.getCurrentYear()));
        return "pop/progress";
    }

    @GetMapping("/progress2")
    public String viewProgressV2(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setEndGameToModel(model);
        setMapUrlToModel(model);
        model.addAttribute(AttributeConstants.ALL_STAGES, stageService.getAllStages(TimeUtils.getCurrentYear()));
        model.addAttribute(AttributeConstants.STAMPS_MAP, getStampsMapForPlayer(user.getPlayer()));
        model.addAttribute(AttributeConstants.ALL_STAGES_COUNT, stageService.getAllStagesCount(TimeUtils.getCurrentYear()));
        return "pop/listV2";
    }

    @GetMapping("/partners")
    public String viewPartners(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setEndGameToModel(model);
        setPartnersToModel(model);
        return "pop/partners";
    }

    @GetMapping("/map")
    public String getAllStages(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setEndGameToModel(model);
        setMapUrlToModel(model);
        return "pop/map";
    }
}
