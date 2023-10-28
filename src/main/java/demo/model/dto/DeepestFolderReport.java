package demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeepestFolderReport {
    private String folderName;
    private int totalFiles;
    private List<DeepestFolderReport> subFolderReports;
}