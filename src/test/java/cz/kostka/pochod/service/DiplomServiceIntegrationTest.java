package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.enums.DiplomSize;
import cz.kostka.pochod.util.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class DiplomServiceIntegrationTest extends AbstractIntegrationTest {

    private DiplomService diplomService;

    @Autowired
    private StampService stampService;
    private final DiplomCreator diplomCreatorMock = Mockito.mock(DiplomCreator.class);

    @BeforeEach
    void setup() {
        diplomService = new DiplomService(stampService, diplomCreatorMock);
    }

    @Test
    void createDiplom_Allowed() throws IOException {
        final Player player = createPlayer("pepa", 5566);
        createStage("first", 1, "one");

        startGame();

        stampService.submitStamp(new StampRequestDTO(player.getId(), "one"));

        diplomService.createDiplom(player, null, TimeUtils.getCurrentYear());

        verify(diplomCreatorMock).download("pepa", null, DiplomSize.BIG, TimeUtils.getCurrentYear());
    }

    @Test
    void createDiplom_AllowedWithMinimumStamps() throws IOException {
        final Player player = createPlayer("pepa", 5566);
        createStage("first", 1, "one");
        createStage("second", 2, "two");

        startGame(1);

        stampService.submitStamp(new StampRequestDTO(player.getId(), "one"));

        diplomService.createDiplom(player, null, TimeUtils.getCurrentYear());

        verify(diplomCreatorMock).download("pepa", null, DiplomSize.BIG, TimeUtils.getCurrentYear());
    }


    @Test
    void createDiplom_NotAllowed() throws IOException {
        final Player player = createPlayer("pepa", 5566);
        createStage("first", 1, "one");

        diplomService.createDiplom(player, null, TimeUtils.getCurrentYear());

        verify(diplomCreatorMock, never()).download("pepa", null, DiplomSize.BIG, TimeUtils.getCurrentYear());
    }
}