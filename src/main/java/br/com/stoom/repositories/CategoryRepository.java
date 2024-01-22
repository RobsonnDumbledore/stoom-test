package br.com.stoom.repositories;

import java.util.Set;
import br.com.stoom.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Modifying
    @Query("DELETE FROM CategoryEntity c WHERE c.id IN (:categoryIds)")
    void deleteByIdIn(@Param("categoryIds") Set<Long> categoryIds);


    @Query("""
        SELECT c FROM CategoryEntity c
            WHERE c.active = true
                AND (:categoryName IS NULL OR :categoryName = '' OR c.name LIKE :categoryName%)
        """
    )
    Page<CategoryEntity> findAllCategories(
            @Param("categoryName") String categoryName,
            Pageable pageable
    );

}
