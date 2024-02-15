package backend.backend.board.entity;

import backend.backend.comment.entity.Comment;
import backend.backend.common.domain.BaseEntity;
import backend.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String context;

    private int viewCount;

    @Builder
    public Board(User user, String title, Category category, String context) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.context = context;
    }

    public void update(String title, Category category, String context) {
        this.title = title;
        this.category = category;
        this.context = context;
    }

    public void addViewCount() {
        this.viewCount ++;
    }
}