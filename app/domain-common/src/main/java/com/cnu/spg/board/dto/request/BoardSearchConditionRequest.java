package com.cnu.spg.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchConditionRequest {
    private String titlePart;
    private String writerName;
    private String contentPart;
}
