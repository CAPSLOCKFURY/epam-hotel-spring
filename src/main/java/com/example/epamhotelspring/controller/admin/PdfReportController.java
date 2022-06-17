package com.example.epamhotelspring.controller.admin;

import com.example.epamhotelspring.converters.StringToDateNullableConverter;
import com.example.epamhotelspring.dto.RoomRegistryReportDTO;
import com.example.epamhotelspring.pdf.RoomRegistryPDFReport;
import com.example.epamhotelspring.service.admin.AdminPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/manager/report")
public class PdfReportController {

    private final AdminPdfService adminPdfService;

    private final StringToDateNullableConverter stringToDateConverter;


    @GetMapping("/configure")
    public String configurePdfReport(){
        return "manager/configure-report";
    }

    @GetMapping("/pdf")
    public void roomRegistryPdfReport(HttpServletResponse response, Locale locale,
                                      @RequestParam(value = "checkInDate", required = false) String checkInDate, @RequestParam(value = "checkOutDate", required = false) String checkOutDate,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "size", required = false, defaultValue = "1000") int size) throws IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();
        Pageable pageable = PageRequest.of(page - 1, size);
        List<RoomRegistryReportDTO> data = adminPdfService.getRoomRegistryReportData(stringToDateConverter.convert(checkInDate), stringToDateConverter.convert(checkOutDate), pageable);
        RoomRegistryPDFReport pdfReport = new RoomRegistryPDFReport(out, data, locale);
        pdfReport.buildDocument();
    }

    @Autowired
    public PdfReportController(AdminPdfService adminPdfService,StringToDateNullableConverter stringToDateConverter) {
        this.adminPdfService = adminPdfService;
        this.stringToDateConverter = stringToDateConverter;
    }
}
