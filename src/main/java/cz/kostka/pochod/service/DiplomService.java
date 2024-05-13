package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.enums.DiplomSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class DiplomService {

    private final StampService stampService;
    private final DiplomCreator diplomCreator;

    @Autowired
    public DiplomService(final StampService stampService, final DiplomCreator diplomCreator) {
        this.stampService = stampService;
        this.diplomCreator = diplomCreator;
    }

    public void createDiplom(final Player player, final HttpServletResponse response, final int year) throws IOException {
        if (stampService.hasPlayerSubmittedAllStamps(player, year)) {
            diplomCreator.download(player.getNickname(), response, DiplomSize.BIG, year);
        }
    }
}
