package com.arka_app.arka.infraestructure.adapter.controller;



import com.arka_app.arka.application.dto.input.CategoryRequestDto;
import com.arka_app.arka.application.dto.output.CategoryResponseDto;
import com.arka_app.arka.application.mapper.CategoryMapper;
import com.arka_app.arka.domain.model.Category;
import com.arka_app.arka.domain.service.CategoryServicePort;
import com.arka_app.arka.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServicePort categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDto>> create(@Valid @RequestBody CategoryRequestDto dto) {
        Category category = CategoryMapper.toDomain(dto);
        Category saved = categoryService.createCategory(category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category created", CategoryMapper.toResponse(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> update(@PathVariable Long id,
                                                                   @Valid @RequestBody CategoryRequestDto dto) {
        Category category = CategoryMapper.toDomain(dto);
        Category updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category updated", CategoryMapper.toResponse(updated)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> getById(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category found", CategoryMapper.toResponse(category)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> list() {
        var list = categoryService.listCategories();
        return ResponseEntity.ok(new ApiResponse<>(true, "Category list", CategoryMapper.toResponseList(list)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category deleted", null));
    }
}
