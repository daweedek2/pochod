package cz.kostka.pochod.controller;

import cz.kostka.pochod.enums.DiplomSize;
import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.DiplomService;
import cz.kostka.pochod.service.GameInfoService;
import cz.kostka.pochod.service.PlayerService;
import cz.kostka.pochod.service.StampService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pop/diplom")
public class DiplomController extends PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(DiplomController.class);

    private final DiplomService diplomService;

    @Autowired
    public DiplomController(
            final PlayerService playerService,
            final StampService stampService,
            final GameInfoService gameInfoService,
            final DiplomService diplomService) {
        super(playerService, stampService, gameInfoService);
        this.diplomService = diplomService;
    }

    @GetMapping
    public void download(
            @AuthenticationPrincipal final CustomUserDetails user, final HttpServletResponse response)
            throws IOException {
        LOG.info("User '{}' tries to download a diplom.", user.getUsername());

        diplomService.download(user.getUsername(), response, DiplomSize.BIG);
    }
}
