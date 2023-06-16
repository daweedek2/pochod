package cz.kostka.pochod.service;

import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import cz.kostka.pochod.domain.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dkostka on 6/20/2022.
 */
@Service
public class TombolaPdfService extends AbstractPdfService {

    private final PlayerService playerService;
    private final StampService stampService;

    public TombolaPdfService(
            final PlayerService playerService,
            final StampService stampService) {
        this.playerService = playerService;
        this.stampService = stampService;
    }

    @Override
    PdfPTable createTableWithData() {
        final PdfPTable pdfTable = super.getEmptyPdfTable(2, 100);
        addPlayersToTable(pdfTable);
        return pdfTable;
    }

    private void addPlayersToTable(final PdfPTable pdfTable) {
        final List<Player> allPlayers = playerService.getAllPlayers();

        final List<Player> playersWithAllStamps = allPlayers.stream()
                .filter(stampService::hasPlayerSubmittedAllStamps)
                .collect(Collectors.toList());

        if (playersWithAllStamps.isEmpty()) {
            printNoPlayerWithAllStamps(pdfTable);
            return;
        }

        handleOddResult(playersWithAllStamps);

        for (final Player player : playersWithAllStamps) {
            addCell(pdfTable, extractPlayersInfo(player));
        }
    }

    private static void handleOddResult(final List<Player> playersWithAllStamps) {
        if (playersWithAllStamps.size() % 2 == 0) {
           return;
        }

        addDummyPlayerToCompleteColumn(playersWithAllStamps);
    }

    private static void addDummyPlayerToCompleteColumn(final List<Player> playersWithAllStamps) {
        playersWithAllStamps.add(new Player(null, null, null));
    }

    private static void printNoPlayerWithAllStamps(final PdfPTable pdfTable) {
        pdfTable.addCell("Zadny hrac se vsemi razitky.");
        pdfTable.addCell("Bohuzel.");
    }

    private static String extractPlayersInfo(final Player player) {
        return player.toString();
    }

    private static void addCell(final PdfPTable pdfTable, final String content) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(content));
        cell.setFixedHeight(125f);
        pdfTable.addCell(cell);
    }
}
