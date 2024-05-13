package cz.kostka.pochod.controller;

import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.DiplomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pop/diplom")
public class DiplomController {

    private static final Logger LOG = LoggerFactory.getLogger(DiplomController.class);

    private final DiplomService diplomService;

    @Autowired
    public DiplomController(final DiplomService diplomService) {
        this.diplomService = diplomService;
    }

    @GetMapping("/{year}")
    public void download(
            @AuthenticationPrincipal final CustomUserDetails user, final HttpServletResponse response, @PathVariable final int year)
            throws IOException {
        LOG.info("User '{}' tries to download a diplom for year {}.", user.getUsername(), year);

        diplomService.createDiplom(user.getPlayer(), response, year);
    }
}
