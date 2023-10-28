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
            throw new IllegalArgumentException("This file already belongs to this folder.");
        }

        if (file.getFolder() != null && !file.getFolder().equals(this)) {
            throw new IllegalArgumentException("This file is already assigned to another folder.");
        }

        if (!files.contains(file)) {
            files.add(file);
            file.setFolder(this);
        }
    }

    public void addParentFolder(Folder parentFolder) {
        if (parentFolder.equals(this)) {
            throw new IllegalArgumentException("You cannot add yourself as a parent folder.");
        }

        if (parentFolder.isDescendantOf(this)) {
            throw new IllegalArgumentException("FOLDER LOOP ALERT");
        }

        this.setParentFolder(parentFolder);
    }

    public void addChildFolder(Folder childFolder) {
        if (childFolder.equals(this)) {
            throw new IllegalArgumentException("You cannot add yourself as a parent folder.");
        }

        if (childFolder.isAncestorOf(this)) {
            throw new IllegalArgumentException("FOLDER LOOP ALERT");
        }

        if (!childrenFolders.contains(childFolder)) {
            childrenFolders.add(childFolder);
            childFolder.setParentFolder(this);
        }
    }

    private boolean isAncestorOf(Folder potentialDescendant) {
        Folder current = potentialDescendant.getParentFolder();
        while (current != null) {
            if (current.equals(this)) {
                return true;
            }
            current = current.getParentFolder();
        }
        return false;
    }

    private boolean isDescendantOf(Folder potentialAncestor) {
        return potentialAncestor.isAncestorOf(this);
    }

}