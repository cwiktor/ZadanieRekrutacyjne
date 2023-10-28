package demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String fileName;
    private long size;

    @OneToMany
    private List<Folder> optionalFolders; // (eg. photos, text files, top secret, etc.)

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public void addFolderToList(Folder folder){
        if (optionalFolders == null) {
            optionalFolders = new ArrayList<>();
        }

        if (folder.equals(this.folder)) {
            throw new IllegalArgumentException("The current folder cannot be added as optional.");
        }

        if (!optionalFolders.contains(folder)) {
            optionalFolders.add(folder);
        }
        else {
            throw new IllegalArgumentException("This folder is already added as optional.");
        }
    }

}