package com.epam.mazaliuk.phones.util;

import com.epam.mazaliuk.phones.dto.phonenumber.PhoneNumberReturnDTO;
import com.epam.mazaliuk.phones.dto.user.UserMainReturnDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public final class GeneratePDFReport {

    private static final Logger logger = LoggerFactory.getLogger(GeneratePDFReport.class);

    private GeneratePDFReport() {

    }

    public static ByteArrayInputStream usersReport(List<UserMainReturnDTO> users) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{1, 3, 3, 3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("UserName", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("FirstName", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("LastName", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("City", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("PhoneNumbers", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);


            for (UserMainReturnDTO user : users) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(user.getId().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getUserName()));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getFirstName()));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getLastName()));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(user.getCity()));
                table.addCell(cell);

                StringBuilder builder = new StringBuilder();
                List<PhoneNumberReturnDTO> numbers = user.getPhoneNumbers();
                if (CollectionUtils.isNotEmpty(numbers)) {
                    numbers.forEach(n -> builder.append(n.getNumber() + "\n"));
                }

                cell = new PdfPCell(new Phrase(builder.toString()));
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
