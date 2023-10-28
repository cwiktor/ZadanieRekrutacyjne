package demo.controller;


import demo.model.File;
import demo.model.Folder;
import demo.service.FileService;
import demo.service.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FolderService folderService;

    @GetMapping("/getAll") //only for testing :)
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/get/fileId/{id}")
    public File getFileByName(@PathVariable("id") long id) {
        return fileService.getFileById(id);
    }

    @GetMapping("/get/fileName/{fileName}")
    public File getFileByName(@PathVariable("fileName") String fileName) {
        return fileService.getFileByFileName(fileName);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public File addFile(@RequestBody File file) {
        return fileService.addFile(file);
    }

    @PutMapping("/update")
    public File updateFile(@RequestBody File file) {
        return fileService.updateFile(file);
    }

    @DeleteMapping("/delete/{id}")
    public Long deleteFileById(@PathVariable("id") Long id) {
        return fileService.deleteFile(id);
    }
    @PutMapping("/update/{fileName}/folder/{folderName}")
    public File addFolderToFile(
            @PathVariable("fileName") String fileName,
            @PathVariable("folderName") String folderName){
        File file = fileService.getFileByFileName(fileName);
        Folder folder = folderService.getFolderByName(folderName);
        file.addFolderToList(folder);
        fileService.updateFile(file);
        return file;
    }
}