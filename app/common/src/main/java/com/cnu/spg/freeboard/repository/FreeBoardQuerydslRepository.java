package com.cnu.spg.freeboard.repository;

import com.cnu.spg.freeboard.domain.FreeBoard;
import org.springframework.data.domain.Page;

public interface FreeBoardQuerydslRepository {
    Page<FreeBoard> findByWriterNameContaining(String keyWord, int pageNo, int pageSize);
}
