package com.arka_app.arka.application.dto.input;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {
    @NotBlank(message = "Category name is required")
    private String name;
    private String description;
    private Boolean active;
}
