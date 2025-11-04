package com.arka_app.arka.application.usecase;

import com.arka_app.arka.domain.model.Category;
import com.arka_app.arka.domain.repository.CategoryRepositoryPort;
import com.arka_app.arka.domain.service.CategoryServicePort;
import com.arka_app.arka.shared.exeptio.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryServicePort {

    private final CategoryRepositoryPort categoryRepository;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        category.setActive(category.getActive() != null ? category.getActive() : true);
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new IllegalArgumentException("Category name already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));

        existing.setName(category.getName() != null ? category.getName() : existing.getName());
        existing.setDescription(category.getDescription() != null ? category.getDescription() : existing.getDescription());
        existing.setActive(category.getActive() != null ? category.getActive() : existing.getActive());

        return categoryRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
