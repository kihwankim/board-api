package com.cnu.spg.board.service;

import com.cnu.spg.board.domain.project.ProjectCategory;
import com.cnu.spg.board.dto.response.CategoriesResponseDto;
import com.cnu.spg.board.dto.response.ProjectCategoryElement;
import com.cnu.spg.board.repository.project.ProjectCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectCategoryRepository projectCategoryRepository;

    public CategoriesResponseDto findAllUserCategories(Long userId) {
        List<ProjectCategory> categories = projectCategoryRepository.findByCategoriesById(userId);
        List<ProjectCategory> parentsCategories = categories.stream().filter(projectCategory -> projectCategory.getParent() == null)
                .collect(Collectors.toList());

        return new CategoriesResponseDto(createTreeCategories(parentsCategories));
    }

    private List<ProjectCategoryElement> createTreeCategories(List<ProjectCategory> parentsCategories) {
        Queue<ProjectCategory> projectCategoryQueue = new LinkedList<>();
        List<ProjectCategoryElement> projectCategoryElements = new ArrayList<>();
        Map<Long, ProjectCategoryElement> parentCache = new HashMap<>();

        for (ProjectCategory parent : parentsCategories) {
            ProjectCategoryElement projectCategoryElement = new ProjectCategoryElement(parent.getId(), parent.getCategoryName(), new ArrayList<>());
            projectCategoryQueue.add(parent);
            projectCategoryElements.add(projectCategoryElement);
            parentCache.put(projectCategoryElement.getCategoryId(), projectCategoryElement);
        }

        while (!projectCategoryQueue.isEmpty()) {
            ProjectCategory parent = projectCategoryQueue.poll();
            ProjectCategoryElement parentDto = parentCache.get(parent.getId());

            for (ProjectCategory child : parent.getChildren()) {
                ProjectCategoryElement projectCategoryElement = new ProjectCategoryElement(child.getId(), child.getCategoryName(), new ArrayList<>());
                projectCategoryQueue.add(child);
                parentDto.getChildren().add(projectCategoryElement);
                parentCache.put(projectCategoryElement.getCategoryId(), projectCategoryElement);
            }
        }

        return projectCategoryElements;
    }
}
