package com.cnu.spg.board.dto.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchCondition {
    private String titlePart;
    private String writerName;
    private String contentPart;
}
