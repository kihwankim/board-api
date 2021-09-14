package com.cnu.spg.freeboard.repository;

import com.cnu.spg.freeboard.domain.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardCommentRepositroy extends JpaRepository<FreeBoardComment, Long>{
	int countByContentId(Long contentId);
}
