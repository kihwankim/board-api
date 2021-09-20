package com.cnu.spg.board.service;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.domain.BoardType;
import com.cnu.spg.board.dto.condition.BoardSearchCondition;
import com.cnu.spg.board.dto.response.BoardResponseDto;
import com.cnu.spg.board.dto.response.CommentCountsWithBoardIdResponseDto;
import com.cnu.spg.board.exception.BoardNotFoundException;
import com.cnu.spg.board.repository.BoardRepository;
import com.cnu.spg.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Page<BoardResponseDto> findBoardsOnePage(BoardSearchCondition boardSearchCondition, Pageable pageable) {
        List<Long> ids = boardRepository.findIdsFromPaginationWithKeyword(boardSearchCondition, pageable);
        List<CommentCountsWithBoardIdResponseDto> countListAndBoardIdBulk = commentRepository.findCountListAndBoardIdBulk(ids);

        Map<Long, CommentCountsWithBoardIdResponseDto> boardIdWithCommentNumber = countListAndBoardIdBulk
                .stream()
                .collect(Collectors.toMap(CommentCountsWithBoardIdResponseDto::getBoardId, Function.identity()));

        Page<Board> pageDataFromBoardByIds = boardRepository.findPageDataFromBoardByIds(ids, boardSearchCondition, pageable);

        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
        pageDataFromBoardByIds.getContent().forEach(board -> boardResponseDtos.add(BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerId(board.getWriterId())
                .writerName(board.getWriterName())
                .commentCount(commentCountFromCommentDto(boardIdWithCommentNumber.get(board.getId())))
                .build()));

        return new PageImpl<>(boardResponseDtos, pageable, pageDataFromBoardByIds.getTotalElements());
    }

    private long commentCountFromCommentDto(CommentCountsWithBoardIdResponseDto commentCountsWithBoardIdResponseDto) {
        if (commentCountsWithBoardIdResponseDto == null) return 0L;

        return commentCountsWithBoardIdResponseDto.getNumberOfComments();
    }

    public BoardResponseDto getBoard(BoardType boardType, Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
        // TODO : board 정보 가져오기
//        if (board.getT)

        return null;
    }
}
