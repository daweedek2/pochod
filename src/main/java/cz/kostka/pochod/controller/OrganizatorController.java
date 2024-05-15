package cz.kostka.pochod.controller;

import cz.kostka.pochod.dto.SubmitStampOrganizatorDTO;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StageService;
import cz.kostka.pochod.service.StampService;
import cz.kostka.pochod.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/organizator")
public class OrganizatorController {

    private final StampService stampService;
    private final PlayerService playerService;
    private final StageService stageService;

    public OrganizatorController(
            final StampService stampService,
            final PlayerService playerService,
            final StageService stageService) {
        this.stampService = stampService;
        this.playerService = playerService;
        this.stageService = stageService;
    }

    @GetMapping
    public String getOrganizatorView() {
        return "organizator/welcome";
    }

    @GetMapping("/submit")
    public String getSubmitView(final Model model) {
        addEmptySubmitDTOToModel(model);
        addPlayersAndStagesToModel(model);
        return "organizator/submit";
    }

    @PostMapping("/submit")
    public String postSubmitView(final @ModelAttribute("submitStampOrganizatorDTO") SubmitStampOrganizatorDTO dto,
                                 final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("stampResultDTO", stampService.submitStampAsOrganizator(dto));
        return "redirect:/organizator/submit";
    }

    private static void addEmptySubmitDTOToModel(final Model model) {
        model.addAttribute("submitStampOrganizatorDTO", new SubmitStampOrganizatorDTO(null, null));
    }

    private void addPlayersAndStagesToModel(final Model model) {
        model.addAttribute("players", playerService.getAllPlayers());
        model.addAttribute("stages", stageService.getAllStages(TimeUtils.getCurrentYear()));
    }
}
