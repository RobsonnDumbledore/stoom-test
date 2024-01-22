package br.com.stoom.repositories;

import br.com.stoom.entities.BrandEntity;
import br.com.stoom.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    @Modifying
    @Query("DELETE FROM BrandEntity c WHERE c.id IN (:brandIds)")
    void deleteByIdIn(@Param("brandIds") Set<Long> brandIds);

    @Query("""
        SELECT b FROM BrandEntity b
            WHERE b.active = true
                AND (:brandName IS NULL OR :brandName = '' OR b.name LIKE :brandName%)
        """
    )
    Page<BrandEntity> findAllBrands(
            @Param("brandName") String brandName,
            Pageable pageable
    );

}
