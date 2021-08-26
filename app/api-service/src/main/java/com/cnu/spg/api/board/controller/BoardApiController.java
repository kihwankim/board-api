package com.cnu.spg.api.board.controller;

import com.cnu.spg.board.dto.BoardDto;
import com.cnu.spg.board.dto.BoardSearchCondition;
import com.cnu.spg.board.dto.request.BoardsRequset;
import com.cnu.spg.board.service.BoardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @ApiOperation("전체 게시판 정보를 제공")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/api/board-service/v1/boards")
    public ResponseEntity<Page<BoardDto>> getBoards(@Valid BoardsRequset boardsRequset) {
        Pageable pageable = PageRequest.of(boardsRequset.getPageNum(), boardsRequset.getElementSize());
        BoardSearchCondition boardSearchCondition = new BoardSearchCondition(boardsRequset.getPartTitle(), boardsRequset.getWriterName(), boardsRequset.getPartOfContent());

        return ResponseEntity.ok().body(boardService.findBoardsOnePage(boardSearchCondition, pageable));
    }
}
