package com.cnu.spg.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;

    private String title;

    private Long writerId;

    private String writerName;

    private String content;

    private long commentCount;
}
