package com.cnu.spg.noticeboard.repository;

import com.cnu.spg.noticeboard.domain.NoticeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardCommentRepository extends JpaRepository<NoticeBoardComment, Long> {
    int countByContentId(Long contentId);
}
