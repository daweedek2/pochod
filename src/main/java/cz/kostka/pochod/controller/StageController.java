package cz.kostka.pochod.controller;

import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StageDetailDTO;
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

    @GetMapping("/{id}")
    public String getStageDetail(
            @PathVariable final Long id,
            @AuthenticationPrincipal final CustomUserDetails user,
            final Model model) {
        setEndGameToModel(model);
        setYearToModel(model, TimeUtils.getCurrentYear());
        final var stage = stageService.getStageById(id);
        setPlayerToModel(user.getPlayer().getId(), model, stage.getYear());
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
                        stage.getColor(),
                        stage.getDistance(),
                        stage.getYear()));
    }
}
