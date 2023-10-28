package demo.service;

import demo.model.dto.FileReport;
import demo.model.dto.FolderReport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportService {

    private FileService fileService;
    private FolderService folderService;

    public FileReport getFileStatsReport() {
        FileReport report = new FileReport();

        report.setTotalSize(fileService.getTotalFileSize());
        report.setAverageSize(fileService.getAverageFileSize());
        report.setHighestSize(fileService.getHighestFileSize());
        report.setLowestSize(fileService.getLowestFileSize());
        report.setNumberOfFiles(fileService.getNumberOfFiles());

        return report;
    }

    public FolderReport getFolderStatsReport(int topN) {
        FolderReport report = new FolderReport();

        report.setTopFoldersBySize(folderService.getTopFoldersBySize(topN));
        report.setTopFoldersByFileCount(folderService.getTopFoldersByFileCount(topN));
        report.setTopFoldersByAvgFileSize(folderService.getTopFoldersByAvgFileSize(topN));

        return report;
    }

}