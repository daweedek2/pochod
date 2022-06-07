package cz.kostka.pochod.controller.admin;

import cz.kostka.pochod.dto.StageCreationDTO;
import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.StageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/6/2022.
 */
@Controller
@RequestMapping(StagesController.ENDPOINT)
public class StagesController {
    protected static final String ENDPOINT = "/admin/stages";
    private final StageService stageService;

    public StagesController(final StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping
    public String getStagesList(@AuthenticationPrincipal final CustomUserDetails admin, final Model model) {
        model.addAttribute("stageCreationDTO", new StageCreationDTO(null, null, null, null, null));
        model.addAttribute("allStages", stageService.getAllStages());
        return "admin/stages";
    }

    @PostMapping("/create")
    public String createStage(final @ModelAttribute("stageCreationDTO") StageCreationDTO dto) {
        stageService.create(dto);
        return "redirect:" + StagesController.ENDPOINT;
    }

    @GetMapping("/{id}")
    public String getStageDetail(final @PathVariable Long id, final Model model) {
        model.addAttribute("stage", stageService.getStageById(id));
        return "/admin/stage-detail";
    }

    @GetMapping("delete/{id}")
    public String deleteStage(final @PathVariable Long id) {
        stageService.delete(id);
        return "redirect:" + StagesController.ENDPOINT;
    }
}
