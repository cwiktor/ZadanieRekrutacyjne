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




}