package com.cnu.spg.board.dto.condition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectBoardCondition {
    private String titlePart;
    private String writerName;
    private String contentPart;
}
