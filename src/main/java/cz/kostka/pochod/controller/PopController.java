package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/3/2022.
 */
@Controller
@RequestMapping("/pop")
public class PopController {

    @GetMapping
    public String viewHome(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        return "pop/welcome";
    }

    @GetMapping("/progress")
    public String viewProgress(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        return "pop/progress";
    }
}
