package com.cnu.spg.board.dto.response;

public class ProjectBoardResponseDto extends BoardResponseDto {
    public ProjectBoardResponseDto(Long id, String title, Long writerId, String writerName, String content, long commentCount) {
        super(id, title, writerId, writerName, content, commentCount);
    }
}
