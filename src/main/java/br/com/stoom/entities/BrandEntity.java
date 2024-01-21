package br.com.stoom.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "brand")
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

    public BrandEntity() {
    }

    public BrandEntity(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public BrandEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public BrandEntity(Long id) {
        this.id = id;
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

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }
}
