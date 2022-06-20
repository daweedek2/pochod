package cz.kostka.pochod.service;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import cz.kostka.pochod.domain.Player;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dkostka on 6/20/2022.
 */
@Service
public class PdfService {

    private final PlayerService playerService;
    private final StampService stampService;
    private final StageService stageService;

    public PdfService(
            final PlayerService playerService,
            final StampService stampService,
            final StageService stageService) {
        this.playerService = playerService;
        this.stampService = stampService;
        this.stageService = stageService;
    }

    public void generate(HttpServletResponse response) throws IOException {
        // prepare document
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // prepare table
        final PdfPTable pdfTable = new PdfPTable(2);
        pdfTable.setWidthPercentage(100);

        addDataToTable(pdfTable);

        document.add(pdfTable);
        document.close();
    }

    private void addDataToTable(final PdfPTable pdfTable) {
        final List<Player> allPlayers = playerService.getAllPlayers();

        final List<Player> playersWithAllStamps = allPlayers.stream()
                .filter(player -> stampService.getStampsByPlayer(player).size() == getNumberOfStages())
                .collect(Collectors.toList());

        if (playersWithAllStamps.size() == 0) {
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

    public int getNumberOfStages() {
        return stageService.getAllStages().size();
    }
}
