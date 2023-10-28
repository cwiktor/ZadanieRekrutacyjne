package demo.service;

import demo.exception.FileNotFoundException;
import demo.model.File;
import demo.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    } //only for testing :)
    public File getFileById(long id) {
        return fileRepository.findById(id).
                orElseThrow(()->new FileNotFoundException("File not found"));
    }
    public File getFileByFileName(String fileName) {
        return fileRepository.findByFileName(fileName).
                orElseThrow(()->new FileNotFoundException("File not found"));
    }
    public File addFile(File file) {
        return fileRepository.save(file);
    }
    public File updateFile(File file) {
        return fileRepository.save(file);
    }
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    public long getTotalFileSize() {
        List<File> allFiles = fileRepository.findAll();
        return allFiles.stream().mapToLong(File::getSize).sum();
    }

    public long getAverageFileSize() {
        List<File> allFiles = fileRepository.findAll();
        return allFiles.isEmpty() ? 0 : (long) allFiles.stream().mapToLong(File::getSize).average().orElse(0);
    }

    public long getHighestFileSize() {
        List<File> allFiles = fileRepository.findAll();
        return allFiles.isEmpty() ? 0 : allFiles.stream().mapToLong(File::getSize).max().orElse(0);
    }

    public long getLowestFileSize() {
        List<File> allFiles = fileRepository.findAll();
        return allFiles.isEmpty() ? 0 : allFiles.stream().mapToLong(File::getSize).min().orElse(0);
    }

    public int getNumberOfFiles() {
        List<File> allFiles = fileRepository.findAll();
        return allFiles.size();
    }

}
