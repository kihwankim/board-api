package com.cnu.spg.noticeboard.domain;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class NoticeBoardTest {
    @Test
    void consturctor_Test() throws Exception {
        // given
        String title = "title 1";
        Long writerId = 1L;
        String writerName = "kkh";
        String content = "content1";

        // when
        NoticeBoard noticeBoard = NoticeBoard.builder()
                .title(title)
                .writerId(writerId)
                .writerName(writerName)
                .content(content)
                .build();

        // then
        assertEquals(title, noticeBoard.getTitle());
        assertEquals(writerId, noticeBoard.getWriterId());
        assertEquals("kkh", noticeBoard.getWriterName());
        assertEquals("content1", noticeBoard.getContent());
    }
}