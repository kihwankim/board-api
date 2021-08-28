package com.cnu.spg.board.service;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.dto.response.BoardResponseDto;
import com.cnu.spg.board.dto.request.BoardSearchConditionRequest;
import com.cnu.spg.board.dto.response.CommentCountsWithBoardIdResponseDto;
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

    public Page<BoardResponseDto> findBoardsOnePage(BoardSearchConditionRequest boardSearchConditionRequest, Pageable pageable) {
        List<Long> ids = boardRepository.findIdsFromPaginationWithKeyword(boardSearchConditionRequest, pageable);
        List<CommentCountsWithBoardIdResponseDto> countListAndBoardIdBulk = commentRepository.findCountListAndBoardIdBulk(ids);

        Map<Long, CommentCountsWithBoardIdResponseDto> boardIdWithCommentNumber = new HashMap<>();
        countListAndBoardIdBulk.forEach(commentDto -> boardIdWithCommentNumber.put(commentDto.getBoardId(), commentDto));

        Page<Board> pageDataFromBoardByIds = boardRepository.findPageDataFromBoardByIds(ids, boardSearchConditionRequest, pageable);

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
}
