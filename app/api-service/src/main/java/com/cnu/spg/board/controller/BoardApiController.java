package com.cnu.spg.board.controller;

import com.cnu.spg.board.domain.BoardType;
import com.cnu.spg.board.dto.request.BoardSearchConditionRequest;
import com.cnu.spg.board.dto.request.BoardsRequset;
import com.cnu.spg.board.dto.response.BoardResponseDto;
import com.cnu.spg.board.dto.response.CategoriesResponseDto;
import com.cnu.spg.board.exception.NotExistBoardTypeException;
import com.cnu.spg.board.service.BoardService;
import com.cnu.spg.board.service.ProjectService;
import com.cnu.spg.config.resolver.UserId;
import com.cnu.spg.user.domain.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final ProjectService projectService;

    @ApiOperation("[권한] 전체 게시판 정보를 제공")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "pageNum", value = "page number of pagination", required = true, paramType = "query"),
            @ApiImplicitParam(name = "elementSize", value = "each page element number", required = true, paramType = "query")
    })
    @GetMapping("/api/v1/boards")
    public ResponseEntity<Page<BoardResponseDto>> getBoards(@Valid BoardsRequset boardsRequset) {
        Pageable pageable = PageRequest.of(boardsRequset.getPageNum(), boardsRequset.getElementSize());
        BoardSearchConditionRequest boardSearchConditionRequest = new BoardSearchConditionRequest(boardsRequset.getPartTitle(), boardsRequset.getWriterName(), boardsRequset.getPartOfContent());

        return ResponseEntity.ok().body(boardService.findBoardsOnePage(boardSearchConditionRequest, pageable));
    }

    @ApiOperation("[권한] board 정보 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header"),
            @ApiImplicitParam(name = "id", value = "board id 정보", readOnly = true, paramType = "path")
    })
    @GetMapping("/api/v1/boards/{boardType}/{id}")
    public ResponseEntity<?> getBoard(@PathVariable String boardType, @PathVariable Long id) {
        BoardType boardTypeEnum = BoardType.findBoardTypeByKey(boardType)
                .orElseThrow(NotExistBoardTypeException::new);


        return ResponseEntity.ok().body(id);
    }

    @ApiOperation("[권한] project board를 위한 category 정보 가져오기")
    @GetMapping("/api/v1/categories")
    public ResponseEntity<CategoriesResponseDto> getAllJoinedCategories(@UserId User user) {
        return ResponseEntity.ok(projectService.findAllUserCategories(user.getId()));
    }
}
