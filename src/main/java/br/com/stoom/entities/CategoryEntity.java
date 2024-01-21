package br.com.stoom.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(mappedBy = "categories")
    private Set<ProductEntity> products;

    public CategoryEntity() {
    }

    public CategoryEntity(Long id) {
        this.id = id;
    }

    public CategoryEntity(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public CategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        this.name = categoryEntity.name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }
}
