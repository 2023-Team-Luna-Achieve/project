package backend.backend.comment.entity;

import backend.backend.noticeboard.entity.NoticeBoard;
import backend.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noticeBoard_id")
    private NoticeBoard noticeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String context;


    private LocalDateTime updated_at;

    private LocalDateTime created_at;

    private LocalDateTime deleted_at;


}
