package cz.kostka.pochod.service;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dkostka on 6/20/2022.
 */
public abstract class AbstractPdfService {

    public void generate(HttpServletResponse response) throws IOException {
        // prepare document
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // prepare table
        final PdfPTable pdfTable = createTableWithData();

        document.add(pdfTable);
        document.close();
    }

    abstract PdfPTable createTableWithData();

    protected PdfPTable getEmptyPdfTable(final int columns, final int width) {
        final PdfPTable pdfPTable = new PdfPTable(columns);
        pdfPTable.setWidthPercentage(width);
        return pdfPTable;
    }
}
