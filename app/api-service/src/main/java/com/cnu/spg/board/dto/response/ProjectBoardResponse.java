package com.cnu.spg.board.dto.response;

import com.cnu.spg.board.domain.project.ProjectBoard;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectBoardResponse extends BoardDetailResponse {
    private List<ProjectRefResponse> referenceUsers;

    public ProjectBoardResponse(ProjectBoard board, List<CommentResponse> comments, List<ProjectRefResponse> projectRefs) {
        super(board.getId(), board.getTitle(), board.getWriterId(), board.getWriterName(), board.getContent(), comments);
        this.referenceUsers = projectRefs;
    }
}
