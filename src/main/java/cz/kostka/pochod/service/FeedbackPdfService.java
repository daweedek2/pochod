package cz.kostka.pochod.service;

import com.lowagie.text.pdf.PdfPTable;
import cz.kostka.pochod.domain.Feedback;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by dkostka on 6/20/2022.
 */
@Service
public class FeedbackPdfService extends AbstractPdfService {

    private final FeedbackService feedbackService;

    public FeedbackPdfService(final FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    PdfPTable createTableWithData() {
        final PdfPTable pdfTable = super.getEmptyPdfTable(3, 100);
        pdfTable.addCell("Hráč");
        pdfTable.addCell("Čas");
        pdfTable.addCell("Feedback");
        addFeedbacksToTable(pdfTable);
        return pdfTable;
    }

    private void addFeedbacksToTable(final PdfPTable pdfTable) {
        final List<Feedback> allFeedbacks = feedbackService.getAllFeedbacks();

        if (allFeedbacks.isEmpty()) {
            pdfTable.addCell("Zadny hrac");
            pdfTable.addCell("zatim");
            pdfTable.addCell("Nenechal zadny feedback");
        }

        for (final Feedback feedback : allFeedbacks) {
            pdfTable.addCell(feedback.getPlayer().getNickname());
            pdfTable.addCell(feedback.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            pdfTable.addCell(feedback.getText());
        }
    }
}
