package com.cnu.spg.eduboard.repository;

import com.cnu.spg.board.domain.EduBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EduBoardRepository extends JpaRepository<EduBoard, Long> {

    Optional<EduBoard> findById(Long id);

    Page<EduBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyWord, Pageable pageable);

    Page<EduBoard> findByWriterNameContaining(String keyWord, Pageable pageable);

    int countByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

    int countByWriterNameContaining(String keyWord);
}