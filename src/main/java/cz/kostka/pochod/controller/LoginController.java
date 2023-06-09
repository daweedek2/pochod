package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.util.LoginUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return LoginUtils.getRedirectUrlAfterLoginForRole(authentication.getAuthorities());
        }

        final WebAuthenticationDetails webAuthenticationDetails = ((WebAuthenticationDetails) authentication.getDetails());
        if (webAuthenticationDetails.getSessionId() != null) {
            LOG.info("SessionId '{}' is not logged in.", webAuthenticationDetails.getSessionId());
        } else {
            LOG.info("New browser opened login page.");
        }

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
            LOG.warn(
                    "Login failed, sessionId: {}",
                    ((WebAuthenticationDetails) authentication.getDetails()).getSessionId());
            return "login/login";
        }

        return LoginUtils.getRedirectUrlAfterLoginForRole(authentication.getAuthorities());
    }
}
