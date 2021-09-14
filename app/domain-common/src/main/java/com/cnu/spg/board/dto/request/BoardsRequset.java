package com.cnu.spg.board.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BoardsRequset {
    @NotNull(message = "page 번호를 입력해주세요")
    private Integer pageNum;
    @NotNull(message = "element 개수를 입력해주세요")
    private Integer elementSize;
    private String partTitle;
    private String writerName;
    private String partOfContent;
}
