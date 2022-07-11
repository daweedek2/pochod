package cz.kostka.pochod.controller.admin;

import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StageAdminDTO;
import cz.kostka.pochod.dto.StageCreationDTO;
import cz.kostka.pochod.mapper.StageMapper;
import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.QrCodeGeneratorService;
import cz.kostka.pochod.service.StageService;
import cz.kostka.pochod.service.StampService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by dkostka on 6/6/2022.
 */
@Controller
@RequestMapping(StagesController.ENDPOINT)
public class StagesController {
    protected static final String ENDPOINT = "/admin/stages";
    private final StageService stageService;
    private final StampService stampService;
    private final QrCodeGeneratorService qrCodeGeneratorService;

    public StagesController(
            final StageService stageService,
            final StampService stampService,
            final QrCodeGeneratorService qrCodeGeneratorService) {
        this.stageService = stageService;
        this.stampService = stampService;
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }

    @GetMapping
    public String getStagesList(@AuthenticationPrincipal final CustomUserDetails admin, final Model model) {
        model.addAttribute("allStages", stageService.getAllStages());
        model.addAttribute("newStage", false);
        return "admin/stages";
    }

    @GetMapping("/new")
    public String createStage(@AuthenticationPrincipal final CustomUserDetails admin, final Model model) {
        model.addAttribute("stageCreationDTO", StageCreationDTO.empty());
        model.addAttribute("newStage", true);
        return "admin/stages";
    }

    @PostMapping("/create")
    public String createStage(final @ModelAttribute("stageCreationDTO") StageCreationDTO dto) {
        stageService.create(dto);
        return "redirect:" + StagesController.ENDPOINT;
    }

    @PostMapping("/update")
    public String createStage(final @ModelAttribute("stageAdminDTO") StageAdminDTO dto) {
        stageService.update(dto);
        return "redirect:" + StagesController.ENDPOINT;
    }

    @GetMapping("/{id}")
    public String getStageDetail(final @PathVariable Long id, final Model model) {
        final Stage stage = stageService.getStageById(id);
        model.addAttribute("stage", stage);
        model.addAttribute("stamps", stampService.getStampsByStageOrdered(stage).size());
        model.addAttribute("qrCode", ENDPOINT + "/" + id + "/generateQRCode");
        model.addAttribute("stageAdminDTO", convertToDTO(stage));
        return "admin/stageDetail";
    }

    @GetMapping("/{id}/generateQRCode")
    public void generateQRCode(final @PathVariable Long id, final HttpServletResponse response) throws IOException {
        final Stage stage = stageService.getStageById(id);
        response.setContentType("image/png");
        byte[] qrCode = qrCodeGeneratorService.generateQrCode(stage.getPin());
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(qrCode);
    }

    @GetMapping("delete/{id}")
    public String deleteStage(final @PathVariable Long id) {
        // TODO: delete stamps first
        stageService.delete(id);
        return "redirect:" + StagesController.ENDPOINT;
    }

    private static StageAdminDTO convertToDTO(final Stage stage) {
        return StageMapper.INSTANCE.stageToDTO(stage);
    }
}
