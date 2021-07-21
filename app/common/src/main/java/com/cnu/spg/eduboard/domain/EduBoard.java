package com.cnu.spg.eduboard.domain;

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
@Table(name = "edu_board")
public class EduBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 40)
    private String title;

    private Long writerId;

    @Column(length = 20)
    private String writerName;

    private short numberOfHit;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date")
    private Calendar createDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "edu_board_id")
    private List<EduBoardFile> eduBoardFile;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private List<EduBoardComment> eduBoardComment;

    public EduBoard() {
    }

    public EduBoard(String title, Long writerId, String writerName, String content) {
        this.title = title;
        this.writerId = writerId;
        this.writerName = writerName;
        this.content = content;
    }
}