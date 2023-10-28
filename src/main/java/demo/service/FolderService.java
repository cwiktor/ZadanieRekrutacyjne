package demo.service;

import demo.exception.FolderNotFoundException;
import demo.model.File;
import demo.model.Folder;
import demo.repository.FolderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FolderService {

    private FolderRepository folderRepository;

    @Transactional
    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    @Transactional
    public Folder getFolderByName(String name) {
        return folderRepository.findByFolderName(name)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found"));
    }

    @Transactional
    public Folder getFolderById(long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found"));
    }

    @Transactional
    public Folder addFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    @Transactional
    public Folder updateFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    @Transactional
    public void deleteFolder(Long id) {
        folderRepository.deleteById(id);
    }

    @Transactional
    public long getTotalSizeOfFolderAndSubfolders(Folder folder) {
        List<File> filesInFolder = folder.getFiles();
        long totalSize = filesInFolder.stream().mapToLong(File::getSize).sum();

        List<Folder> subFolders = folder.getChildrenFolders();
        for (Folder subFolder : subFolders) {
            totalSize += getTotalSizeOfFolderAndSubfolders(subFolder);
        }
        return totalSize;
    }

    @Transactional
    public int getTotalFileCountInFolderAndSubfolders(Folder folder) {
        List<File> filesInFolder = folder.getFiles();
        int fileCount = filesInFolder.size();

        List<Folder> subFolders = folder.getChildrenFolders();
        for (Folder subFolder : subFolders) {
            fileCount += getTotalFileCountInFolderAndSubfolders(subFolder);
        }
        return fileCount;
    }

    @Transactional
    public double getAverageFileSizeInFolderAndSubfolders(Folder folder) {
        int totalFileCount = getTotalFileCountInFolderAndSubfolders(folder);
        long totalSize = getTotalSizeOfFolderAndSubfolders(folder);

        return totalFileCount == 0 ? 0 : (double) totalSize / totalFileCount;
    }

    @Transactional
    public List<Folder> getTopFoldersBySize(int N) {
        List<Folder> allFolders = folderRepository.findAll();
        allFolders.sort(Comparator.comparingLong(this::getTotalSizeOfFolderAndSubfolders).reversed());
        return allFolders.stream().limit(N).collect(Collectors.toList());
    }

    @Transactional
    public List<Folder> getTopFoldersByFileCount(int N) {
        List<Folder> allFolders = folderRepository.findAll();
        allFolders.sort(Comparator.comparingInt(this::getTotalFileCountInFolderAndSubfolders).reversed());
        return allFolders.stream().limit(N).collect(Collectors.toList());
    }

    @Transactional
    public List<Folder> getTopFoldersByAvgFileSize(int N) {
        List<Folder> allFolders = folderRepository.findAll();
        allFolders.sort(Comparator.comparingDouble(this::getAverageFileSizeInFolderAndSubfolders).reversed());
        return allFolders.stream().limit(N).collect(Collectors.toList());
    }


}