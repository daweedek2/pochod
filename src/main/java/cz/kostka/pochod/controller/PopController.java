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
import org.springframework.web.bind.annotation.PathVariable;
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
        setPlayerToModel(user.getPlayer().getId(), model, TimeUtils.getCurrentYear());
        setStartGameToModel(model);
        setEndGameToModel(model);
        model.addAttribute(AttributeConstants.ALL_STAGES_COUNT, stageService.getAllStagesCount(TimeUtils.getCurrentYear()));
        model.addAttribute(AttributeConstants.FACEBOOK_EVENT_URL, getGameInfo().getFacebookUrl());
        return "pop/welcome";
    }

    @GetMapping("/progress2/{year}")
    public String viewProgressV2(@AuthenticationPrincipal final CustomUserDetails user, @PathVariable final Integer year, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model, year);
        setEndGameToModel(model);
        setMapUrlToModel(model, year);
        setYearToModel(model, year);
        model.addAttribute(AttributeConstants.ALL_STAGES, stageService.getAllStages(year));
        model.addAttribute(AttributeConstants.STAMPS_MAP, getStampsMapForPlayer(user.getPlayer(), year));
        model.addAttribute(AttributeConstants.TOMBOLA_UNLOCKED, isTombolaUnlocked(user.getPlayer(), year));
        model.addAttribute(AttributeConstants.ALL_STAGES_COUNT, stageService.getAllStagesCount(year));
        return "pop/listV2";
    }

    @GetMapping("/partners")
    public String viewPartners(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model, TimeUtils.getCurrentYear());
        setEndGameToModel(model);
        setPartnersToModel(model);
        return "pop/partners";
    }

    @GetMapping("/map")
    public String getAllStages(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model, TimeUtils.getCurrentYear());
        setEndGameToModel(model);
        setMapUrlToModel(model, TimeUtils.getCurrentYear());
        return "pop/map";
    }
}
