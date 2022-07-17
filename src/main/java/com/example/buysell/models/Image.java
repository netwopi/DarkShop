package com.example.buysell.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id ;
    @Column(name = "name")
    private String name ;
    @Column(name = "originalFileNAme")
    private String originalFileNAme;
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

    public Image() {
    }

    public Image(long id, String name, String originalFileNAme, Long size, String contentType, boolean isPreviewImage, byte[] bytes) {
        this.id = id;
        this.name = name;
        this.originalFileNAme = originalFileNAme;
        this.size = size;
        this.contentType = contentType;
        this.isPreviewImage = isPreviewImage;
        this.bytes = bytes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFileNAme() {
        return originalFileNAme;
    }

    public void setOriginalFileNAme(String originalFileNAme) {
        this.originalFileNAme = originalFileNAme;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isPreviewImage() {
        return isPreviewImage;
    }

    public void setPreviewImage(boolean previewImage) {
        isPreviewImage = previewImage;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id && isPreviewImage == image.isPreviewImage && name.equals(image.name) && originalFileNAme.equals(image.originalFileNAme) && size.equals(image.size) && contentType.equals(image.contentType) && Arrays.equals(bytes, image.bytes) && product.equals(image.product);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, originalFileNAme, size, contentType, isPreviewImage, product);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalFileNAme='" + originalFileNAme + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", isPreviewImage=" + isPreviewImage +
                ", bytes=" + Arrays.toString(bytes) +
                ", product=" + product +
                '}';
    }
}
