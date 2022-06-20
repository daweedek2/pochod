package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StampService;
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

    public PopController(
            final PlayerService playerService,
            final StampService stampService,
            final GameInfoService gameInfoService) {
        super(playerService, stampService, gameInfoService);
    }

    @GetMapping
    public String viewHome(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setStartGameToModel(model);
        return "pop/welcome";
    }

    @GetMapping("/progress")
    public String viewProgress(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        return "pop/progress";
    }

    @GetMapping("/partners")
    public String viewPartners(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setPartnersToModel(model);
        return "pop/partners";
    }
}
