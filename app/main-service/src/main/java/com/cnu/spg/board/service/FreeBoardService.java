package com.cnu.spg.board.service;

import com.cnu.spg.freeboard.domain.FreeBoard;
import com.cnu.spg.freeboard.domain.FreeBoardComment;
import com.cnu.spg.freeboard.domain.FreeBoardFile;
import com.cnu.spg.freeboard.repository.FreeBoardCommentRepositroy;
import com.cnu.spg.freeboard.repository.FreeBoardFileRepository;
import com.cnu.spg.freeboard.repository.FreeBoardRepository;
import com.cnu.spg.user.exception.ResourceNotFoundException;
import com.cnu.spg.utils.FilePath;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardFileRepository freeBoardFileRepository;
    private final FreeBoardCommentRepositroy freeBoardCommentRepository;

    @Transactional
    public FreeBoard save(FreeBoard freeBoard) {
        this.freeBoardRepository.save(freeBoard);
        return freeBoard;
    }

    public List<FreeBoard> findByPage(int startNum) {
        List<Long> ids = freeBoardRepository.findIdsByDynamicWriterNameWithPagination(null, startNum, 10);
        Page<FreeBoard> page = freeBoardRepository.findByIdsWithPagination(ids, startNum, 10);

        return page.getContent();
    }

    public int getTotalCount() {
        return (int) this.freeBoardRepository.count();
    }

    public List<FreeBoard> findByTitleContainingOrContentContaining(int startNum, String keyword) {
        Pageable pageable = PageRequest.of(startNum, 10, Sort.by("id").descending());
        return this.freeBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable).getContent();
    }

    public int getCountByTitleContainingOrContentContaining(String keyword) {
        return this.freeBoardRepository.countByTitleContainingOrContentContaining(keyword, keyword);
    }

    public List<FreeBoard> findByWriterNameContaining(int startNum, String keyword) {
        List<Long> ids = freeBoardRepository.findIdsByDynamicWriterNameWithPagination(keyword, startNum, 10);

        return this.freeBoardRepository.findByIdsWithPagination(ids, startNum, 10).getContent();
    }

    public int getCountByWriterNameContaining(String keyword) {
        return this.freeBoardRepository.countByWriterNameContaining(keyword);
    }

    public FreeBoard getFreeBoardDetail(long freeBoardId) {
        FreeBoard freeBoard = this.freeBoardRepository.findById(freeBoardId)
                .orElseThrow(() -> new ResourceNotFoundException("FreeBoard", "id", "it can not find free board data"));

        freeBoard.setNumberOfHit((short) (freeBoard.getNumberOfHit() + 1));
        return this.freeBoardRepository.save(freeBoard);
    }

    @Transactional
    public FreeBoardFile save(FreeBoardFile freeBoardFile) {
        this.freeBoardFileRepository.save(freeBoardFile);
        return freeBoardFile;
    }

    @Transactional
    public void deleteFilesAndFreeBoardDataByContentId(long contentId) {
        // todo kkh : what will we handle this
        boolean result = this.deleteFilesInList(this.freeBoardFileRepository.findAllByFreeBoardId(contentId));
        this.freeBoardRepository.deleteById(contentId);
    }

    @Transactional
    public boolean modifyFreeBoardDetail(FreeBoard newFreeBoard) {
        boolean isDeleteWell = this.deleteFilesInList(this.freeBoardFileRepository.findAllByFreeBoardId(newFreeBoard.getId()));
        if (isDeleteWell) this.freeBoardRepository.save(newFreeBoard);
        return isDeleteWell;
    }

    private boolean deleteFile(String filename) {
        File file = new File(FilePath.FreeBoard.getFilePath() + filename);
        if (file.exists()) {
            return file.delete();
        }

        return false;
    }

    private boolean deleteFilesInList(List<FreeBoardFile> freeBoardFileList) {
        boolean isDeleteError = true;
        for (FreeBoardFile freeBoardFile : freeBoardFileList) {
            if (!this.deleteFile(freeBoardFile.getStoreFileName())) {
                isDeleteError = false;
            }
        }

        return isDeleteError;
    }

    // comment
    @Transactional
    public FreeBoardComment save(FreeBoardComment freeBoardComment) {
        this.freeBoardCommentRepository.save(freeBoardComment);
        return freeBoardComment;
    }

    public int getCommentCountByContentId(Long contentId) {
        return this.freeBoardCommentRepository.countByContentId(contentId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        this.freeBoardCommentRepository.deleteById(commentId);
    }

}