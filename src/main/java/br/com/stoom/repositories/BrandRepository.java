package br.com.stoom.repositories;

import br.com.stoom.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
}
