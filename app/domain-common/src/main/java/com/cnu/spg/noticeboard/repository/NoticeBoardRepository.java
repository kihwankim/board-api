package com.cnu.spg.noticeboard.repository;

import com.cnu.spg.noticeboard.domain.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    Optional<NoticeBoard> findById(Long id);

    Page<NoticeBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyWord, Pageable pageable);

    Page<NoticeBoard> findByWriterNameContaining(String keyWord, Pageable pageable);

    int countByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

    int countByWriterNameContaining(String keyWord);
}