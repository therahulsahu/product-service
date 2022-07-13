package com.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "report-generation-client", url = "http://localhost:8082/api/report/v1")
public interface ReportGenerationClient {

    @GetMapping("/pdf")
    ResponseEntity<Resource> downloadPdf();

    @GetMapping("/excel")
    void exportToExcelAndDownload();

    @GetMapping("/csv")
    void exportToCSV();

}
