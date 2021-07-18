package com.cnu.spg.eduboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnu.spg.eduboard.domain.EduBoardComment;

public interface EduBoardCommentRepository extends JpaRepository<EduBoardComment, Long>{
	int countByContentId(Long contentId);
}
