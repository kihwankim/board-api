package com.cnu.spg.freeboard.repository;

import java.util.Optional;

import com.cnu.spg.freeboard.domain.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>, FreeBoardQuerydslRepository {

	Page<FreeBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyWord, Pageable pageable);

	int countByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
	
	int countByWriterNameContaining(String keyWord);
}