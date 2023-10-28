package demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileReport {
    private long totalSize;
    private long averageSize;
    private long highestSize;
    private long lowestSize;
    private long numberOfFiles;

}