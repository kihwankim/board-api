package com.cnu.spg.api.board.controller;

import com.cnu.spg.board.dto.BoardDto;
import com.cnu.spg.board.dto.BoardSearchCondition;
import com.cnu.spg.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/hello")
    public ResponseEntity<Page<BoardDto>> getBoards(@RequestParam(value = "page", defaultValue = "0") int pageNum,
                                                    @RequestParam(value = "size", defaultValue = "10") int elementSize,
                                                    @RequestParam(value = "title", required = false) String partTitle,
                                                    @RequestParam(value = "writer", required = false) String writerName,
                                                    @RequestParam(value = "content", required = false) String partOfContent) {
        Pageable pageable = PageRequest.of(pageNum, elementSize);
        BoardSearchCondition boardSearchCondition = new BoardSearchCondition(partTitle, writerName, partOfContent);

        return ResponseEntity.ok().body(boardService.findBoardsOnePage(boardSearchCondition, pageable));
    }
}
