package demo.controller;

import demo.model.File;
import demo.model.Folder;
import demo.service.FileService;
import demo.service.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
@AllArgsConstructor
public class FolderController {
    private FolderService folderService;
    private FileService fileService;

    @GetMapping("/getAll") //only for testing :)
    public List<Folder> getAllFolders(){
        return folderService.getAllFolders();
    }

    @GetMapping("/get/folderId/{id}")
    public Folder getFolderByName(@PathVariable("id") long id){
        return folderService.getFolderById(id);
    }

    @GetMapping("/get/folderName/{folderName}")
    public Folder getFileByName(@PathVariable("folderName") String folderName){
        return folderService.getFolderByName(folderName);
    }

    @PostMapping("/add")
    public Folder addFolder(@RequestBody Folder folder){
        return folderService.addFolder(folder);
    }

    @PutMapping("/update")
    public Folder updateFolder(@RequestBody Folder folder){
        return folderService.updateFolder(folder);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFolderById(@PathVariable("id") Long id){
        folderService.deleteFolder(id);
    }

    @PutMapping("/update/{folderName}/file/{fileName}")
    public Folder addFileToFolder(
            @PathVariable("folderName") String folderName,
            @PathVariable("fileName") String fileName){
        Folder folder = folderService.getFolderByName(folderName);
        File file = fileService.getFileByFileName(fileName);
        folder.addFileToFolder(file);
        folderService.updateFolder(folder);
        return folder;
    }

    @PutMapping("/update/{parentFolderName}/childfolder/{childFolderName}")
    public Folder addChildFolderToFolder(
            @PathVariable("parentFolderName") String parentFolderName,
            @PathVariable("childFolderName") String childFolderName){
        Folder parentFolder = folderService.getFolderByName(parentFolderName);
        Folder childFolder = folderService.getFolderByName(childFolderName);
        parentFolder.addChildFolder(childFolder);
        folderService.updateFolder(parentFolder);
        return parentFolder;
    }

    @PutMapping("/update/{childFolderName}/parentfolder/{parentFolderName}")
    public Folder addParentFolderToFolder(
            @PathVariable("childFolderName") String childFolderName,
            @PathVariable("parentFolderName") String parentFolderName){
        Folder childFolder = folderService.getFolderByName(childFolderName);
        Folder parentFolder = folderService.getFolderByName(parentFolderName);
        childFolder.addParentFolder(parentFolder);
        folderService.updateFolder(childFolder);
        return childFolder;
    }
}
