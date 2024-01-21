package br.com.stoom.utils;

import br.com.stoom.entities.BrandEntity;
import br.com.stoom.entities.CategoryEntity;
import br.com.stoom.entities.ProductEntity;
import br.com.stoom.repositories.BrandRepository;
import br.com.stoom.repositories.CategoryRepository;
import br.com.stoom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataUtils {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DataUtils(CategoryRepository categoryRepository, BrandRepository brandRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    public void initData() {

        final var categories = List.of(
                new CategoryEntity( "Categoria04", true),
                new CategoryEntity( "Categoria05", true),
                new CategoryEntity( "Categoria06", true)
        );

        categoryRepository.saveAll(categories);

        final var brands = List.of(
                new BrandEntity( "Marca01", true),
                new BrandEntity( "Marca02", true),
                new BrandEntity( "Marca03", true)
        );

        brandRepository.saveAll(brands);


        final var products = List.of(
                new ProductEntity( "Produto04", "Descricao do produto 04", 100.50, true, new BrandEntity(4L), Set.of(new CategoryEntity(4L))),
                new ProductEntity( "Produto05", "Descricao do produto 05", 200.50, true, new BrandEntity(5L), Set.of(new CategoryEntity(5L))),
                new ProductEntity( "Produto06", "Descricao do produto 06", 300.50, true, new BrandEntity(6L), Set.of(new CategoryEntity(6L)))
        );

        productRepository.saveAll(products);

    }

}
