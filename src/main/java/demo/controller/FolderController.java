package demo.controller;

import demo.model.File;
import demo.model.Folder;
import demo.service.FileService;
import demo.service.FolderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
@AllArgsConstructor
public class FolderController {
    private FolderService folderService;
    private FileService fileService;

    @GetMapping("/getAll") //only for testing :)
    public ResponseEntity<List<Folder>> getAllFolders(){
        List<Folder> folderList = folderService.getAllFolders();
        return new ResponseEntity<>(folderList, HttpStatus.OK);
    }
    @GetMapping("/get/folderId/{id}")
    public ResponseEntity<Folder> getFolderByName(@PathVariable("id") long id){
        Folder folder = folderService.getFolderById(id);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }
    @GetMapping("/get/folderName/{folderName}")
    public ResponseEntity<Folder> getFileByName(@PathVariable("folderName") String folderName){
        Folder folder= folderService.getFolderByName(folderName);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Folder> addFolder(@RequestBody Folder folder){
        folderService.addFolder(folder);
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Folder> updateFolder(@RequestBody Folder folder){
        folderService.updateFolder(folder);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Folder> deleteFolderById(@PathVariable("id") Long id){
        folderService.deleteFolder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{folderName}/file/{fileName}")
    public ResponseEntity<Folder> addFileToFolder(
            @PathVariable("folderName") String folderName,
            @PathVariable("fileName") String fileName){
        Folder folder = folderService.getFolderByName(folderName);
        File file = fileService.getFileByFileName(fileName);
        folder.addFileToFolder(file);
        folderService.updateFolder(folder);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PutMapping("/update/{parentFolderName}/childfolder/{childFolderName}")
    public ResponseEntity<Folder> addChildFolderToFolder(
            @PathVariable("parentFolderName") String parentFolderName,
            @PathVariable("childFolderName") String childFolderName){
        Folder parentFolder = folderService.getFolderByName(parentFolderName);
        Folder childFolder = folderService.getFolderByName(childFolderName);
        parentFolder.addChildFolder(childFolder);
        folderService.updateFolder(parentFolder);
        return new ResponseEntity<>(parentFolder, HttpStatus.OK);
    }

    @PutMapping("/update/{childFolderName}/parentfolder/{parentFolderName}")
    public ResponseEntity<Folder> addParentFolderToFolder(
            @PathVariable("childFolderName") String childFolderName,
            @PathVariable("parentFolderName") String parentFolderName){
        Folder childFolder = folderService.getFolderByName(childFolderName);
        Folder parentFolder = folderService.getFolderByName(parentFolderName);
        childFolder.addParentFolder(parentFolder);
        folderService.updateFolder(childFolder);
        return new ResponseEntity<>(childFolder, HttpStatus.OK);
    }
}
