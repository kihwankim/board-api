package com.cnu.spg.board.service;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.dto.BoardDto;
import com.cnu.spg.board.dto.BoardSearchCondition;
import com.cnu.spg.board.dto.CommentCountsWithBoardIdDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Page<BoardDto> findBoardsOnePage(BoardSearchCondition boardSearchCondition, Pageable pageable) {
        List<Long> ids = boardRepository.findIdsFromPaginationWithKeyword(boardSearchCondition, pageable);
        List<CommentCountsWithBoardIdDto> countListAndBoardIdBulk = commentRepository.findCountListAndBoardIdBulk(ids);

        Map<Long, CommentCountsWithBoardIdDto> boardIdWithCommentNumber = new HashMap<>();
        countListAndBoardIdBulk.forEach(commentDto -> boardIdWithCommentNumber.put(commentDto.getBoardId(), commentDto));

        Page<Board> pageDataFromBoardByIds = boardRepository.findPageDataFromBoardByIds(ids, boardSearchCondition, pageable);

        List<BoardDto> boardDtos = new ArrayList<>();
        pageDataFromBoardByIds.getContent().forEach(board -> boardDtos.add(BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerId(board.getWriterId())
                .writerName(board.getWriterName())
                .commentCount(commentCountFromCommentDto(boardIdWithCommentNumber.get(board.getId())))
                .build()));

        return new PageImpl<>(boardDtos, pageable, pageDataFromBoardByIds.getTotalElements());
    }

    private long commentCountFromCommentDto(CommentCountsWithBoardIdDto commentCountsWithBoardIdDto) {
        if (commentCountsWithBoardIdDto == null) return 0L;

        return commentCountsWithBoardIdDto.getNumberOfComments();
    }
}
