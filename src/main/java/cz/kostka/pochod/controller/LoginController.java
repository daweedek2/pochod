package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.util.LoginUtils;
import cz.kostka.pochod.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private final GameInfoService gameInfoService;

    public LoginController(final GameInfoService gameInfoService) {
        this.gameInfoService = gameInfoService;
    }

    @GetMapping("/login")
    public String login(final Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return LoginUtils.getRedirectUrlAfterLoginForRole(authentication.getAuthorities());
        }

        model.addAttribute("gameEnded", TimeUtils.hasGameEnded(gameInfoService.get()));
        return "login/login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/successfulLogin")
    public String redirectUserBasedOnRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return "login/login";
        }

        LOG.info("User '{}' is successfully logged in.",
                ((CustomUserDetails) authentication.getPrincipal()).getUser().getUsername());
        return LoginUtils.getRedirectUrlAfterLoginForRole(authentication.getAuthorities());
    }
}
