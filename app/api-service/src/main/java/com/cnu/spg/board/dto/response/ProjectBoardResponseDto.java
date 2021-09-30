package com.cnu.spg.board.dto.response;

import com.cnu.spg.board.domain.project.ProjectBoard;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectBoardResponseDto extends BoardDetailResponseDto {
    private List<ProjectRefResponseDto> referenceUsers;

    public ProjectBoardResponseDto(ProjectBoard board, List<CommentResponseDto> comments, List<ProjectRefResponseDto> projectRefs) {
        super(board.getId(), board.getTitle(), board.getWriterId(), board.getWriterName(), board.getContent(), comments);
        this.referenceUsers = projectRefs;
    }
}
