package com.cnu.spg.board.controller;

import com.cnu.spg.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EduBoardController {

    private final BoardService boardService;

}
