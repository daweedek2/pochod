package cz.kostka.pochod.controller;

import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StageDetailDTO;
import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.GameInfoService;
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
            final StageService stageService,
            final GameInfoService gameInfoService) {
        super(playerService, stampService, gameInfoService);
        this.stageService = stageService;
    }

    @GetMapping
    public String getAllStages(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setEndGameToModel(model);
        model.addAttribute("allStages", stageService.getAllStages());
        model.addAttribute("stampsMap", getStampsMapForPlayer(user.getPlayer()));
        setMapUrlToModel(model);
        return "pop/list";
    }

    @GetMapping("/{id}")
    public String getStageDetail(
            @PathVariable final Long id,
            @AuthenticationPrincipal final CustomUserDetails user,
            final Model model) {
        setPlayerToModel(user.getPlayer().getId(), model);
        setEndGameToModel(model);
        final var stage = stageService.getStageById(id);
        setStageToModel(stage, model);
        model.addAttribute("stamp", getStampDTOForPlayerAndStage(user.getPlayer(), stage));
        return "pop/stage";
    }

    private void setStageToModel(final Stage stage, final Model model) {
        model.addAttribute("stage",
                new StageDetailDTO(
                        stage.getNumber(),
                        stage.getName(),
                        stage.getInfo().split(";"),
                        stage.getLocation(),
                        stage.getColor()));
    }
}
