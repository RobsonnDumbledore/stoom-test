package br.com.stoom.services.category;

import br.com.stoom.entities.CategoryEntity;

import java.util.Map;
import java.util.Set;

public interface CategoryService {

    void createCategory(Set<CategoryEntity> categoriesEntity);
    void changeCategoryStatus(Map<Long, Boolean> categoryStatus);
    void updateCategory(CategoryEntity categoryEntity);
    CategoryEntity findCategoryById(Long categoryId);

}
