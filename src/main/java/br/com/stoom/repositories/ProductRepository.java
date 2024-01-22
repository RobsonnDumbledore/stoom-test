package br.com.stoom.repositories;

import java.util.Optional;
import java.util.Set;

import br.com.stoom.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
                SELECT p FROM ProductEntity p
                    JOIN p.categories c
                    WHERE c.name = :category
                        AND c.active = true
                        AND p.active = true
                        AND p.brand.active = true
            """)
    Page<ProductEntity> findByCategory(
            @Param("category") String category,
            Pageable pageable
    );

    @Query(
            """
            
                SELECT p FROM ProductEntity p
                    JOIN p.categories c
                    WHERE p.brand.name = :brand
                        AND p.active = true
                        AND p.brand.active = true
                        AND c.active = true
                    
            """
    )
    Page<ProductEntity> findByBrand(
            @Param("brand") String brand,
            Pageable pageable
    );

    @Query("""
        SELECT p FROM ProductEntity p
            JOIN p.categories c
            WHERE p.brand.active = true
                AND p.active = true
                AND c.active = true
                AND (:productName IS NULL OR :productName = '' OR p.name LIKE :productName%)
        """
    )
    Page<ProductEntity> findAllProducts(
            @Param("productName") String productName,
            Pageable pageable
    );


    @EntityGraph(attributePaths = {"brand", "categories"})
    Optional<ProductEntity> findById(Long id);

    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.id IN (:productIds)")
    void deleteByIdIn(@Param("productIds") Set<Long> productIds);

}
