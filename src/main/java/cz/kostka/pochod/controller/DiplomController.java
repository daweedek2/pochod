package cz.kostka.pochod.controller;

import cz.kostka.pochod.enums.DiplomSize;
import cz.kostka.pochod.security.CustomUserDetails;
import cz.kostka.pochod.service.DiplomCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pop/diplom")
public class DiplomController {

    private static final Logger LOG = LoggerFactory.getLogger(DiplomController.class);

    @GetMapping
    public void download(
            @AuthenticationPrincipal final CustomUserDetails user, final HttpServletResponse response)
            throws IOException {
        LOG.info("User '{}' tries to download a diplom.", user.getUsername());

        DiplomCreator.download(user.getPlayer().getNickname(), response, DiplomSize.BIG);
    }
}
