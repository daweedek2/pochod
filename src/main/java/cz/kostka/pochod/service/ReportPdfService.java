package cz.kostka.pochod.service;

import com.lowagie.text.pdf.PdfPTable;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.domain.Stamp;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by dkostka on 7/12/2022.
 */
@Service
public class ReportPdfService extends AbstractPdfService {

    private final PlayerService playerService;
    private final StageService stageService;
    private final StampService stampService;

    public ReportPdfService(
            final PlayerService playerService,
            final StageService stageService,
            final StampService stampService) {
        this.playerService = playerService;
        this.stageService = stageService;
        this.stampService = stampService;
    }

    @Override
    PdfPTable createTableWithData() {
        final PdfPTable pdfTable = super.getEmptyPdfTable(1, 80);

        pdfTable.addCell("***** Hráči *****");
        pdfTable.addCell("");
        addPlayersReportToTable(pdfTable);

        pdfTable.addCell("***** Stanoviště *****");
        pdfTable.addCell("");
        addStagesReportToTable(pdfTable);

        return pdfTable;
    }

    private void addStagesReportToTable(final PdfPTable pdfTable) {
        final List<Stage> allStages = stageService.getAllStages();

        for (final Stage stage : allStages) {
            // add stage name as header
            pdfTable.addCell("\n" + stage.getNumber() + ". " + stage.getName());
            final PdfPTable stageStampsTable = getEmptyPdfTable(3, 75);
            final List<Stamp> stageStamps = stampService.getStampsByStageOrdered(stage);
            int counter = 1;

            // add sub-table with stamps to the table
            for (final Stamp stamp : stageStamps) {
                stageStampsTable.addCell(String.valueOf(counter));
                stageStampsTable.addCell(stamp.getPlayer().getNickname());
                stageStampsTable.addCell(stamp.getTimestamp().format(DateTimeFormatter.ISO_TIME));
                counter++;
            }

            pdfTable.addCell(stageStampsTable);
        }
    }

    private void addPlayersReportToTable(final PdfPTable pdfTable) {
        final List<Player> allPlayers = playerService.getAllPlayers();
        int counter = 1;

        for (final Player player : allPlayers) {
            pdfTable.addCell("\n"
                    + counter + " "
                    + printAllStampsCollected(player)
                    + player.getId() + " "
                    + player.getNickname());
            final PdfPTable playerStampsTable = getEmptyPdfTable(2, 75);
            stampService.getAllStampsByPlayerOrdered(player)
                    .forEach(stamp -> addPlayersStampToTable(stamp, playerStampsTable));
            pdfTable.addCell(playerStampsTable);
            counter++;
        }
    }

    private void addPlayersStampToTable(final Stamp stamp, final PdfPTable playerStampsTable) {
        playerStampsTable.addCell(String.valueOf(stamp.getStage().getId()));
        playerStampsTable.addCell(stamp.getTimestamp().format(DateTimeFormatter.ISO_TIME));
    }

    private String printAllStampsCollected(final Player player) {
        return stampService.hasPlayerSubmittedAllStamps(player) ? "WINNER " : "";
    }
}
