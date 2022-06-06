package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.PlayerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/4/2022.
 */
@Controller
@RequestMapping("/pop/stages")
public class StageController extends PlayerController {

    public StageController(final PlayerService playerService) {
        super(playerService);
    }

    @GetMapping
    public String getAllStages(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        return "pop/list";
    }

    @GetMapping("/1")
    public String getStageDetail(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        return "pop/stage";
    }

    @GetMapping("/1/stamp")
    public String getStamp(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        return "pop/stamp";
    }
}
