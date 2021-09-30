package com.cnu.spg.board.service;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.domain.BoardType;
import com.cnu.spg.board.domain.Comment;
import com.cnu.spg.board.domain.project.ProjectBoard;
import com.cnu.spg.board.domain.project.ProjectReference;
import com.cnu.spg.board.dto.condition.BoardSearchCondition;
import com.cnu.spg.board.dto.reponse.CommentCountsWithBoardIdResponseDto;
import com.cnu.spg.board.dto.response.*;
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

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardAllService {
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

    public BoardDetailResponseDto getBoard(BoardType boardType, Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);

        List<CommentResponseDto> comments = board.getComments()
                .stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getWriterName(), comment.getContent()))
                .collect(Collectors.toList()); // N + 1 발생

        if (boardType == BoardType.PROJECT && board instanceof ProjectBoard) {
            return findProjectBoardElement((ProjectBoard) board, comments);
        }

        throw new InvalidParameterException("board type exception");
    }

    private ProjectBoardResponseDto findProjectBoardElement(ProjectBoard projectBoard, List<CommentResponseDto> comments) {
        List<Long> referenceIds = projectBoard.getProjectReference().stream()
                .map(projectReference -> projectBoard.getId())
                .collect(Collectors.toList());
        List<ProjectReference> referencedUsersByIds = boardRepository.findReferencedUsersByIds(referenceIds);
        List<ProjectRefResponseDto> referenceUserDtos = referencedUsersByIds.stream()
                .map(projectReference -> new ProjectRefResponseDto(projectReference.getReferenceUser().getName()))
                .collect(Collectors.toList());

        return new ProjectBoardResponseDto(projectBoard, comments, referenceUserDtos);
    }
}
