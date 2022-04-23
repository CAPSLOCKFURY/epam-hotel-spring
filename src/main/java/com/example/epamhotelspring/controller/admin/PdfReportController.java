package com.example.epamhotelspring.controller.admin;

import com.example.epamhotelspring.dto.RoomRegistryReportDTO;
import com.example.epamhotelspring.pdf.RoomRegistryPDFReport;
import com.example.epamhotelspring.repository.admin.AdminRoomRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/manager/report")
public class PdfReportController {

    private final AdminRoomRegistryRepository adminRoomRegistryRepository;

    @GetMapping("/pdf")
    public void testPdf(HttpServletResponse response, Locale locale) throws IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        List<RoomRegistryReportDTO> data = adminRoomRegistryRepository.findRoomRegistriesForPdfReport();
        RoomRegistryPDFReport pdfReport = new RoomRegistryPDFReport(out, data, locale);
        pdfReport.buildDocument();
    }

    @Autowired
    public PdfReportController(AdminRoomRegistryRepository adminRoomRegistryRepository) {
        this.adminRoomRegistryRepository = adminRoomRegistryRepository;
    }
}
