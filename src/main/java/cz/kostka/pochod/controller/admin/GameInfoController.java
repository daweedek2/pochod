package cz.kostka.pochod.controller.admin;

import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.dto.GameInfoDTO;
import cz.kostka.pochod.mapper.GameInfoMapper;
import cz.kostka.pochod.service.GameInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/20/2022.
 */
@Controller
@RequestMapping("/admin/game")
public class GameInfoController {

    private final GameInfoService gameInfoService;

    public GameInfoController(final GameInfoService gameInfoService) {
        this.gameInfoService = gameInfoService;
    }

    @GetMapping
    public String getGameInfo(final Model model) {
        model.addAttribute("gameInfoDto",
                GameInfoMapper.INSTANCE.gameInfoToDto(gameInfoService.get().orElse(new GameInfo())));
        return "admin/gameInfoDetail";
    }

    @PostMapping("/update")
    public String updateGameInfo(final @ModelAttribute("gameInfoDto") GameInfoDTO dto) {
        gameInfoService.update(dto);
        return "redirect:/admin/game";
    }

}
