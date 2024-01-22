package br.com.stoom.entities;

import jakarta.persistence.*;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "sku")
    private String sku;

    @Column(name = "price")
    private double price;

    @Column(name = "discount")
    private double discount;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    private BrandEntity brand;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories;

    public ProductEntity() {
    }

    public ProductEntity(
            String name,
            String description,
            double price,
            double discount,
            boolean active,
            String imageName,
            String sku,
            BrandEntity brand,
            Set<CategoryEntity> categories
    ) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.imageName = imageName;
        this.discount = discount;
        this.sku = sku;
        this.brand = brand;
        this.categories = categories;
    }

    public ProductEntity(
            Long id,
            String name,
            String description,
            double price,
            double discount,
            String imageName,
            String sku,
            boolean active,
            BrandEntity brand,
            Set<CategoryEntity> categories
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.active = active;
        this.imageName = imageName;
        this.sku = sku;
        this.brand = brand;
        this.categories = categories;
    }

    public ProductEntity updateEntity(ProductEntity productEntity) {

        this.name = productEntity.getName();
        this.description = productEntity.getDescription();
        this.price = productEntity.getPrice();
        this.discount = productEntity.getDiscount();
        this.active = productEntity.isActive();
        this.brand = productEntity.getBrand();
        this.imageName = productEntity.getImageName();
        this.sku = productEntity.getSku();

        updateCategory(productEntity);

        return this;
    }

    public void updateCategory(ProductEntity productEntity) {
        this.categories.clear();
        this.categories.addAll(productEntity.getCategories());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public String getSku() {
        return sku;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isActive() {
        return active;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }
}
