package demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String folderName;

    @OneToMany(mappedBy = "folder")
    private List<File> files;

    @JsonIgnore
    @OneToMany(mappedBy = "parentFolder")
    private List<Folder> childrenFolders;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;

    public void addFileToFolder(File file) {
        if (files.contains(file)) {
            throw new IllegalArgumentException("Ten plik już należy do tego folderu.");
        }

        if (file.getFolder() != null && !file.getFolder().equals(this)) {
            throw new IllegalArgumentException("Ten plik jest już przypisany do innego folderu.");
        }

        if (!files.contains(file)) {
            files.add(file);
            file.setFolder(this);
        }
    }

    public void addParentFolder(Folder parentFolder) {
        this.setParentFolder(parentFolder);
    }

    public void addChildFolder(Folder childFolder) {
        if (!childrenFolders.contains(childFolder)) {
            childrenFolders.add(childFolder);
            childFolder.setParentFolder(this);
        }
    }
}