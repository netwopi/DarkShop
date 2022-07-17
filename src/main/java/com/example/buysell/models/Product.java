package com.example.buysell.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id ;
    @Column(name = "title")
    private String title;
    @Column(name = "description" ,columnDefinition = "text")
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "city")
    private String city;
    @Column(name = "author")
    private String author;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY ,
            mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime  dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated =LocalDateTime.now();
    }
    public void addImageToProduct(Image image){
        image.setProduct(this);
        images.add(image);

    }
    public Product() {
    }

    public Product(long id, String title, String description, int price, String city, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && title.equals(product.title) && description.equals(product.description) && city.equals(product.city) && author.equals(product.author) && images.equals(product.images) && previewImageId.equals(product.previewImageId) && dateOfCreated.equals(product.dateOfCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, city, author, images, previewImageId, dateOfCreated);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", city='" + city + '\'' +
                ", author='" + author + '\'' +
                ", images=" + images +
                ", previewImageId=" + previewImageId +
                ", dateOfCreated=" + dateOfCreated +
                '}';
    }
}
