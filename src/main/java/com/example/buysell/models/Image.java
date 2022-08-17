package com.example.buysell.models;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id ;
    @Column(name = "name")
    private String name ;
    @Column(name = "originalFileName")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "contentType")
    private String contentType;
    @Column(name = "isPreviewImage")
    private boolean isPreviewImage;
    @Column(columnDefinition ="LONGBLOB")
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.REFRESH , fetch = FetchType.EAGER)
    private Product product;
}
