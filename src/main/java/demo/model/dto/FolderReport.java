package demo.model.dto;

import demo.model.Folder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderReport {

    private List<Folder> topFoldersBySize;
    private List<Folder> topFoldersByFileCount;
    private List<Folder> topFoldersByAvgFileSize;
}
