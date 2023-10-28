package demo.controller;


import demo.model.File;
import demo.service.FileService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/getAll") //only for testing :)
    public ResponseEntity<List<File>> getAllFiles(){
        List<File> fileList = fileService.getAllFiles();
        return new ResponseEntity<>(fileList, HttpStatus.OK);
    }
    @GetMapping("/get/fileId/{id}")
    public ResponseEntity<File> getFileById(@PathVariable("id") long id){
        File file= fileService.getFileById(id);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }
    @GetMapping("/get/fileName/{fileName}")
    public ResponseEntity<File> getFileByName(@PathVariable("fileName") String fileName){
        File file= fileService.getFileByFileName(fileName);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<File> addFile(@RequestBody File file){
        fileService.addFile(file);
        return new ResponseEntity<>(file, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<File> updateFile(@RequestBody File file){
        fileService.updateFile(file);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<File> deleteFileById(@PathVariable("id") Long id){
        fileService.deleteFile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}