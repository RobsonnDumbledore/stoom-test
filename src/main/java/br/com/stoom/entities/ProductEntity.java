package br.com.stoom.entities;

import jakarta.persistence.*;

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

    @Column(name = "price")
    private double price;

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

    public ProductEntity(String name, String description, double price, boolean active,
                         BrandEntity brand, Set<CategoryEntity> categories) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.brand = brand;
        this.categories = categories;
    }

    public ProductEntity(Long id, String name, String description, double price,
                         boolean active, BrandEntity brand, Set<CategoryEntity> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.brand = brand;
        this.categories = categories;
    }

    public ProductEntity updateEntity(ProductEntity productEntity) {

        this.name = productEntity.getName();
        this.description = productEntity.getDescription();
        this.price = productEntity.getPrice();
        this.active = productEntity.isActive();
        this.brand = productEntity.getBrand();

        this.categories.clear();
        this.categories.addAll(productEntity.getCategories());

        return this;
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

    public double getPrice() {
        return price;
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
