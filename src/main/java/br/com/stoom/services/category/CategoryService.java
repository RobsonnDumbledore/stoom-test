package br.com.stoom.services.category;

import br.com.stoom.entities.CategoryEntity;
import br.com.stoom.utils.Pagination;
import br.com.stoom.utils.SearchQuery;

import java.util.Map;
import java.util.Set;

public interface CategoryService {

    void createCategory(Set<CategoryEntity> categoriesEntity);
    void changeCategoryStatus(Map<Long, Boolean> categoryStatus);
    void updateCategory(CategoryEntity categoryEntity);
    void deleteCategory(Set<Long> categoryIds);
    Pagination<CategoryEntity> findAllCategories(String categoryName, SearchQuery searchQuery);
    CategoryEntity findCategoryById(Long categoryId);

}
