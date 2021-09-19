package com.cnu.spg.board.service;

import com.cnu.spg.board.domain.project.ProjectCategory;
import com.cnu.spg.board.dto.response.CategoriesResponseDto;
import com.cnu.spg.board.dto.response.ProjectCategoryElement;
import com.cnu.spg.board.repository.project.ProjectCategoryRepository;
import com.cnu.spg.comon.exception.NotFoundException;
import com.cnu.spg.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectCategoryRepository projectCategoryRepository;

    public CategoriesResponseDto findAllUserCategories(Long userId) {
        List<ProjectCategory> categories = projectCategoryRepository.findCategoriesByUserId(userId);
        List<ProjectCategory> parentsCategories = categories
                .parallelStream()
                .filter(projectCategory -> projectCategory.getParent() == null)
                .collect(Collectors.toList());

        Map<Long, List<ProjectCategory>> parentsMappedCategoriesCache = categories
                .stream()
                .filter(projectCategory -> projectCategory.getParent() != null)
                .collect(groupingBy(projectCategory -> projectCategory.getParent().getId()));

        return new CategoriesResponseDto(createTreeCategories(parentsCategories, parentsMappedCategoriesCache));
    }

    private List<ProjectCategoryElement> createTreeCategories(List<ProjectCategory> parentsCategories, Map<Long, List<ProjectCategory>> parentsMappedCategoriesCache) {
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
            List<ProjectCategory> children = parentsMappedCategoriesCache.getOrDefault(parent.getId(), new ArrayList<>());

            for (ProjectCategory child : children) {
                ProjectCategoryElement projectCategoryElement = new ProjectCategoryElement(child.getId(), child.getCategoryName(), new ArrayList<>());
                projectCategoryQueue.add(child);
                parentDto.getChildren().add(projectCategoryElement);
                parentCache.put(projectCategoryElement.getCategoryId(), projectCategoryElement);
            }
        }

        return projectCategoryElements;
    }

    @Transactional
    public Long createProjectCategory(User user, String categoryName, Long parentCategoryId) {
        ProjectCategory parentCategory = null;
        if (parentCategoryId != null) {
            parentCategory = projectCategoryRepository.findById(parentCategoryId)
                    .orElseThrow(() -> new NotFoundException("부모 category가 없습니다"));
        }

        ProjectCategory createdCategory = ProjectCategory.builder()
                .categoryName(categoryName)
                .parent(parentCategory)
                .user(user)
                .build();

        ProjectCategory savedCategory = projectCategoryRepository.save(createdCategory);

        return savedCategory.getId();
    }
}
