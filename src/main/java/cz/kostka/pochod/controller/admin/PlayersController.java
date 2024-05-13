package cz.kostka.pochod.controller.admin;

import cz.kostka.pochod.dto.PlayerAdminDTO;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.mapper.PlayerMapper;
import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.RegistrationService;
import cz.kostka.pochod.service.StampService;
import cz.kostka.pochod.util.TimeUtils;
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
@RequestMapping("/admin/players")
public class PlayersController {
    private final PlayerService playerService;
    private final RegistrationService registrationService;
    private final StampService stampService;

    public PlayersController(
            final PlayerService playerService,
            final RegistrationService registrationService,
            final StampService stampService) {
        this.playerService = playerService;
        this.registrationService = registrationService;
        this.stampService = stampService;
    }

    @GetMapping
    public String getPlayersList(@AuthenticationPrincipal final CustomUserDetails admin, final Model model) {
        final var allPlayers = playerService.getAllPlayers();
        model.addAttribute("allPlayers", allPlayers);
        model.addAttribute("newPlayer", false);
        return "admin/players";
    }

    @GetMapping("/new")
    public String createNewPlayer(@AuthenticationPrincipal final CustomUserDetails admin, final Model model) {
        model.addAttribute("newPlayer", true);
        model.addAttribute("registrationDto", new RegistrationRequestDTO(null, null, null, null));
        return "admin/players";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayer(final @PathVariable("id") Long id) {
        stampService.deleteStampsOfPlayer(playerService.getPlayerById(id));
        playerService.deletePlayer(id);
        return "redirect:/admin/players";
    }

    @PostMapping("/create")
    public String createPlayer(final @ModelAttribute("registrationDto") RegistrationRequestDTO dto) {
        registrationService.register(dto);
        return "redirect:/admin/players";
    }

    @PostMapping("/update")
    public String updatePlayer(final @ModelAttribute("playerAdminDTO") PlayerAdminDTO dto) {
        playerService.update(dto);
        // TODO: edit also user here
        return "redirect:/admin/players";
    }

    @GetMapping("/{id}")
    public String getPlayerDetail(final @PathVariable("id") Long id, final Model model) {
        final var player = playerService.getPlayerById(id);
        model.addAttribute("player", player);
        model.addAttribute("stamps", stampService.getCountOfStagesWithStamp(player, TimeUtils.getCurrentYear()));
        model.addAttribute("playerAdminDTO", PlayerMapper.INSTANCE.playerToDTO(player));
        return "admin/playerDetail";
    }
}
