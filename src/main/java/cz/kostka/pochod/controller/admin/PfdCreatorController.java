package cz.kostka.pochod.controller.admin;

import cz.kostka.pochod.service.PdfService;
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
    private final PdfService pdfService;

    public PfdCreatorController(final PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping
    public void generatePdf(final HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        pdfService.generate(response);
    }
}
