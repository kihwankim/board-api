package com.cnu.spg.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoriesResponseDto {
    private List<ProjectCategoryElement> projectCategories;
}
