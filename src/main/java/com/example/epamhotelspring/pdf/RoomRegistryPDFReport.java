package com.example.epamhotelspring.pdf;


import com.example.epamhotelspring.dto.RoomRegistryReportDTO;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RoomRegistryPDFReport {

    private final OutputStream out;

    private final List<RoomRegistryReportDTO> data;

    private final Locale locale;

    private ResourceBundle bundle;

    public static final String FONT = "/fonts/Arial.ttf";

    public RoomRegistryPDFReport(OutputStream out, List<RoomRegistryReportDTO> data, Locale locale) {
        this.out = out;
        this.data = data;
        this.locale = locale;
    }

    public void buildDocument() throws IOException{
        PdfWriter writer = new PdfWriter(out);
        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        bundle = ResourceBundle.getBundle("pdf", locale);
        PdfDocument pdfDocument = new PdfDocument(writer);
        addMetaData(pdfDocument.getDocumentInfo());
        Document document = new Document(pdfDocument);
        document.setFont(font);
        writeHeader(document);
        Table dataTable = new Table(new float[]{100f, 200f, 200f, 200f, 200f, 100f});
        document.add(dataTable);
        addColumns(dataTable);
        writeRows(dataTable);
        dataTable.complete();
        document.close();
        out.flush();
    }

    private void addColumns(Table table){
        Cell userIdCol = new Cell();
        userIdCol.add(new Paragraph(bundle.getString("userId")));
        Cell fistNameCol = new Cell();
        fistNameCol.add(new Paragraph(bundle.getString("firstName")));
        Cell lastNameCol = new Cell();
        lastNameCol.add(new Paragraph(bundle.getString("lastName")));
        Cell checkInDateCol = new Cell();
        checkInDateCol.add(new Paragraph(bundle.getString("checkInDate")));
        Cell checkOutDateCol = new Cell();
        checkOutDateCol.add(new Paragraph(bundle.getString("checkOutDate")));
        Cell roomIdCol = new Cell();
        roomIdCol.add(new Paragraph(bundle.getString("roomId")));
        table.addCell(userIdCol).addCell(fistNameCol).addCell(lastNameCol).addCell(checkInDateCol).addCell(checkOutDateCol).addCell(roomIdCol);
    }


    private void writeRows(Table table){
        data.forEach(model -> {
            Cell userId = new Cell().add(new Paragraph(String.valueOf(model.getUserId())));
            Cell firstName = new Cell().add(new Paragraph(model.getUserFirstName()));
            Cell lastName = new Cell().add(new Paragraph(model.getUserLastName()));
            Cell checkInDate = new Cell().add(new Paragraph(model.getCheckInDate().toString()));
            Cell checkOutDate = new Cell().add(new Paragraph(model.getCheckOutDate().toString()));
            String roomUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/room/"+model.getRoomId()).build().toUriString();
            Link roomIdHref = new Link(String.valueOf(model.getRoomId()),
                    PdfAction.createURI(roomUrl));
            Paragraph roomIdParagraph = new Paragraph(roomIdHref.setFontColor(ColorConstants.BLUE).setUnderline());
            Cell roomId = new Cell().add(roomIdParagraph);
            table.addCell(userId).addCell(firstName).addCell(lastName).addCell(checkInDate).addCell(checkOutDate).addCell(roomId);
        });
    }

    private void addMetaData(PdfDocumentInfo info){
        info.setTitle("Room Registry Report");
        info.setCreator("Epam Hotel");
        info.setAuthor("Epam Hotel");
    }

    private void writeHeader(Document document){
        Paragraph header = new Paragraph(bundle.getString("roomRegistryReport"));
        header.setTextAlignment(TextAlignment.CENTER);
        document.add(header);
    }

}
