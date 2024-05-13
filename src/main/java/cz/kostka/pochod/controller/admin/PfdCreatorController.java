package cz.kostka.pochod.controller.admin;

import cz.kostka.pochod.service.FeedbackPdfService;
import cz.kostka.pochod.service.ReportPdfService;
import cz.kostka.pochod.service.TombolaPdfService;
import cz.kostka.pochod.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dkostka on 6/20/2022.
 */
@Controller
@RequestMapping("/admin/pdf")
public class PfdCreatorController {
    private final TombolaPdfService tombolaPdfService;
    private final FeedbackPdfService feedbackPdfService;
    private final ReportPdfService reportPdfService;

    public PfdCreatorController(
            final TombolaPdfService tombolaPdfService,
            final FeedbackPdfService feedbackPdfService,
            final ReportPdfService reportPdfService) {
        this.tombolaPdfService = tombolaPdfService;
        this.feedbackPdfService = feedbackPdfService;
        this.reportPdfService = reportPdfService;
    }

    @GetMapping("/tombola")
    public void generatePlayersWithAllStampsPdf(final HttpServletResponse response) throws IOException {
        prepareResponse(response, "pop%s_tombola".formatted(TimeUtils.getCurrentYear()));

        tombolaPdfService.generate(response);
    }

    @GetMapping("/feedback")
    public void generateFeedbackPdf(final HttpServletResponse response) throws IOException {
        prepareResponse(response, "pop%s_feedback".formatted(TimeUtils.getCurrentYear()));

        feedbackPdfService.generate(response);
    }

    @GetMapping("/report")
    public void generateReportPdf(final HttpServletResponse response) throws IOException {
        prepareResponse(response, "pop%s_report".formatted(TimeUtils.getCurrentYear()));

        reportPdfService.generate(response);
    }

    private void prepareResponse(final HttpServletResponse response, final String filenamePrefix) {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" +
                filenamePrefix +
                "_" +
                currentDateTime +
                ".pdf";
        response.setHeader(headerKey, headerValue);
    }
}
