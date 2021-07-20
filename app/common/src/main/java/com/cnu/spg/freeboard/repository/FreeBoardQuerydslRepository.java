package com.cnu.spg.freeboard.repository;

import com.cnu.spg.freeboard.domain.FreeBoard;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FreeBoardQuerydslRepository {
    Page<FreeBoard> findByIdsWithPagination(List<Long> ids, int pageNo, int pageSize);

    List<Long> findIdsByDynamicWriterNameWithPagination(String writerKeyword, int pageNo, int pageSize);
}
