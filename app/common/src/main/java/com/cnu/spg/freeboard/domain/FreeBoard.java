package com.cnu.spg.freeboard.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "free_board")
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 40, nullable = false)
    private String title;

    private Long writerId;

    @Column(length = 20)
    private String writerName;

    private short numberOfHit;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "create_date")
    private Calendar createDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "free_board_id")
    private List<FreeBoardFile> freeBoardFile;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private List<FreeBoardComment> freeBoardComment;

    public FreeBoard() {
    }

    public FreeBoard(String title, long writerId, String writerName, String content) {
        this.title = title;
        this.writerId = writerId;
        this.writerName = writerName;
        this.content = content;
    }
}