package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StageService;
import cz.kostka.pochod.service.StampService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/4/2022.
 */
@Controller
@RequestMapping("/pop/stages")
public class StageController extends PlayerController {
    private final StageService stageService;

    public StageController(
            final PlayerService playerService,
            final StampService stampService,
            final StageService stageService) {
        super(playerService, stampService);
        this.stageService = stageService;
    }

    @GetMapping
    public String getAllStages(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        model.addAttribute("allStages", stageService.getAllStages());
        model.addAttribute("stampsMap", getStampsMapForPlayer(user.getPlayer()));
        return "pop/list";
    }

    @GetMapping("/{id}")
    public String getStageDetail(
            @PathVariable final Long id,
            @AuthenticationPrincipal final CustomUserDetails user,
            final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        final var stage = stageService.getStageById(id);
        setStageToModel(id, model);
        model.addAttribute("stamp", getStampDTOForPlayerAndStage(user.getPlayer(), stage));
        return "pop/stage";
    }

    @GetMapping("/{id}/stamp")
    public String viewStampForStage(
            @PathVariable final Long id,
            @AuthenticationPrincipal final CustomUserDetails user,
            final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setStageToModel(id, model);
        model.addAttribute("stamp", getStampDTOForPlayerAndStage(user.getPlayer(), stageService.getStageById(id)));
        return "pop/stamp";
    }

    private void setStageToModel(final Long stageId, final Model model) {
        model.addAttribute("stage", stageService.getStageById(stageId));
    }
}
