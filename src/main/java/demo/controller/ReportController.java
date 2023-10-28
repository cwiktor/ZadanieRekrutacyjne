package demo.controller;

import demo.model.dto.FileReport;
import demo.model.dto.FolderReport;
import demo.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {

    private ReportService reportService;

    @GetMapping("/file-stats")
    public FileReport getFileStatsReport() {
        return reportService.getFileStatsReport();
    }

    @GetMapping("/folder-stats/{N}")
    public FolderReport getFolderStatsReport(@PathVariable int N) {
        return reportService.getFolderStatsReport(N);
    }


}