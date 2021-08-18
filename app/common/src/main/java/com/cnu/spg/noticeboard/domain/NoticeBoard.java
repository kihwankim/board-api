package com.cnu.spg.noticeboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "notice_board")
public class NoticeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 40)
    @Column(name = "title", nullable = false)
    private String title;

    private Long writerId;

    @Column(name = "writer_name", length = 20, nullable = false)
    private String writerName;

    private short numberOfHit;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    private Calendar createDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "notice_board_id")
    private List<NoticeBoardFile> noticeBoardFile;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private List<NoticeBoardComment> noticeBoardComment;

    public NoticeBoard() {
    }

    @Builder
    public NoticeBoard(String title, Long writerId, String writerName, String content) {
        this.title = title;
        this.writerId = writerId;
        this.writerName = writerName;
        this.content = content;
    }

    public void watchSomeoneThisBoard() {
        this.numberOfHit += 1;
    }
}